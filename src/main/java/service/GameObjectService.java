package service;

import dao.CommentDAO;
import dao.GameObjectDao;
import entity.GameObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class GameObjectService {
    private final GameObjectDao gameObjectDao;

    public GameObjectService(GameObjectDao gameObjectDao) {
        this.gameObjectDao = gameObjectDao;
    }

    public void updateObject(Long objectId, String message,String text, Integer authorId,Long userId) {
        gameObjectDao.updateGameObject(new GameObject(objectId,message,text,authorId,userId, null,Date.valueOf(LocalDate.now())));

    }
    public boolean deleteGameObject(Long objectId) {
       return gameObjectDao.deleteGameObject(objectId);
    }

        public void createGameObject(String message,String text, Integer authorId,Long userId) {
        gameObjectDao.createGameObject(new GameObject(0L,message,text,authorId,userId, Date.valueOf(LocalDate.now()),null));
    }
}
