package o.e.dao;

import o.e.entity.GameObject;
import o.e.entity.SellerInformationDTO;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameObjectDao {
    private final ConnectionProvider provider;

    public GameObjectDao(ConnectionProvider provider) {
        this.provider = provider;
    }

    public List<GameObject> findAllGameObjects() {
        String sql = "SELECT * FROM game_object ";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            List<GameObject> gameObjects = new ArrayList<>();
            while (rs.next()) {
                gameObjects.add(new GameObject(
                                rs.getLong("id"),
                                rs.getString("title"),
                                rs.getString("text"),
                                rs.getInt("game_id"),
                                rs.getLong("user_id"),
                                rs.getDate("created_at"),
                                rs.getDate("updated_at")
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
        String sql = "INSERT INTO game_object (title, text,game_id,user_id, created_at,updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setString(1, gameObject.title());
            stmt.setString(2, gameObject.text());
            stmt.setLong(3, gameObject.gameId());
            stmt.setLong(4, gameObject.userId());
            stmt.setDate(5, gameObject.createdAt());
            stmt.setDate(6, gameObject.updatedAt());

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }


    public boolean deleteGameObject(Long objectId) {
        String sql = "DELETE FROM game_object WHERE id = ? ";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, objectId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameObject updateGameObject(GameObject gameObject) {
        String sql = "UPDATE game_object SET title = ?, text = ?, game_id = ?,updated_at=?  WHERE id = ?";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

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

    public List<SellerInformationDTO> findUsersByGameAndRate(Integer gameId, Integer startRate, Integer endRate) {
        String sql = "SELECT u.id, first_name, last_name, email FROM game_object join comments " +
                "on comments.author_id=game_object.user_id " +
                "join \"user\" as u on u.id=comments.author_id " +
                "WHERE game_id = ? and rate between ? and ? ";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            stmt.setInt(2, startRate);
            stmt.setInt(3, endRate);
            ResultSet rs = stmt.executeQuery();
            List<SellerInformationDTO> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new SellerInformationDTO(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email")
                        )
                );

            }
            return users;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
