package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.ModelConstants.TokenConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.UserValidator;

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
    @Qualifier(UsersConst.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute(UsersConst.USER, new User());
        return UsersConst.FORGOT_PASSWORD_VIEW;
    }

   @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(UsersConst.USER)User modelUser, BindingResult bindingResult) {
        String email = modelUser.getEmail();
        userValidator.validateEmail(email, bindingResult);
        if (bindingResult.hasErrors()) {
            return UsersConst.FORGOT_PASSWORD_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendChangePassword(UsersConst.CHANGE_PASS, user, token);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model, @RequestParam(TokenConst.TOKEN) String token) {
        Token verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        tokenService.delete(verificationToken);
        model.addAttribute(UsersConst.USER, user);
        return UsersConst.UPDATE_PASS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String savePassword(@ModelAttribute(UsersConst.USER)User modelUser, BindingResult bindingResult, Errors errors) {
        userValidator.validatePassword(modelUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return UsersConst.UPDATE_PASS_VIEW;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        user.setConfirmed(true);
        userService.update(user);
        return UsersConst.LOGIN_VIEW;
    }
}
