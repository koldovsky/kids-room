package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import java.util.UUID;

/**
 * Created by Chak on 27.05.2016.
 */

@Controller
public class UserChangePasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier(UserConstants.Model.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @Log
    private static Logger log;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute(UserConstants.Entity.USER, new User());
        return UserConstants.Model.FORGOT_PASSWORD_VIEW;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(UserConstants.Entity.USER) User modelUser, BindingResult bindingResult) {
        String email = modelUser.getEmail();
        userValidator.validateEmail(email, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.Model.FORGOT_PASSWORD_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        try {
            mailService.sendChangePassword(MailConstants.CHANGE_PASS, user, token);
        } catch (MessagingException | MailSendException e) {
            log.error("Error! Sending email!!!", e);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            return UserConstants.Model.FORGOT_PASSWORD_VIEW;
        }
        return UserConstants.Model.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model, @RequestParam(TokenConstants.TOKEN) String token) {

        Token verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        tokenService.delete(verificationToken);
        model.addAttribute(UserConstants.Entity.USER, user);
        return UserConstants.Model.UPDATE_PASS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String savePassword(@ModelAttribute(UserConstants.Entity.USER) User modelUser, BindingResult bindingResult) {
        userValidator.validatePassword(modelUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.Model.UPDATE_PASS_VIEW;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        user.setConfirmed(true);
        userService.update(user);
        return UserConstants.Model.LOGIN_VIEW;
    }
}
