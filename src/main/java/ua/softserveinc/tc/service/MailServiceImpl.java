package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Chak on 10.05.2016.
 */

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async()
    @Override
    public void sendMessage(User user, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        boolean sended = false;
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(user.getEmail());
            helper.setText(text, true);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            //TODO
        }

        while (!sended) {
            try {
                synchronized (message) {
                    mailSender.send(message);
                    sended = true;
                }
            } catch (MailException e) {
                //TODO
            }
        }
    }

}
