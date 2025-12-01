package o.e;

import jakarta.transaction.Transactional;
import o.e.entity.Comment;
import o.e.entity.Roles;
import o.e.entity.User;
import o.e.repository.CommentRepository;
import o.e.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DbConfig.class})
@WebAppConfiguration
@Transactional
public class AddCommentIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void testSaveAndFindUser() {
        var comment = new Comment(1L,"test",1L,Date.valueOf(LocalDate.now()),1);
        var inserted = commentRepository.save(comment);

        assertTrue(commentRepository.findByAuthorIdAndId(inserted.getAuthorId(),inserted.getId()).isPresent());
    }
}
