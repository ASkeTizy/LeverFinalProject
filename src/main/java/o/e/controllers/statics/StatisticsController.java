package o.e.controllers.statics;

import o.e.entity.SellerInformationDTO;
import o.e.service.CommentService;
import o.e.service.GameObjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {
    private final CommentService commentService;
    private final GameObjectService gameObjectService;

    public StatisticsController(CommentService commentService, GameObjectService gameObjectService) {
        this.commentService = commentService;
        this.gameObjectService = gameObjectService;
    }

    @GetMapping("/{userId}/rate")
    public Double getSellerRating(
            @PathVariable("userId") Long userId) {

        return commentService.calculateSellerRating(userId);

    }
    @GetMapping("/top/sellers")
    public List<SellerInformationDTO> getTopSellers() {

        return commentService.getTopSellers();

    }

}
