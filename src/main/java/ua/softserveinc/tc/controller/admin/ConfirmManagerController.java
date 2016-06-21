package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Controller class for "Confirm manager registration" view,
 * which accompanies accepting registration of manager.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/confirm-manager")
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


    /**
     * Method open "Confirm manager registration" view. Send model with values founded by token.
     * Deleting used token. Mapped by AdminConstants.CONFIRM_MANAGER constant.
     *
     * @param model
     * @param sToken
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showConfirmRegistrationForm(Model model, @RequestParam(TokenConstants.TOKEN) String sToken) {
        Token token = this.tokenService.findByToken(sToken);
        User manager = token.getUser();
        model.addAttribute(AdminConstants.ATR_MANAGER, manager);

        this.tokenService.delete(token);
        return AdminConstants.CONFIRM_MANAGER;
    }


    /**
     * Method build model based based on parameters received from view.
     * Save built Manager object into Service layer with method update().
     * Redirect into view, witch mapped by UserConstants.Model.LOGIN_VIEW
     *
     * @param manager
     * @param bindingResult
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String confirmPassword(@ModelAttribute(AdminConstants.ATR_MANAGER) User manager, BindingResult bindingResult) {
        User managerToSave = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        managerToSave.setPassword(manager.getPassword());
        managerToSave.setConfirm(manager.getConfirm());

        this.userValidator.validatePassword(managerToSave, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.CONFIRM_MANAGER;
        }

        managerToSave.setPassword(this.passwordEncoder.encode(manager.getPassword()));
        managerToSave.setConfirmed(true);

        this.userService.update(managerToSave);
        return UserConstants.Model.LOGIN_VIEW;
    }
}
