package dao;

import entity.Game;
import entity.GameObject;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class GameDao {
    Connection connection = ConnectionManager.getDatabaseConnection();
    public Game findGameByName(String name) {
        String sql = "SELECT * FROM games WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            List<Game> games = new ArrayList<>();
            while (rs.next()) {
                games.add(new Game(
                                rs.getInt("id"),
                                rs.getString("title")

                        )
                );

            }
            if (games.size() == 1) {
                return games.getFirst();
            } else throw new SQLException("collision exception");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
