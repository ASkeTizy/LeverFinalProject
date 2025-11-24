package o.e.controllers.auth;

import o.e.dto.UserDTO;
import o.e.entity.User;
import o.e.service.AuthorizationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserDTO userDto) {

        authorizationService.addUserToConfirmation(userDto);
        return "User added to confirmation";

    }


    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        return "Forgot password" + email;
    }

    @PostMapping("/reset")
    public String submitPassword(@RequestParam("code") String code,
                                 @RequestParam("password") String password) {

        return "Submit password" + code + password;
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/check_code")
    public String checkCode() {
        return "Forgot password" + 1;
    }
}
