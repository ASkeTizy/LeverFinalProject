package dao;

import entity.Comment;
import entity.GameObject;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameObjectDao {
    Connection connection = ConnectionManager.getDatabaseConnection();

    public List<GameObject> findAllGameObjects() {
        String sql = "SELECT * FROM gameobjects ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            List<GameObject> gameObjects = new ArrayList<>();
            while (rs.next()) {
                gameObjects.add(new GameObject(
                                rs.getLong("id"),
                                rs.getString("title"),
                                rs.getString("text"),
                                rs.getInt("gameId"),
                                rs.getLong("userId"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt")
                        )
                );

            }

            return gameObjects;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createGameObject(GameObject gameObject) {
        String sql = "INSERT INTO gameobjects (title, text,game_id,user_id, created_at,updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, gameObject.title());          // message
            stmt.setString(2, gameObject.text());                       // author_id
            stmt.setLong(3, gameObject.gameId()); // created_at
            stmt.setLong(4, gameObject.userId()); // created_at
            stmt.setDate(5, gameObject.createdAt()); // created_at
            stmt.setDate(6, gameObject.updatedAt()); // created_at

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }



    public boolean deleteGameObject(Long objectId) {
        String sql = "DELETE FROM gameobject WHERE id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, objectId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameObject updateGameObject( GameObject gameObject) {
        String sql = "UPDATE gameobjects SET title = ?, text = ?, game_id = ?,updated_at=?  WHERE objectId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, gameObject.title());
            stmt.setString(2, gameObject.text());
            stmt.setLong(3, gameObject.gameId());
            stmt.setDate(4, gameObject.updatedAt());
            stmt.setLong(5, gameObject.id());

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
            return gameObject;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
