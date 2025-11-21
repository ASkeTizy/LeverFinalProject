package controllers.gameobject;

import dto.CommentDTO;
import dto.GameObjectDTO;
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
    public String editObject(@PathVariable Long objectId, @RequestBody GameObjectDTO gameObjectDTO){
        gameObjectService.updateObject(objectId, gameObjectDTO);
    return "Edited object";
    }
    public String createObject(@RequestBody GameObjectDTO gameObjectDTO) {
        gameObjectService.createGameObject(gameObjectDTO);
        return "Object created";
    }
    @DeleteMapping("/{objectId}")
    public String deletObject(@PathVariable Long objectId) {
        gameObjectService.deleteGameObject(objectId);
        return "Object created";
    }
}
