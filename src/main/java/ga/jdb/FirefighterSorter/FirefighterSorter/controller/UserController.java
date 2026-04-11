package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.User;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ChangePasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ForgetPasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.LoginRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.ResetPasswordRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public User createUser(@RequestBody User user) throws MessagingException {
        return userService.createUser(user);
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token){
        return userService.verifyUser(token);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return userService.changePassword(changePasswordRequest);
    }

    @PostMapping(path = "/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        return userService.forgetPassword(forgetPasswordRequest);
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword){
        return userService.resetPassword(token, newPassword);
    }
}
