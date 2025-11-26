package o.e.controllers.gameobject;

import o.e.dto.GameObjectDTO;
import o.e.entity.SellerInformationDTO;
import o.e.entity.GameObject;
import o.e.service.GameObjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/object")
public class GameObjectController {
    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @GetMapping
    public List<GameObject> getAllObject() {
        return gameObjectService.findGameObjects();

    }

    @PutMapping("/{objectId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER','ROLE_ADMIN')")
    public GameObject editObject(@PathVariable("objectId") Long objectId, @RequestBody GameObjectDTO gameObjectDTO) {
       return gameObjectService.updateObject(objectId, gameObjectDTO);

    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SELLER','ROLE_ADMIN')")
    public GameObject createObject(@RequestBody GameObjectDTO gameObjectDTO) {

        return gameObjectService.createGameObject(gameObjectDTO);
    }

    @DeleteMapping("/{objectId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER','ROLE_ADMIN')")
    public GameObject deleteObject(@RequestBody Long objectId) {
        return gameObjectService.deleteGameObject(objectId);
    }

    @GetMapping("{gameId}/{startRate}/{endRate}")
    public List<SellerInformationDTO> getGameObject(@PathVariable("gameId") String gameName,
                                                    @PathVariable("startRate") Integer startRate,
                                                    @PathVariable("endRate") Integer endRate) {

        return gameObjectService.getUsersByGameAndRate(gameName, startRate, endRate);

    }
}
