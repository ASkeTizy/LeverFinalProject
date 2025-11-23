package o.e.dao;

import o.e.entity.Game;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameDao {
    private final ConnectionProvider provider;

    public GameDao(ConnectionProvider provider) {

        this.provider = provider;
    }

    public Game findGameByName(String name) {
        String sql = "SELECT * FROM game WHERE name = ?";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            List<Game> games = new ArrayList<>();
            while (rs.next()) {
                games.add(new Game(
                                rs.getInt("id"),
                                rs.getString("name")

                        )
                );

            }
            if (games.isEmpty()) return null;
            if (games.size() == 1) {
                return games.getFirst();
            } else throw new SQLException("collision exception");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Game createGame(String name) {
        String sql = "INSERT INTO game (name) VALUES (?)";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        return new Game(id, name);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
