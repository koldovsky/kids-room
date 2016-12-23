package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.constants.LocaleConstants;
import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;


    @Log
    private static Logger log;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute(UserConstants.Entity.USER, new User());
        return UserConstants.Model.REGISTRATION_VIEW;
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute(UserConstants.Entity.USER) User user,
                           BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.Model.REGISTRATION_VIEW;
        }
        user.setRole(Role.USER);
        user.setConfirmed(false);
        user.setActive(true);
        String token = UUID.randomUUID().toString();
        try {
            mailService.sendRegisterMessage(MailConstants.CONFIRM_REGISTRATION, user, token);
        } catch (MessagingException | MailSendException e) {
            log.error("Error! Sending email!!!", e);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            return UserConstants.Model.REGISTRATION_VIEW;
        }
        userService.createWithEncoder(user);
        tokenService.createToken(token, user);
        return UserConstants.Model.SUCCESS_VIEW;
    }

    @GetMapping("/confirm")
    public ModelAndView confirmRegistration(@RequestParam(TokenConstants.TOKEN) String sToken) {
        Token token = tokenService.findByToken(sToken);
        User user = token.getUser();
        user.setConfirmed(true);
        userService.update(user);
        tokenService.delete(token);
        Locale locale = (Locale)request.getSession().getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        if (locale == null)
            locale = request.getLocale();
        ModelAndView model = new ModelAndView();
        model.setViewName(UserConstants.Model.LOGIN_VIEW);
        model.getModelMap().addAttribute(ReportConstants.CONFIRM_ATTRIBUTE,
                messageSource.getMessage(ReportConstants.CONFIRM_MESSAGE, null, locale));
        return model;
    }
}

