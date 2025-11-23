package o.e.dao;

import o.e.entity.Comment;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDAO {

    private final ConnectionProvider provider;

    public CommentDAO(ConnectionProvider provider) {

        this.provider = provider;
    }

    public List<Comment> findAllCommentsByUserId(Long userId) {
        String sql = "SELECT * FROM comments WHERE author_id = ?";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

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
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Comment createComment(Comment comment) {
        String sql = "INSERT INTO comments (message, author_id, created_at,rate) VALUES (?, ?, ?,?)";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, comment.message());          // message
            stmt.setLong(2, comment.authorId());                       // author_id
            stmt.setDate(3, comment.created_at()); // created_at
            stmt.setInt(4, comment.rate()); // created_at

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        Long id = rs.getLong(1);
                        return new Comment(id, comment.message(), comment.authorId(), comment.created_at(), comment.rate());
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;

    }

    public Comment findByUserIdAndCommentId(Long userId, Long commentId) {
        String sql = "SELECT * FROM comments WHERE author_id = ? and id = ?";

        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

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

    public Comment updateComment(Comment newComment) {
        String sql = "UPDATE comments SET message = ?, author_id = ?, rate = ? WHERE id = ?";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {

            stmt.setString(1, newComment.message());
            stmt.setLong(2, newComment.authorId());
            stmt.setInt(3, newComment.rate());
            stmt.setLong(4, newComment.id());

            int rows = stmt.executeUpdate();
            System.out.println("Updated rows: " + rows);
            return newComment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteComment(Long userId, Long commentId) {
        String sql = "DELETE FROM comments WHERE author_id = ? and id=?";
        try (PreparedStatement stmt = provider.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, commentId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
