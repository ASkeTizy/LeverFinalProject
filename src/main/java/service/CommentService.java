package service;

import dao.CommentDAO;
import dto.CommentDTO;
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
       return commentDAO.findByUserIdAndCommentId(userId,commentId);
    }

    public Comment addComment(Long userId, CommentDTO commentDTO) {
        var comment = new Comment(0L,commentDTO.message(),userId, Date.valueOf(LocalDate.now()),commentDTO.rate());
        commentDAO.createComment(comment);
        return comment;
    }

    public List<Comment> findAllByUserId(Long userId) {
        return commentDAO.findAllCommentsByUserId(userId);
    }

    public boolean deleteComment(Long userId, Long commentId) {
        return commentDAO.deleteComment(userId,commentId);
    }

    public Comment updateComment(Long userId, Long commentId, CommentDTO commentDTO) {
        return commentDAO.updateComment(new Comment(commentId,commentDTO.message(),userId,Date.valueOf(LocalDate.now()),commentDTO.rate()));
    }
}
