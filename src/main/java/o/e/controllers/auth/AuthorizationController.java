package o.e.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import o.e.dto.ConfirmDTO;
import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
import o.e.entity.User;
import o.e.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserDTO userDto) {

        authorizationService.addUserToConfirmation(userDto);
        return "User added to confirmation";

    }


    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        return authorizationService.forgotPassword(email);
    }

    @PostMapping("/reset")
    public User submitPassword(@RequestBody VerifiedUserDTO verifiedUserDTO) {
        return authorizationService.updateUserPassword(verifiedUserDTO);

    }
    @PostMapping("/confirm")
    public User confirmUser(@RequestBody ConfirmDTO confirmDTO) {
        return authorizationService.verifyUser(confirmDTO.code(), confirmDTO.email());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request) {
        if(authorizationService.loginUser(email,password,request)) {
            return ResponseEntity.ok().body("User authorized");
        }
        return ResponseEntity.badRequest().body("User not authorized");
    }

    @PostMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestBody Map<String, String> payload) {
        try {
            authorizationService.generateCode(payload.get("email"));
            return ResponseEntity.ok("Verification code sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send code");
        }
    }
}
