package o.e.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import o.e.dto.UserDTO;
import o.e.dto.VerifiedUserDTO;
import o.e.entity.Comment;
import o.e.entity.Roles;
import o.e.entity.User;
import o.e.exception.ResourceNotFoundException;
import o.e.exception.UserNotVerifiedException;
import o.e.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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

    private Roles defineRole(String role) {
        return switch (role) {
            case "SELLER" -> Roles.SELLER;
            case "ADMIN" -> Roles.ADMIN;
            default -> Roles.SELLER;
        };
    }

    public void addUserToConfirmation(UserDTO userDTO) {
        ++ID;

        users.put(ID, new User(ID,
                userDTO.firstName(),
                userDTO.lastName(),
                userDTO.password(),
                userDTO.email(),
                Date.valueOf(LocalDate.now()),
                defineRole(userDTO.role())));

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

    public boolean loginUser(String email, String password, HttpServletRequest request) {
        var user = customUserDetailsService.loadUserByUsername(email);
        if (Objects.equals(user.getPassword(), password)) {
            var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            return true;
        }
        return false;
    }

    public void declineUser(Long userId) {
        users.remove(userId);
    }

    public List<User> getUsers() {
//        return userRepository.findAll();
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
            User user = approvedUsers.get(email);
            if (user == null) throw new ResourceNotFoundException("NO user with this email");
            return userRepository.save(user);
        } else throw new UserNotVerifiedException("User " + email + " not verified");
    }

    public String forgotPassword(String email) {
        return verificationService.generateCode(email);
    }
}
