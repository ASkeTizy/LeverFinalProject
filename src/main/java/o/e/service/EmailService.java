package o.e.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@PropertySource("classpath:application.properties")
public class EmailService {

    private final Environment env;

    public EmailService(Environment env) {
        this.env = env;
    }
    public void sendVerificationEmail(String to, String code) {
        final String username =  env.getProperty("mail.username"); // ваш Gmail
        final String appPassword =  env.getProperty("mail.password"); // ваш App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable")); // TLS
        props.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", env.getProperty("mail.smtp.port"));

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Тестовое письмо");
            message.setText("Привет! Это письмо отправлено через Gmail SMTP с App Password."+code);

            Transport.send(message);
            System.out.println("Письмо отправлено успешно!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}

