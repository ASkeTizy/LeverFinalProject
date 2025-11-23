package o.e.service;

import o.e.dao.GameDao;
import o.e.dao.GameObjectDao;
import o.e.dto.GameObjectDTO;
import o.e.entity.GameObject;
import o.e.entity.SellerInformationDTO;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class GameObjectService {
    private final GameObjectDao gameObjectDao;
    private final GameDao gameDao;
    private final CommentService commentService;

    public GameObjectService(GameObjectDao gameObjectDao, GameDao gameDao, CommentService commentService) {
        this.gameObjectDao = gameObjectDao;
        this.gameDao = gameDao;
        this.commentService = commentService;
    }

    private Integer getGameId(String name) {
        var game = gameDao.findGameByName(name);
        if (game == null) {
            game = gameDao.createGame(name);
        }
        return game.id();
    }

    public void updateObject(Long objectId, GameObjectDTO dto) {
        var gameId = getGameId(dto.gameName());
        gameObjectDao.updateGameObject(new GameObject(objectId,
                dto.message(),
                dto.text(),
                gameId,
                dto.userId(),
                null,
                Date.valueOf(LocalDate.now())
        ));

    }

    public boolean deleteGameObject(Long objectId) {
        return gameObjectDao.deleteGameObject(objectId);
    }

    public GameObject createGameObject(GameObjectDTO dto) {
        var gameId = getGameId(dto.gameName());
        var gameObject = new GameObject(
                0L,
                dto.message(),
                dto.text(),
                gameId,
                dto.userId(), Date.valueOf(LocalDate.now()), null);
        gameObjectDao.createGameObject(gameObject);
        return gameObject;
    }

    public List<GameObject> findGameObjects() {
        return gameObjectDao.findAllGameObjects();
    }

    public List<SellerInformationDTO> getUsersByGameAndRate(String gameName, Integer startRate, Integer endRate) {
        var gameId = getGameId(gameName);
        if (gameId == null) return List.of();
        return commentService.setSellerRate(gameObjectDao.findUsersByGameAndRate(gameId, startRate, endRate));
    }
}
