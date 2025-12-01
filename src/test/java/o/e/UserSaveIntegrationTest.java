package o.e;

import jakarta.transaction.Transactional;
import o.e.dto.UserDTO;
import o.e.entity.Roles;
import o.e.entity.User;
import o.e.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DbConfig.class})
@Transactional
@WebAppConfiguration
public class UserSaveIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User(5L, "artiom", "nev"
                ,"1234",
                "artemnevmerz@gmail.com", Date.valueOf(LocalDate.now()), Roles.ADMIN);
        userRepository.save(user);
        assertTrue(userRepository.findByEmail("artemnevmerz@gmail.com").isPresent());
    }
}
