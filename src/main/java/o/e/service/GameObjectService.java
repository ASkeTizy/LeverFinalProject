package o.e.service;

import o.e.dto.GameObjectDTO;
import o.e.entity.Game;
import o.e.entity.GameObject;
import o.e.entity.SellerInformationDTO;
import o.e.exception.ResourceNotFoundException;
import o.e.repository.GameObjectRepository;
import o.e.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class GameObjectService {
    private final GameObjectRepository gameObjectRepository;
    private final GameRepository gameRepository;
    private final CommentService commentService;

    public GameObjectService(GameObjectRepository gameObjectRepository, GameRepository gameRepository, CommentService commentService) {
        this.gameObjectRepository = gameObjectRepository;
        this.gameRepository = gameRepository;
        this.commentService = commentService;
    }

    private Integer getGameId(String name) {
        var game = gameRepository.findByName(name);
        if (game == null) {
            game = gameRepository.save(new Game(null,name));
        }
        return game.getId();
    }

    public GameObject updateObject(Long objectId, GameObjectDTO dto) {
        var gameId = getGameId(dto.gameName());
        return gameObjectRepository.save(new GameObject(objectId,
                dto.message(),
                dto.text(),
                gameId,
                dto.userId(),
                null,
                Date.valueOf(LocalDate.now())
        ));

    }

    public GameObject deleteGameObject(Long objectId) {
        return gameObjectRepository.findById(objectId)
                .map(obj -> {
                    gameObjectRepository.delete(obj);
                    return obj;
                }).orElseThrow(()->new ResourceNotFoundException("Game object not found"));
    }

    public GameObject createGameObject(GameObjectDTO dto) {
        var gameId = getGameId(dto.gameName());
        var gameObject = new GameObject(
                0L,
                dto.message(),
                dto.text(),
                gameId,
                dto.userId(), Date.valueOf(LocalDate.now()), null);
        gameObjectRepository.save(gameObject);
        return gameObject;
    }

    public List<GameObject> findGameObjects() {
        return gameObjectRepository.findAll();
    }

    public List<SellerInformationDTO> getUsersByGameAndRate(String gameName, Integer startRate, Integer endRate) {
        var gameId = getGameId(gameName);
        if (gameId == null) return List.of();
        return commentService.setSellerRate(gameObjectRepository.findUsersByGameAndRate(gameId, startRate, endRate));
    }
}
