package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.AuthenticationException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.BadRequestException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.EmailVerificationToken;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.PasswordResetToken;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.User;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ChangePasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ForgetPasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.LoginRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ResetPasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.EmailVerificationTokenRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.PasswordResetTokenRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.UserRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.security.JWTUtils;
import ga.jdb.FirefighterSorter.FirefighterSorter.security.MyUserDetails;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    @Value("${spring.mail.username}")
    private String fromMail;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails,
                       EmailVerificationTokenRepository emailVerificationTokenRepository,
                       @Lazy PasswordResetTokenRepository passwordResetTokenRepository,
                       @Lazy JavaMailSender mailSender){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailSender = mailSender;
    }

    public User createUser(User user) throws MessagingException {
        if(!userRepository.existsByEmail(user.getEmail())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setStatus(User.Status.Active);
            user.setRole(User.Role.Firefighter);
            user.setVerified(false);
            User userObject = userRepository.save(user);

            EmailVerificationToken token = new EmailVerificationToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(userObject);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(30));
            emailVerificationTokenRepository.save(token);

            sendVerificationEmail(userObject.getEmail(), token.getToken());
            return userObject;
        }else {
            throw new InformationExistException("Username already used");
        }
    }

    private void sendVerificationEmail(String email, String token) throws MessagingException {
        String URL = "http://localhost:8080/auth/users/verify?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setFrom(fromMail);
        helper.setSubject("Verify Your Email");
        helper.setText("<h1>Hey </h1>"+
                "<p>Please click the following Button to verify</p>"+
                "<a href=\"" + URL +"\" style='display:inline-block;padding:12px 24px;display:inline-block;padding:12px 24px;font-size:16px;color:white;background-color:#28a745;'>Verify</a>"
                , true);
        mailSender.send(message);
    }

    public ResponseEntity<String> verifyUser(String token){
        EmailVerificationToken emailVerificationToken= emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthenticationException("Invalid verification token or email already verified"));
        if(emailVerificationToken.getExpiryDate().isAfter(LocalDateTime.now())){
            emailVerificationToken.getUser().setVerified(true);
        }else {
            throw new AuthenticationException("Verification token expired");
        }
        userRepository.save(emailVerificationToken.getUser());
        emailVerificationTokenRepository.delete(emailVerificationToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Email verified Successfully🎉");
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public ResponseEntity<String> loginUser(LoginRequest request){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try{
            Authentication authentication =  authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(JWT);
        } catch (LockedException e) {
            throw new AuthenticationException("Error: This account has been deactivated. Please contact an admin for support.");
        } catch (DisabledException e) {
            throw new AuthenticationException("Error: Email not verified. Please verify your email before logging in.");
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Error: Username or password is incorrect");
        }
    }

    public static User getCurrentLoggedInUser(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(myUserDetails == null)
            throw new AuthenticationException("No user is logged in currently");
        return myUserDetails.getUser();
    }

    public ResponseEntity<String> changePassword(ChangePasswordRequest request){
        User loginUser = getCurrentLoggedInUser();
        if(!passwordEncoder.matches(request.getOldPassword(), loginUser.getPassword())){
            return ResponseEntity.ok("Old password is not correct");
        }else if(request.getOldPassword().equals(request.getNewPassword()))
            return ResponseEntity.ok("New password couldn't be as old password");
        else{
            loginUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(loginUser);
            return ResponseEntity.ok("password has been created");
        }
    }

    public ResponseEntity<String> forgetPassword(ForgetPasswordRequest request) throws MessagingException {
        if(userRepository.existsByEmail(request.getEmail())){
            User forgetPassUser = userRepository.findUserByEmail(request.getEmail());
            if(forgetPassUser.getStatus().equals(User.Status.Inactive))
                throw new AuthenticationException("Error: This account has been deactivated. Please contact an admin for support.");
            PasswordResetToken passwordResetToken =
                    passwordResetTokenRepository
                    .findByUser(forgetPassUser)
                    .orElse(new PasswordResetToken());
            passwordResetToken.setUser(forgetPassUser);
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
            passwordResetTokenRepository.save(passwordResetToken);
            sendForgetPasswordEmail(request.getEmail(), passwordResetToken.getToken());
        }
        return ResponseEntity.ok("If user exist, email send to reset password");
    }

    private void sendForgetPasswordEmail(String email, String token) throws MessagingException {
        String URL = "http://localhost:8080/auth/users/reset-password";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setFrom(fromMail);
        helper.setSubject("Reset your password");
        helper.setText("<p>Please enter your email and password</p>"+
                        "<form method='post' action='" + URL + "'>"+
                        "<input type='hidden' name='token' value='" + token + "'/>"+
                        "<label>New Password</label>"+
                        "<input type='text' name='newPassword'/>"+
                        "</form>"
                , true);
        mailSender.send(message);
    }

    public ResponseEntity<String> resetPassword(String token, String newPassword){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new AuthenticationException("Request is invalid"));
        User resetTokenUser = resetToken.getUser();
        if(newPassword != null){
            resetTokenUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(resetTokenUser);
            passwordResetTokenRepository.delete(resetToken);
            return ResponseEntity.ok("Password has been reset Successfully! 😁");
        }else{
            throw new BadRequestException("The password is not accepted");
        }
    }
}
