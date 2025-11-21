package controllers.comments;

import entity.Comment;
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
    public Comment addComment(
            @PathVariable("userId") Long userId,
            @RequestParam("request") String request,
            @RequestParam("rate") Integer rate) {

        return commentService.addComment(userId, request,rate);

    }
    @GetMapping
    public List<Comment> getCommentsByUser(
            @PathVariable("userId") Long userId) {

        return commentService.findAllByUserId(userId);

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
        if(commentService.deleteComment(userId, commentId)) {
            return "Deleted";
        } else {
            return "Not deleted";

        }

    }
    @PutMapping("/{commentId}")
    public Comment updateComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId,
            @RequestParam("message") String message,
            @RequestParam("rate") Integer rate) {

        return commentService.updateComment(userId, commentId, message,rate);
    }

}
