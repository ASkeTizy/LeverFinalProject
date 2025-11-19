package controllers.auth;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        return "Forgot password"+ email;
    }
    @PostMapping("/submit")
    public String submitPassword(@RequestBody Integer code,String password) {
        return "Submit password"+ code + password;
    }
    @GetMapping("/check_code")
    public String checkCode() {
        return "Forgot password"+ 1;
    }
}
