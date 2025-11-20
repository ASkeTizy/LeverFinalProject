package controllers.users;

import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CommentService;

import java.util.List;

@RestController

@RequestMapping("/users/{userId}/comments")
public class CommentsController {

    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public String addComment(
            @PathVariable("userId") Long userId,
            @RequestBody String request) {

                commentService.addComment(userId, request);
        return "Coomet created";
    }
    @GetMapping("")
    public String getCommentsByUser(
            @PathVariable("userId") Long userId) {

        List<Comment> comments = commentService.findAllByUserId(userId);

//        if (comments.isEmpty()) {
//            return ResponseEntity.noContent().build(

        return "Get comments";
    }

    @GetMapping("/{commentId}")
    public String getCommentByUserAndId(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId) {
        commentService.findByUserIdAndCommentId(userId, commentId);
        return "Get comment";
    }
    @DeleteMapping("/{commentId}")
    public String deleteComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
        return "Deleted";

    }
    @PutMapping("/{commentId}")
    public Comment updateComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId,
            @RequestBody String message) {

        return commentService.updateComment(userId, commentId, message);
    }

}
