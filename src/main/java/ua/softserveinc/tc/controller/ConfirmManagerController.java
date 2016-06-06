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
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.constants.ModelConstants.TokenConst;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.UserValidator;

/**
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class ConfirmManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    @Qualifier(UserConstants.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/confirm-manager", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam(TokenConst.TOKEN) String sToken) {

        Token token = tokenService.findByToken(sToken);
        User user = token.getUser();
        model.addAttribute(UserConstants.USER, user);//"user"

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        tokenService.delete(token);

        return AdminConst.CONFIRM_MANAGER;//"adm-confirm-manager"
    }

    @RequestMapping(value = "/confirm-manager", method = RequestMethod.POST)
    public String confirmPassword(@ModelAttribute(UserConstants.USER) User manager, BindingResult bindingResult) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(manager.getPassword());
        user.setConfirm(manager.getConfirm());

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()){
            return AdminConst.CONFIRM_MANAGER;//"adm-confirm-manager"
        }
        user.setPassword(passwordEncoder.encode(manager.getPassword()));
        user.setConfirmed(true);
        userService.update(user);

        return UserConstants.LOGIN_VIEW;
    }

}
