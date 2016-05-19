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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value="/login ", method = RequestMethod.GET)
    public String login(Model model){
        return UsersConst.LOGIN_VIEW;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute(UsersConst.USER, new ua.softserveinc.tc.entity.User());
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
        mailService.buildRegisterMessage(UsersConst.CONFIRM_REGISTRATION, user, token);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value="/rules ", method = RequestMethod.GET)
    public String getRules(){
        return UsersConst.RULES_VIEW;
    }

    /*@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        model.addAttribute(UsersConst.USER, userService.getUserByEmail(principal.getName()));
        return UsersConst.ACCESS_DENIED_VIEW;
    }*/

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);

        userService.update(user);
        verificationTokenService.delete(verificationToken);
        return "redirect:/login";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String changePassword(){
        return "forgotPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("email") String email){
        User user = userService.getUserByEmail(email);
        String token = UUID.randomUUID().toString();
        verificationTokenService.createToken(token, user);
        mailService.sendChangePassword("change", user, token);
        return UsersConst.SUCCESS_VIEW;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(@RequestParam("id") Long id, @RequestParam("token") String token) {
        System.out.println(token);
        System.out.println(id);
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User user = verificationToken.getUser();
        if(user.getId()!=id){
            return "redirect:/registrtion";
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        verificationTokenService.delete(verificationToken);
        return "updatePassword";
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    public String savePassword(@RequestParam("password") String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(password));
        userService.update(user);
        return "login";
    }


}


