package controllers.gameobject;

import entity.GameObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.GameObjectService;

@RestController

@RequestMapping("/object")
public class GameObjectController {
    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @PutMapping("/{objectId}")
    public String editObject(@PathVariable Long objectId, GameObject gameObject){
        gameObjectService.updateObject(objectId,gameObject);
    return "Edited object";
    }
    public String createObject(GameObject gameObject) {

    }
}
