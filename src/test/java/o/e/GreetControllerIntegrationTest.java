package o.e;

import o.e.entity.Roles;
import o.e.entity.User;
import o.e.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DbConfig.class})
public class GreetControllerIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User(null, "artiom", "nev",
                "artemnevmerzh@gmail.com",
                "1234", Date.valueOf(LocalDate.now()), Roles.ADMIN);
        userRepository.save(user);
        System.out.println("erofkop;ergk");
        assertTrue(userRepository.findByEmail("artemnevmerzh@gmail.com").isPresent());
    }
}
