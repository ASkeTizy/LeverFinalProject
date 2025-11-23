package o.e.service;

import o.e.dao.UserDAO;
import o.e.dto.UserDTO;
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
    Map<Long, User> users = new HashMap<>();
    private final CommentQueue queue;

    public AuthorizationService(UserDAO userDAO, CommentService commentService, CommentQueue queue) {
        this.userDAO = userDAO;
        this.commentService = commentService;
        this.queue = queue;
    }

    public void addUserToConfirmation(UserDTO userDTO) {
        ++ID;
        users.put(ID, new User(0L,
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
        if (createUser(user)) {
            users.remove(userId);
        }
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

    public void declineComment(Long commentId) {
        queue.removeComment(commentId);
    }
}
