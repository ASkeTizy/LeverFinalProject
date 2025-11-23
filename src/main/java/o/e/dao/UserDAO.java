package o.e.dao;

import o.e.entity.Roles;
import o.e.entity.SellerInformationDTO;
import o.e.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {
    private final ConnectionProvider provider;

    public UserDAO(ConnectionProvider provider) {
        this.provider = provider;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO \"user\" ( first_name,last_name, password,email,created_at,role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setString(1, user.firstName());
            stmt.setString(2, user.lastName());
            stmt.setString(3, user.password());
            stmt.setString(4, user.email());
            stmt.setDate(5, user.createdAt());
            stmt.setString(6, user.role().name());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public List<SellerInformationDTO> findUsersWithComments() {
        String sql = "SELECT distinct u.id, first_name, last_name, email FROM \"user\" as u  join comments as c on u.id=c.author_id";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

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

    public User findUserById(Long userId) {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getDate("created_at"),
                                Roles.valueOf(rs.getString("role"))
                        )
                );

            }
            if (users.size() == 1) {
                return users.getFirst();
            } else return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
