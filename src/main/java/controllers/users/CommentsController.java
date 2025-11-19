package controllers.users;

import entity.Comment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/users")
public class CommentsController {
    public String addComment(@PathVariable Integer id, Comment comment) {
        return comment.message();
    }
}
