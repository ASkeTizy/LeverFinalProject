package service;

import dao.CommentDAO;
import entity.Comment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class CommentService {
private final CommentDAO commentDAO;

    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public Comment findByUserIdAndCommentId(Long userId, Long commentId) {
        return null;
    }

    public Comment addComment(Long userId, String request) {
        var comment = new Comment(0L,request,userId, Date.valueOf(LocalDate.now()));
        commentDAO.createComment(comment);
        return comment;
    }

    public List<Comment> findAllByUserId(Long userId) {
        return List.of();
    }

    public boolean deleteComment(Long userId, Long commentId) {
        return true;
    }

    public Comment updateComment(Long userId, Long commentId, String message) {
        return null;
    }
}
