package o.e;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class RedisConfig {
//    @Bean
//    public SendGrid javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.sendgrid.net");
//        mailSender.setPort(465);
//        mailSender.setUsername("apikey"); // literal string "apikey"
//        mailSender.setPassword("UXF2YES6D1V5TCTGEBTFB819");
//
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.starttls.enable", "true");   // STARTTLS upgrade
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");  // force modern TLS
//        props.put("mail.debug", "true");
//        SendGrid sg = new SendGrid(System.getenv("UXF2YES6D1V5TCTGEBTFB819"));
//        Request request = new Request();
//        request.setMethod(Method.POST);
//        request.setEndpoint("mail/send");
//        request.setBody("{ \"personalizations\": [{ \"to\": [{ \"email\": \""+to+"\" }] }], \"from\": { \"email\": \"your_verified_sender@example.com\" }, \"subject\": \"Email Verification\", \"content\": [{ \"type\": \"text/html\", \"value\": \"<p>Your code: <b>"+code+"</b></p>\" }] }");
//        try {
//            sg.api(request);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return sg;
//    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("172.19.192.1", 6379);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }
}
