package com.example.stay_mate.service.contact;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String emailAddress, String emailSubject, String emailText) {
        try {
            MimeMessagePreparator mimeMessagePreparator =
                    mimeMessage -> {
                        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
                        mimeMessage.setSubject(emailSubject);
                        mimeMessage.setText(emailText, "UTF-8", "html");
                    };
            javaMailSender.send(mimeMessagePreparator);
            log.info("E-mail sent to: " + emailAddress);
        } catch (Exception e) {
            log.error("Error sending e-mail to: " + emailAddress, e);
        } finally {
            // Zárd le a JavaMailSender-t, ha szükséges
        }
    }

    public Object getLog() {
        // Getter for the logger
            return log;
        }
}