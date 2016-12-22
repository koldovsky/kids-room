package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ConfirmManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;



    /**
     * Method open "Confirm manager registration" view. Send model with values founded by token.
     * Deleting used token. Mapped by AdminConstants.CONFIRM_MANAGER constant.
     *
     * @param model
     * @param sToken
     * @return
     */
    @GetMapping("/confirm-manager")
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
     * @param managerWithPassword
     * @param bindingResult
     * @return
     */
    @PostMapping("/confirm-manager")
    public String confirmPassword(@ModelAttribute(AdminConstants.ATR_MANAGER) User managerWithPassword,
                                  BindingResult bindingResult) {
        this.userValidator.validatePassword(managerWithPassword, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.CONFIRM_MANAGER;
        }

        User manager = this.userService.findById(managerWithPassword.getId());
        manager.setPassword(this.passwordEncoder.encode(managerWithPassword.getPassword()));
        manager.setConfirmed(true);

        this.userService.update(manager);
        return UserConstants.Model.LOGIN_VIEW;
    }
}
