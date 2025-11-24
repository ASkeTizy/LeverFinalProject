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

//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    public void sendVerificationEmail(String to, String code) {
        // Create a new MimeMessage
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//            // Use MimeMessageHelper for easier handling of HTML content
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setTo(to);
//            helper.setSubject("Email Verification");
//
//            // true → send as HTML
//            String htmlContent = "<p>Your verification code is: <b>" + code + "</b></p>";
//            helper.setText(htmlContent, true);
//
        SendGrid sg = new SendGrid(System.getenv("UXF2YES6D1V5TCTGEBTFB819"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody("{ \"personalizations\": [{ \"to\": [{ \"email\": \""+to+"\" }] }], \"from\": { \"email\": \"your_verified_sender@example.com\" }, \"subject\": \"Email Verification\", \"content\": [{ \"type\": \"text/html\", \"value\": \"<p>Your code: <b>"+code+"</b></p>\" }] }");
        try {
            sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Send the message
//            mailSender.send(mimeMessage);

    }
}
