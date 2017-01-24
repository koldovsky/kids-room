package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.*;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;



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
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    @Qualifier(UserConstants.Model.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @Log
    private static Logger log;

    @GetMapping("/resetPassword")
    public String changePassword(Model model) {
        model.addAttribute(UserConstants.Entity.USER, new User());
        return UserConstants.Model.FORGOT_PASSWORD_VIEW;
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute(UserConstants.Entity.USER) User modelUser,
                                BindingResult bindingResult) {
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

    @GetMapping("/changePassword")
    public String changePassword(Model model, @RequestParam(TokenConstants.TOKEN) String token) {

        Token verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        tokenService.delete(verificationToken);
        model.addAttribute(UserConstants.Entity.USER, user);
        return UserConstants.Model.UPDATE_PASS_VIEW;
    }

    @PostMapping("/changePassword")
    public ModelAndView savePassword(@ModelAttribute(UserConstants.Entity.USER) User modelUser,
                               BindingResult bindingResult) {
        userValidator.validatePassword(modelUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView(UserConstants.Model.UPDATE_PASS_VIEW);
        }
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(email);
        if(user == null)
            return new ModelAndView(UserConstants.Model.ENTRY_POINT);
        user.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        user.setConfirmed(true);
        userService.update(user);
        Locale locale = (Locale)request.getSession().getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        if (locale == null)
            locale = request.getLocale();
        ModelAndView model = new ModelAndView(UserConstants.Model.LOGIN_VIEW);
        model.getModelMap().addAttribute(ReportConstants.CONFIRM_ATTRIBUTE,
                messageSource.getMessage(ReportConstants.CHANGE_PASSWORD_SUCCESS_MESSAGE, null, locale));
        return model;
    }
}
