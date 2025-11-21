package dao;

import entity.Comment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDAO {
    Connection connection = ConnectionManager.getDatabaseConnection();

    public CommentDAO() {

    }

    public List<Comment> findAllCommentsByUserId(Long userId) {
        String sql = "SELECT * FROM comment WHERE author_id = ? and id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                comments.add(new Comment(
                                rs.getLong("id"),
                                rs.getString("message"),
                                rs.getLong("author_id"),
                                rs.getDate("created_at"),
                                rs.getInt("rate")
                        )
                );

            }

            return comments;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createComment(Comment comment) {
        String sql = "INSERT INTO comments (message, author_id, created_at,rate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, comment.message());          // message
            stmt.setLong(2, comment.authorId());                       // author_id
            stmt.setDate(3, comment.created_at()); // created_at
            stmt.setInt(4, comment.rate()); // created_at

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public Comment findByUserIdAndCommentId(Long userId, Long commentId) {
        String sql = "SELECT * FROM comment WHERE author_id = ? and id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, userId);          // message
            stmt.setLong(2, commentId);   // created_at
            ResultSet rs = stmt.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                comments.add(new Comment(
                                rs.getLong("id"),
                                rs.getString("message"),
                                rs.getLong("author_id"),
                                rs.getDate("created_at"),
                                rs.getInt("rate")
                        )
                );

            }
            if (comments.size() == 1) {
                return comments.getFirst();
            } else throw new SQLException("collision exception");
//            return new Comment(id,message)
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Comment updateComment(Comment newComment)  {
        String sql = "UPDATE comments SET message = ?, author_id = ?, created_at = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, newComment.message());
            stmt.setLong(2, newComment.authorId());
            stmt.setDate(3, newComment.created_at());
            stmt.setInt(4, newComment.rate());

            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
            return newComment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteComment(Long userId, Long commentId) {
        String sql = "DELETE FROM comments WHERE author_id = ? and comment_id=?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, commentId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
