package ua.softserveinc.tc.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
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
    private ServletRequest request;

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
            while (!sended) {
                synchronized (message) {
                    mailSender.send(message);
                    sended = true;
                }
            }
        } catch (MailException e) {
            //TODO
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String EMAIL_TEMPLATE = "/emailTemplate/";
    private String HTTP = "http://";
    private String LINK = "link";


    @Override
    public void sendRegisterMessage(String subject, User user, String token) {
        String link = HTTP + request.getServerName()  + "/confirm?token=" + token;

        Map<String, Object> model = new HashMap<>();
        model.put(UsersConst.USER, user);
        model.put(LINK, link);

        sendMessage(user, subject, getTextMessage("confirmEmail.vm", model));
    }

    @Override
    public void sendChangePassword(String subject, User user, String token) {
        String link = HTTP + request.getServerName()
                + "/changePassword?token=" + token;

        Map<String, Object> model = new HashMap<>();
        model.put(UsersConst.USER, user);
        model.put(LINK, link);

        sendMessage(user, subject, getTextMessage("changePassword.vm", model));
    }

    @Override
    public void sendPaymentInfo(User user, String subject, Long sumTotal)
    {
        String link = HTTP + request.getServerName()
                + "/mybookings";
        Map<String, Object> model = new HashMap<>();
        model.put(UsersConst.USER, user);
        model.put("sumTotal", sumTotal);
        model.put(LINK, link);

        sendMessage(user, subject, getTextMessage("paymentInfo.vm", model));
    }


    @Override
    public void buildConfirmRegisterManager(String subject, User manager, String token) {

        String link = HTTP +request.getServerName()  + "/confirm-manager?token=" + token;

        Map<String, Object> model = new HashMap<>();
        model.put("manager", manager);
        model.put(LINK, link);

        sendMessage(manager, subject, getTextMessage("confirmManager.vm", model));
    }

    private String getTextMessage(String template, Map<String, Object> model){
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                EMAIL_TEMPLATE +template, "UTF-8", model);
    }
}
