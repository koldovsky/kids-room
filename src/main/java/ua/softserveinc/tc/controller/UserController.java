package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.VerificationToken;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.VerificationTokenService;
import ua.softserveinc.tc.validator.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
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
    private VerificationTokenService verificationTokenService;

    @RequestMapping(value="/login ", method = RequestMethod.GET)
    public String login(Model model){
        return UsersConst.LOGIN_VIEW;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute(UsersConst.USER, new User());
        return UsersConst.REGISTRATION_VIEW;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam(value = "confirm") String confirm) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors())){
//            return UsersConst.REGISTRATION_VIEW;
//        }
//        if(!user.getPassword().equals(confirm)){
//            return UsersConst.REGISTRATION_VIEW;
//        }
        String token = UUID.randomUUID().toString();
        user.setRole(Role.USER);
        user.setEnabled(false);
        userService.create(user);
        verificationTokenService.createToken(token, user);
        String message = mailService.buildRegisterMessage(user,token);
        mailService.sendMessage(user, UsersConst.CONFIRM_REGISTRATION, message);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value="/rules ", method = RequestMethod.GET)
    public String getRules(){
        return UsersConst.RULES_VIEW;
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        model.addAttribute(UsersConst.USER, userService.getUserByEmail(principal.getName()));
        return UsersConst.ACCESS_DENIED_VIEW;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.update(user);
        verificationTokenService.delete(verificationToken);
        return "redirect:/login";
    }
}


