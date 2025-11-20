package dao;

import entity.Comment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
@Repository
public class CommentDAO {
    Connection connection;
    public CommentDAO() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mydatabase", "user", "secret"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createComment(Comment comment)  {
        String sql = "INSERT INTO comments (message, author_id, created_at) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, comment.message());          // message
            stmt.setLong(2, comment.authorId());                       // author_id
            stmt.setDate(3, comment.created_at()); // created_at

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    public Comment updateComment(Comment newComment,Long oldCommentId) throws SQLException {
        String sql = "UPDATE comments SET message = ?, author_id = ?, created_at = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, newComment.message());          // message
        stmt.setLong(2, newComment.authorId());                       // author_id
        stmt.setDate(3, newComment.created_at()); // created_at

        int rows = stmt.executeUpdate();
        System.out.println("Inserted rows: " + rows);
        return newComment;
    }
}
