package ua.softserveinc.tc.service.impl;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.util.ApplicationConfigurator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chak on 10.05.2016.
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private ServletRequest request;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private ApplicationConfigurator configurator;

    @Async()
    @Override
    public void sendMessage(String email, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        boolean sended = false;
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);

            while (!sended) {
                synchronized (message) {
                    mailSender.send(message);
                    sended = true;
                }
            }
        } catch (MailException e) {
            //TODO
        } catch (MessagingException e) {
            //TODO
        }
    }

    @Override
    public void sendRegisterMessage(String subject, User user, String token) {

        Map<String, Object> model = getModelWithUser(user);
        model.put(MailConstants.LINK, getLink(MailConstants.CONFIRM_USER_LINK).append(token));

        sendMessage(user.getEmail(), subject, getTextMessage(MailConstants.CONFIRM_USER_VM, model));
    }

    @Override
    public void sendChangePassword(String subject, User user, String token) {

        Map<String, Object> model = getModelWithUser(user);
        model.put(MailConstants.LINK, getLink(MailConstants.CHANGE_PASS_LINK).append(token));

        sendMessage(user.getEmail(), subject, getTextMessage(MailConstants.CHANGE_PASS_VM, model));
    }

    @Override
    public void sendPaymentInfo(User user, String subject, Long sumTotal) {
        Map<String, Object> model = getModelWithUser(user);
        model.put(ReportConst.SUM_TOTAL, sumTotal);
        model.put(MailConstants.LINK, MailConstants.HTTP +
                configurator.getServerName() + MailConstants.MY_BOOKINGS_LINK);

        sendMessage(user.getEmail(), subject, getTextMessage(MailConstants.PAYMENT_VM, model));
    }

    @Override
    public void buildConfirmRegisterManager(String subject, User user, String token) {

        Map<String, Object> model = getModelWithUser(user);
        model.put(MailConstants.LINK, getLink(MailConstants.CONFIRM_MANAGER_LINK).append(token));

        sendMessage(user.getEmail(), subject, getTextMessage(MailConstants.CONFIRM_MANAGER_VM, model));
    }

    private String getTextMessage(String template, Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                MailConstants.EMAIL_TEMPLATE + template, MailConstants.UTF_8, model);
    }

    private Map<String, Object> getModelWithUser(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put(UserConstants.USER, user);
        return model;
    }

    private StringBuilder getLink(String partOfLink) {
        return new StringBuilder(MailConstants.HTTP).append(request.getServerName()).append(partOfLink);
    }
}
