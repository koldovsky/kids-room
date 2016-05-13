package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.User;

/**
 * Created by Chak on 10.05.2016.
 */
public interface MailService {
    public void sendMessage(User user, String subject, String text);
    public String buildRegisterMessage(User user, String token);
}
