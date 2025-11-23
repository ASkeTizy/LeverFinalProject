package o.e;

import liquibase.integration.spring.SpringLiquibase;
import o.e.dao.ConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "o.e"
})
public class AppConfig {

}