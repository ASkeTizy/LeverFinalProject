package o.e.controllers.auth;

import o.e.entity.User;
import o.e.service.AuthorizationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthorizationService authorizationService;

    public AdminController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/approve/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveUser(@PathVariable("userId") Long userId) {

        authorizationService.approveUser(userId);
        return "User approved";

    }
    @GetMapping("/approve/comment/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveComment(@PathVariable("commentId") Integer commentId) {

        authorizationService.approveComment(commentId);
        return "Comment approved";

    }

    @GetMapping("decline/comment/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String declineComment(@PathVariable("commentId") Integer commentId) {

        authorizationService.declineComment(commentId);
        return "Comment decline";

    }
    @GetMapping("/decline/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String declineUser(@PathVariable("userId") Long userId) {

        authorizationService.declineUser(userId);
        return "User decline";

    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {

        return authorizationService.getUsers();


    }
}
