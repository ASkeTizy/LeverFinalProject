package o.e.service;

import o.e.dao.UserDAO;
import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
import o.e.entity.Comment;
import o.e.entity.Roles;
import o.e.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationService {
    private static Long ID = 0L;
    private final UserDAO userDAO;
    private final CommentService commentService;
    private final CommentQueue queue;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final CustomUserDetailsService customUserDetailsService;
    Map<Long, User> users = new HashMap<>();

    public AuthorizationService(UserDAO userDAO, CommentService commentService, CommentQueue queue, VerificationService verificationService, EmailService emailService, CustomUserDetailsService customUserDetailsService) {
        this.userDAO = userDAO;
        this.commentService = commentService;
        this.queue = queue;
        this.verificationService = verificationService;
        this.emailService = emailService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public void addUserToConfirmation(UserDTO userDTO) {
        ++ID;
        users.put(ID, new User(ID,
                userDTO.firstName(),
                userDTO.lastName(),
                userDTO.email(),
                userDTO.password(),
                Date.valueOf(LocalDate.now()),
                Roles.SELLER));
    }

    private boolean createUser(User user) {

        return userDAO.addUser(user);
    }

    public void approveUser(Long userId) {
        var user = users.get(userId);
        System.out.println(user);
        if (user != null) {
            var code = verificationService.generateCode(user.email());
            emailService.sendVerificationEmail(user.email(), code);
        }

    }

    public boolean loginUser(String email) {
        customUserDetailsService.loadUserByUsername(email);
        return true;
    }

    public void declineUser(Long userId) {
        users.remove(userId);
    }

    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    public void approveComment(Long commentId) {
        var comment = queue.getComments().get(commentId);
        if (createComment(comment) != null) {
            queue.removeComment(commentId);
        }
    }

    private Comment createComment(Comment comment) {
        return commentService.addComment(comment);
    }

    private Long findUserByEmail(String email) {
        var user = users.values().stream().filter(el -> el.email().equals(email)).findFirst();
        return user.map(User::id).orElse(null);
    }

    public void updateUserPassword(User user) {
        userDAO.updateUserPassword(user);
    }

    public void declineComment(Long commentId) {
        queue.removeComment(commentId);
    }

    public void generateCode(String email) {
        var code = verificationService.generateCode(email);
        emailService.sendVerificationEmail(email, code);

    }

    public boolean verifyUser(VerifiedUserDTO verifiedUserDTO) {
        boolean valid = verificationService.verifyCode(verifiedUserDTO.email(), verifiedUserDTO.code());
        if (valid) {
            var user = users.remove(findUserByEmail(verifiedUserDTO.email()));
            if (user != null) {
                updateUserPassword(user);
                return true;
            }
        }
        return false;
    }

    public String forgotPassword(String email) {
        return verificationService.generateCode(email);
    }
}
