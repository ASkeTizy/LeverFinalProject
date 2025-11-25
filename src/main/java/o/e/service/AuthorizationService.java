package o.e.service;

import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
import o.e.entity.Comment;
import o.e.entity.Roles;
import o.e.entity.User;
import o.e.exception.ResourceNotFoundException;
import o.e.exception.UserNotVerifiedException;
import o.e.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AuthorizationService {
    private static Long ID = 0L;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final CommentQueue queue;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final CustomUserDetailsService customUserDetailsService;
    Map<Long, User> users = new HashMap<>();
    Map<String, User> approvedUsers = new HashMap<>();

    public AuthorizationService(UserRepository repository, CommentService commentService, CommentQueue queue, VerificationService verificationService, EmailService emailService, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = repository;
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

    private User createUser(User user) {

        return userRepository.save(user);
    }

    public void approveUser(Long userId) {
        var user = users.get(userId);

        if (user != null) {
            var code = verificationService.generateCode(user.getEmail());
            emailService.sendVerificationEmail(user.getEmail(), code);
            approvedUsers.put(user.getEmail(), user);
            users.remove(userId);
        } else throw new ResourceNotFoundException("User not found" + userId);

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

    public void approveComment(Integer commentId) {
        var comment = queue.getComments().get(commentId);
        if (createComment(comment) != null) {
            queue.removeComment(commentId);
        } else throw new ResourceNotFoundException("Comment not found in inner queuer" + commentId);
    }

    private Comment createComment(Comment comment) {
        return commentService.createComment(comment);
    }

    private Long findUserByEmail(String email) {
        var user = users.values().stream().filter(el -> el.getEmail().equals(email)).findFirst();
        return user.map(User::getId).orElse(null);
    }

    public User updateUserPassword(VerifiedUserDTO user) {
        if (verifyUser(user.code(), user.email()) != null) {
            var dbUser = userRepository.findByEmail(user.email()).orElseThrow(
                    () -> new NoSuchElementException("no user with email " + user.email()));
            dbUser.setPassword(user.password());
           return userRepository.save(dbUser);
        }
        return null;
    }

    public void declineComment(Integer commentId) {
        queue.removeComment(commentId);
    }

    public void generateCode(String email) {
        var code = verificationService.generateCode(email);
        emailService.sendVerificationEmail(email, code);

    }

    public User verifyUser(String code, String email) {
        boolean valid = verificationService.verifyCode(email, code);
        if (valid) {
            return approvedUsers.get(email);

        } else throw new UserNotVerifiedException("User " + email + " not verified");
    }

    public String forgotPassword(String email) {
        return verificationService.generateCode(email);
    }
}
