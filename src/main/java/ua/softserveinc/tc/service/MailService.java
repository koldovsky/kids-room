package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.User;

import javax.mail.MessagingException;

/**
 * Created by Chak on 10.05.2016.
 */
public interface MailService {

    void sendMessage(String email, String subject, String text) throws MessagingException;

    void sendPaymentInfo(User user, String subject, Long sumTotal) throws MessagingException;

    void sendRegisterMessage(String subject, User user, String token) throws MessagingException;

    void sendChangePassword(String subject, User manager, String token) throws MessagingException;

    void buildConfirmRegisterManager(String subject, User user, String token) throws MessagingException;
}
