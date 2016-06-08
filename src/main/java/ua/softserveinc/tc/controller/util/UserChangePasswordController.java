package ua.softserveinc.tc.controller.util;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.ModelConstants.TokenConst;
import ua.softserveinc.tc.constants.UserConstants;
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
    @Qualifier(UserConstants.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute(UserConstants.USER, new User());
        return UserConstants.FORGOT_PASSWORD_VIEW;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(UserConstants.USER) User modelUser, BindingResult bindingResult) {
        String email = modelUser.getEmail();
        userValidator.validateEmail(email, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.FORGOT_PASSWORD_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendChangePassword(UserConstants.CHANGE_PASS, user, token);
        return UserConstants.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model, @RequestParam(TokenConst.TOKEN) String token) {
        Token verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        tokenService.delete(verificationToken);
        model.addAttribute(UserConstants.USER, user);
        return UserConstants.UPDATE_PASS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String savePassword(@ModelAttribute(UserConstants.USER) User modelUser, BindingResult bindingResult) {
        userValidator.validatePassword(modelUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return UserConstants.UPDATE_PASS_VIEW;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        user.setConfirmed(true);
        userService.update(user);
        return UserConstants.LOGIN_VIEW;
    }
}
