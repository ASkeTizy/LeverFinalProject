package service;

import dao.GameDao;
import dao.GameObjectDao;
import dto.GameObjectDTO;
import entity.GameObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class GameObjectService {
    private final GameObjectDao gameObjectDao;
    private final GameDao gameDao;
    public GameObjectService(GameObjectDao gameObjectDao, GameDao gameDao) {
        this.gameObjectDao = gameObjectDao;
        this.gameDao = gameDao;
    }

    public void updateObject(Long objectId, GameObjectDTO dto) {
        var game = gameDao.findGameByName(dto.gameName());
        if(game == null) {
            gameDao.createGame(dto.gameName());
        }
        gameObjectDao.updateGameObject(new GameObject(objectId, dto.gameName(), commentDTO.te, authorId,userId, null,Date.valueOf(LocalDate.now())));

    }
    public boolean deleteGameObject(Long objectId) {
       return gameObjectDao.deleteGameObject(objectId);
    }

        public void createGameObject(GameObjectDTO gameObjectDTO) {
        gameObjectDao.createGameObject(new GameObject(0L,message,text,authorId,userId, Date.valueOf(LocalDate.now()),null));
    }
}
