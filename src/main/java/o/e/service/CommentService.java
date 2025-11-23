package o.e.service;

import o.e.dao.CommentDAO;
import o.e.dao.UserDAO;
import o.e.dto.CommentDTO;
import o.e.entity.Comment;
import o.e.entity.SellerInformationDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentDAO commentDAO;
    private final UserDAO userDao;
    private final CommentQueue queue;

    public CommentService(CommentDAO commentDAO, UserDAO userDao, CommentQueue queue) {
        this.commentDAO = commentDAO;
        this.userDao = userDao;
        this.queue = queue;
    }


    public Comment findByUserIdAndCommentId(Long userId, Long commentId) {
        return commentDAO.findByUserIdAndCommentId(userId, commentId);
    }

    public Comment addComment(Comment comment) {
        var user = userDao.findUserById(comment.authorId());
        if (user != null) {
            return commentDAO.createComment(comment);
        }
        return null;
    }

    public boolean addCommentToCheck(Long userId, CommentDTO commentDTO) {
        var user = userDao.findUserById(userId);
        if (user != null) {
            queue.addComment(userId, commentDTO);
            return true;
        }
        return false;
    }

    public List<Comment> findAllByUserId(Long userId) {
        return commentDAO.findAllCommentsByUserId(userId);
    }

    public boolean deleteComment(Long userId, Long commentId) {
        return commentDAO.deleteComment(userId, commentId);
    }

    public Comment updateComment(Long userId, Long commentId, CommentDTO commentDTO) {
        return commentDAO.updateComment(new Comment(commentId, commentDTO.message(), userId, null, commentDTO.rate()));
    }

    public double calculateSellerRating(Long userId) {
        var comments = commentDAO.findAllCommentsByUserId(userId);
        if (!comments.isEmpty()) {
            return comments.stream().mapToDouble(Comment::rate).average().orElse(0);
        }
        return 0;
    }

    public List<SellerInformationDTO> setSellerRate(List<SellerInformationDTO> users) {

        return users.stream().peek(el -> el.setRate(calculateSellerRating(el.getId()))).toList();
    }

    public List<SellerInformationDTO> getTopSellers() {
        var users = userDao.findUsersWithComments();

        return setSellerRate(users).stream()
                .sorted(Comparator.comparingDouble(SellerInformationDTO::getRate)
                        .reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}
