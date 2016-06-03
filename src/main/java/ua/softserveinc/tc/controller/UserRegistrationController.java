package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.ModelConstants.TokenConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.UserValidator;

import java.util.UUID;

/**
 * Created by Chak on 27.05.2016.
 */

@Controller
public class UserRegistrationController {

    @Autowired
    BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private TokenService tokenService;

    @Secured({"ROLE_ANONYMOUS"})
    @RequestMapping(value = "/login ", method = RequestMethod.GET)
    public String login(Model model) {
        return UsersConst.LOGIN_VIEW;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute(UsersConst.USER, new User());
        return UsersConst.REGISTRATION_VIEW;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(UsersConst.USER) User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return UsersConst.REGISTRATION_VIEW;
        }
        user.setRole(Role.USER);
        user.setConfirmed(false);
        user.setActive(true);
        userService.create(user);

        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendRegisterMessage(UsersConst.CONFIRM_REGISTRATION, user, token);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam(TokenConst.TOKEN) String sToken) {
        Token token = tokenService.findByToken(sToken);
        User user = token.getUser();
        user.setConfirmed(true);
        userService.update(user);
        tokenService.delete(token);
        return "redirect:/login";
    }

    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.GET)
    public String sendConfirmation(Model model){
        model.addAttribute(UsersConst.USER, new User());
        return UsersConst.RESEND_MAIL_VIEW;
    }

    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.POST)
    public String sendConfirmation(@ModelAttribute(UsersConst.USER)User modelUser, BindingResult bindingResult) {
        String email = modelUser.getEmail();
        userValidator.validateEmail(email, bindingResult);
        if (bindingResult.hasErrors()) {
            return UsersConst.RESEND_MAIL_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendRegisterMessage(UsersConst.CONFIRM_REGISTRATION, user, token);
        return UsersConst.SUCCESS_VIEW;
    }
}

