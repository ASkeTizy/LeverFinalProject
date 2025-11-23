package o.e.controllers.auth;

import o.e.dto.UserDTO;
import o.e.entity.User;
import o.e.service.AuthorizationService;
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

    @GetMapping("/approve/{userId}")
    public String approveUser(@RequestParam("userId") Long userId) {

        authorizationService.approveUser(userId);
        return "User approved";

    }
    @GetMapping("approve/{commentId}")
    public String approveComment(@RequestParam("commentId") Long commentId) {

        authorizationService.approveComment(commentId);
        return "Comment approved";

    }

    @GetMapping("decline/{commentId}")
    public String declineComment(@RequestParam("commentId") Long commentId) {

        authorizationService.declineComment(commentId);
        return "Comment decline";

    }
    @GetMapping("/decline/{userId}")
    public String declineUser(@RequestParam("userId") Long userId) {

        authorizationService.declineUser(userId);
        return "User decline";

    }

    @GetMapping("/users")
    public List<User> getUsers() {

        return authorizationService.getUsers();


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

    @GetMapping("/check_code")
    public String checkCode() {
        return "Forgot password" + 1;
    }
}
