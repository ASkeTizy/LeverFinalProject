package o.e.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {


    public void sendVerificationEmail(String to, String code) {

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody("{ \"personalizations\": [{ \"to\": [{ \"email\": \""+to+"\" }] }], \"from\": { \"email\": \"hero.artem@gmail.com\" }, \"subject\": \"Email Verification\", \"content\": [{ \"type\": \"text/html\", \"value\": \"<p>Your code: <b>"+code+"</b></p>\" }] }");
        try {
            sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
