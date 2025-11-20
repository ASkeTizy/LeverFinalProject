import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "controllers",
        "service"
}) // Replace with your actual controller package
public class AppConfig {
    // Additional configurations or bean definitions can go here
}