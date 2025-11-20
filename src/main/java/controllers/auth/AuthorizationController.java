package controllers.auth;

import entity.RequestPassword;
import entity.User;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        return "Forgot password"+ email;
    }
    @PostMapping(value = "/reset",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitPassword(@RequestParam("code") String code,
                                 @RequestParam("password") String password) {

        return "Submit password"+ code + password;
    }
    @GetMapping("/check_code")
    public String checkCode() {
        return "Forgot password"+ 1;
    }
}
