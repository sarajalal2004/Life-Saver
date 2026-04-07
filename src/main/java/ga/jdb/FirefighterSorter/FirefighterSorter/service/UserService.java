package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.EmailVerificationToken;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.User;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.EmailVerificationTokenRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.UserRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.security.JWTUtils;
import ga.jdb.FirefighterSorter.FirefighterSorter.security.MyUserDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    private JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails,
                       EmailVerificationTokenRepository emailVerificationTokenRepository,
                       @Lazy JavaMailSender mailSender){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.mailSender = mailSender;
    }

    public User createUser(User user){
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

            try{
                sendVerificationEmail(userObject.getEmail(), token.getToken());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return userObject;
        }else {
            throw new RuntimeException();
            //TODO: add the exception
        }
    }

    private void sendVerificationEmail(String email, String token) throws MessagingException {
        String URL = "http://localhost:8080/auth/users/verify?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Verify Your Email");
        helper.setText("<h1>Hey </h1>"+
                "<p>Please click the following Button to verify</p>"+
                "<a href=\"" + URL +"\" style='display:inline-block;padding:12px 24px;display:inline-block;padding:12px 24px;font-size:16px;color:white;background-color:#28a745;'>Verify</a>"
                , true);
        mailSender.send(message);
    }

    public ResponseEntity<?> verifyUser(String token){
        EmailVerificationToken emailVerificationToken= emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthenticationException("Invalid verification token"));
        if(emailVerificationToken.getExpiryDate().isAfter(LocalDateTime.now())){
            emailVerificationToken.getUser().setVerified(true);
        }else {
            throw new AuthenticationException("Verification token expired");
        }
        userRepository.save(emailVerificationToken.getUser());
        emailVerificationTokenRepository.delete(emailVerificationToken);
        return ResponseEntity.ok("Email verified");
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
