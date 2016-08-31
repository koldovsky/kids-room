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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.softserveinc.tc.constants.MailConstants;
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

import javax.mail.MessagingException;
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

    @Log
    private static Logger log;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute(UserConstants.Entity.USER, new User());
        return UserConstants.Model.REGISTRATION_VIEW;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(UserConstants.Entity.USER) User user, BindingResult bindingResult) {
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

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(@RequestParam(TokenConstants.TOKEN) String sToken) {
        Token token = tokenService.findByToken(sToken);
        User user = token.getUser();
        user.setConfirmed(true);
        userService.update(user);
        tokenService.delete(token);

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/login");
        model.getModelMap().addAttribute("confirm", ValidationConstants.ConfigFields.SUCCESSFUL_CONFIRMATION_MESSAGE);
        return model;
    }
}

