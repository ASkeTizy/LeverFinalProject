package o.e.repository;

import o.e.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{


    List<Comment> findAllByAuthorId(Long authorId);

    Optional<Comment> findByAuthorIdAndId(Long authorId, Long id);


}
