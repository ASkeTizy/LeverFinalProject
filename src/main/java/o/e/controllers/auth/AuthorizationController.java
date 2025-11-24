package o.e.controllers.auth;

import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
import o.e.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public String submitPassword(@RequestBody VerifiedUserDTO verifiedUserDTO) {
        return String.valueOf(authorizationService.verifyUser(verifiedUserDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody VerifiedUserDTO verifiedUserDTO) {
        if(authorizationService.verifyUser(verifiedUserDTO)) {
            return ResponseEntity.ok().body("User created");
        }
        return ResponseEntity.badRequest().body("User not created");
    }

    @GetMapping("/check_code/{email}")
    public String checkCode(@PathVariable String email) {

        return authorizationService.generateCode(email);
    }
}
