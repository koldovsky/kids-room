package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import java.util.UUID;

/**
 * Created by Chack on 21.06.2016.
 */
@Controller
public class UserResendConfirmation {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TokenService tokenService;

    @Log
    private static Logger log;

    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.GET)
    public String sendConfirmation(Model model) {
        model.addAttribute(UserConstants.Entity.USER, new User());
        return UserConstants.Model.RESEND_MAIL_VIEW;
    }

    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.POST)
    public String sendConfirmation(@ModelAttribute(UserConstants.Entity.USER) User currentUser, BindingResult bindingResult) {
        String email = currentUser.getEmail();
        userValidator.validateEmail(email, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.Model.RESEND_MAIL_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();

        try {
            mailService.sendRegisterMessage(MailConstants.CONFIRM_REGISTRATION, user, token);
        } catch (MessagingException | MailSendException e) {
            log.error("Error! Sending email!!!", e);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            return UserConstants.Model.RESEND_MAIL_VIEW;
        }
        tokenService.createToken(token, user);
        return UserConstants.Model.SUCCESS_VIEW;
    }
}
