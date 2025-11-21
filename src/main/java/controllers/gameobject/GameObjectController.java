package controllers.gameobject;

import entity.GameObject;
import org.springframework.web.bind.annotation.*;
import service.GameObjectService;

@RestController

@RequestMapping("/object")
public class GameObjectController {
    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @PutMapping("/{objectId}")
    public String editObject(@PathVariable Long objectId, @PathVariable String message,@PathVariable String text, @PathVariable Integer gameId,
                             @PathVariable Long userId
    ){
        gameObjectService.updateObject(objectId,message,text,gameId,userId);
    return "Edited object";
    }
    public String createObject(@PathVariable String message,@PathVariable String text, @PathVariable Integer gameId,
                               @PathVariable Long userId
                               ) {
        gameObjectService.createGameObject(message,text,gameId,userId);
        return "Object created";
    }
    @DeleteMapping("/{objectId}")
    public String deletObject(@PathVariable Long objectId) {
        gameObjectService.deleteGameObject(objectId);
        return "Object created";
    }
}
