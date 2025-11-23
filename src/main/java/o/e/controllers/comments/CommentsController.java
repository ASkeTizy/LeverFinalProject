package o.e.controllers.comments;

import o.e.dto.CommentDTO;
import o.e.entity.Comment;
import o.e.service.CommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody @Validated CommentDTO commentDTO) {

        if (commentService.addCommentToCheck(userId, commentDTO)) {
            return "Comment added";
        } else return "redirect:/auth/create";

    }

    @GetMapping
    public List<Comment> getCommentsByUser(
            @PathVariable("userId") Long userId) {

        return commentService.findAllByUserId(userId);

    }



    @GetMapping("/{commentId}")
    public Comment getCommentByUserAndId(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId) {
        return commentService.findByUserIdAndCommentId(userId, commentId);

    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId) {
        if (commentService.deleteComment(userId, commentId)) {
            return "Deleted";
        } else {
            return "Not deleted";
        }
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDTO commentDTO) {

        return commentService.updateComment(userId, commentId, commentDTO);
    }

}
