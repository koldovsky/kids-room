package ua.softserveinc.tc.controller.admin;

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
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.TokenConstants;
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
    @Qualifier(UserConstants.Model.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;


    @RequestMapping(value = "/confirm-manager", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam(TokenConstants.TOKEN) String sToken) {

        Token token = tokenService.findByToken(sToken);
        User manager = token.getUser();
        model.addAttribute(AdminConstants.ATR_MANAGER, manager);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                manager, null, userDetailsService.loadUserByUsername(manager.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        tokenService.delete(token);
        return AdminConstants.CONFIRM_MANAGER;
    }

    @RequestMapping(value = "/confirm-manager", method = RequestMethod.POST)
    public String confirmPassword(@ModelAttribute(AdminConstants.ATR_MANAGER) User manager, BindingResult bindingResult) {
        User managerToSave = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        managerToSave.setPassword(manager.getPassword());
        managerToSave.setConfirm(manager.getConfirm());

        userValidator.validatePassword(managerToSave, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.CONFIRM_MANAGER;
        }

        managerToSave.setPassword(passwordEncoder.encode(manager.getPassword()));
        managerToSave.setConfirmed(true);
        userService.update(managerToSave);

        return UserConstants.Model.LOGIN_VIEW;
    }

}
