package o.e.controllers.auth;

import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
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

    @PostMapping("/create")
    public String createUser(@RequestBody UserDTO userDto) {

        authorizationService.addUserToConfirmation(userDto);
        return "User added to confirmation";

    }


    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        return authorizationService.forgotPassword(email);
    }

    @PostMapping("/reset")
    public String submitPassword(@RequestBody VerifiedUserDTO verifiedUserDTO) {
        return String.valueOf(authorizationService.verifyUser(verifiedUserDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody VerifiedUserDTO verifiedUserDTO) {
        if(authorizationService.loginUser(verifiedUserDTO.email())) {
            return ResponseEntity.ok().body("User created");
        }
        return ResponseEntity.badRequest().body("User not created");
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
