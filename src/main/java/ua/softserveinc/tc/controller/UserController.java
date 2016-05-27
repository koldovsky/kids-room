package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
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
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;
import ua.softserveinc.tc.constants.ErrorPages;
import ua.softserveinc.tc.constants.ModelConstants.TokenConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.dto.EmailWrapper;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.EmailValidator;
import ua.softserveinc.tc.validator.UserValidator;

import java.util.UUID;

/**
 * Created by Chak on 07.05.2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier(UsersConst.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;


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
        String token = UUID.randomUUID().toString();
        user.setRole(Role.USER);
        user.setEnabled(false);
        userService.create(user);
        tokenService.createToken(token, user);
        mailService.sendRegisterMessage(UsersConst.CONFIRM_REGISTRATION, user, token);
        return UsersConst.SUCCESS_VIEW;
    }



    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.POST)
    public String sendConfirmation(@RequestParam(UserConst.EMAIL) String email ){
        if (userService.getUserByEmail(email) == null) {
            return UsersConst.LOGIN_VIEW;
        }
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendRegisterMessage(UsersConst.CONFIRM_REGISTRATION, user, token);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/rules ", method = RequestMethod.GET)
    public String getRules() {
        return UsersConst.RULES_VIEW;
    }


    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam(TokenConst.TOKEN) String sToken) {
        Token token = tokenService.findByToken(sToken);
        User user = token.getUser();
        user.setEnabled(true);
        userService.update(user);
        tokenService.delete(token);
        return "redirect:/login";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("emailWrapper", new EmailWrapper());
        return UsersConst.FORGOT_PASS_VIEW;
    }



    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute("emailWrapper")EmailWrapper emailWrapper, BindingResult bindingResult, Errors errors) {

            emailValidator.validate(emailWrapper, bindingResult);
            if (bindingResult.hasErrors()) {
                return UsersConst.FORGOT_PASS_VIEW;
            }

        User user = userService.getUserByEmail(emailWrapper.getEmail());
        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, user);
        mailService.sendChangePassword(UsersConst.CHANGE_PASS, user, token);
        return UsersConst.SUCCESS_VIEW;
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(@RequestParam("id") Long id, @RequestParam(TokenConst.TOKEN) String token) {
        Token verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        if (user.getId() != id) {
            return UsersConst.FORGOT_PASS_VIEW;
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        tokenService.delete(verificationToken);
        return UsersConst.UPDATE_PASS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String savePassword(@RequestParam(UserConst.PASSWORD) String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(password));
        userService.update(user);
        return UsersConst.LOGIN_VIEW;
    }

    @RequestMapping("/accessDenied")
    public String handleError403() {
        return  ErrorPages.ACCESS_DENIED_VIEW;
    }

}


