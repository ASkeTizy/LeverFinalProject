package o.e;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import o.e.entity.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import jakarta.persistence.*;
import java.util.Map;

public class UserRepositoryIT {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;


    @BeforeAll
    static void setUpAll() {
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(o.e.entity.User.class);
        var props = Map.of(
                "jakarta.persistence.jdbc.url", "jdbc:postgresql://172.19.192.2:5433/gamestore",
                "jakarta.persistence.jdbc.user", "user",
                "jakarta.persistence.jdbc.password", "new_secret",
                "jakarta.persistence.jdbc.driver", "org.postgresql.Driver",
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                "hibernate.hbm2ddl.auto", "create-drop",
                "hibernate.show_sql", "true"
                );

        cfg.buildSessionFactory();
        emf = new HibernatePersistenceProvider()
                .createEntityManagerFactory("testPU", props);
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @AfterAll
    static void cleanUp() {
        emf.close();
    }

    @Test
    void testPersistAndFind() {
        tx.begin();
        User user = new User();
        user.setEmail("artemnevmerz@gmail.com");
        user.setFirstName("artiom");
        user.setLastName("Nevm");
        user.setPassword("1234");
        em.persist(user);
        tx.commit();

        User found = em.find(User.class, user.getId());
        Assertions.assertEquals(user.getId(), found.getId());
        Assertions.assertEquals(user.getEmail(), found.getEmail());
        Assertions.assertEquals(user.getFirstName(), found.getFirstName());
        Assertions.assertEquals(user.getLastName(), found.getLastName());
        Assertions.assertEquals(user.getPassword(), found.getPassword());
    }
}
