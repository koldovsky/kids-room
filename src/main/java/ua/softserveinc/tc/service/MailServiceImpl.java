package ua.softserveinc.tc.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import ua.softserveinc.tc.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chak on 10.05.2016.
 */

@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private ServletContext context;

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

    @Override
    public void sendPaymentInfo(User user, String subject, Long sumTotal)
    {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("sumTotal", sumTotal);

        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "/emailTemplate/paymentInfo.vm", "UTF-8", model);

        sendMessage(user, subject, text);
    }

    @Override
    public void sendRegisterMessage(String subject, User user, String token) {
//        String link = "http://" + context.getVirtualServerName() + ":8080" + context.getContextPath()
//        + "/confirm?token=" + token;
        String link = "http://localhost:8080/home" + "/confirm?token=" + token;

        Map model = new HashMap();
        model.put("user", user);
        model.put("link", link);

        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "/emailTemplate/confirmEmail.vm", "UTF-8", model);
        sendMessage(user, subject, text);
    }

    @Override
    public void buildConfirmRegisterManager(String subject, User manager, String token) {

        String link = "http://localhost:8080/home" + "/confirm-manager?token=" + token;

        Map model = new HashMap();
        model.put("manager", manager);
        model.put("link", link);

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "/emailTemplate/confirmManager.vm", "UTF-8", model);

        sendMessage(manager, subject, text);
    }

    @Override
    public void sendChangePassword(String subject, User user, String token) {
//        String link = "http://" + context.getVirtualServerName() + context.getContextPath()
//        + "/changePassword?id=" + user.getId() + "&token=" + token;
        String link = "http://localhost:8080/home" + "/changePassword?id=" + user.getId() + "&token=" + token;

        Map model = new HashMap();
        model.put("user", user);
        model.put("link", link);

        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "/emailTemplate/changePassword.vm", "UTF-8", model);
        sendMessage(user, subject, text);
    }

}
