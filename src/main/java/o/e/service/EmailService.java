package o.e.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {


    public void sendVerificationEmail(String to, String code) {
        final String username = "hero.artem@gmail.com"; // ваш Gmail
        final String appPassword = "fley qpaf fohf cbdi"; // ваш App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

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

