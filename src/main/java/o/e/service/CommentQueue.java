package o.e.service;

import o.e.dto.CommentDTO;
import o.e.entity.Comment;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommentQueue {
    private static Integer ID = 0;
    private final Map<Integer, Comment> comments = new HashMap<>();

    public void addComment(Long userId, CommentDTO commentDTO) {
        ++ID;
        comments.put(ID, new Comment(0L,
                commentDTO.message(),
                userId,
                Date.valueOf(LocalDate.now()),
                commentDTO.rate()));
    }
    public void removeComment(Long commentId) {
        comments.remove(commentId);
    }

    public Map<Integer, Comment> getComments() {
        return comments;
    }
}
