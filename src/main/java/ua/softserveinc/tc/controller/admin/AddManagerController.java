package ua.softserveinc.tc.controller.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller class for "Add manager" view, which accompanies add new manager.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/adm-add-manager")
public class AddManagerController {

    @Log
    private static Logger log;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserValidator userValidator;


    /**
     * Method open "Add manager" view. Send empty model into view.
     * Mapped by AdminConstants.ADD_MANAGER constant.
     *
     * @return model
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAddManagerForm() {
        ModelAndView model = new ModelAndView(AdminConstants.ADD_MANAGER);
        model.addObject(AdminConstants.ATR_MANAGER, new User());

        return model;
    }


    /**
     * Method saving model with values received from view. Check value validation.
     * Redirect into view, witch mapped by AdminConstants.EDIT_MANAGER
     *
     * @param manager
     * @param bindingResult
     * @return String value
     */
    @RequestMapping(method = RequestMethod.POST)
    public String saveNewManager(@Valid @ModelAttribute(AdminConstants.ATR_MANAGER) User manager,
                                 BindingResult bindingResult) {
        this.userValidator.validateManagerEmail(manager, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.ADD_MANAGER;
        }

        manager.setRole(Role.MANAGER);
        manager.setActive(true);
        manager.setConfirmed(false);

        String token = UUID.randomUUID().toString();
        try {
            this.mailService.buildConfirmRegisterManager("Confirmation registration", manager, token);
        } catch (MessagingException | MailSendException exeption) {
            log.error("Error! Sending email!!!", exeption);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            return AdminConstants.ADD_MANAGER;
        }

        this.userService.create(manager);
        this.tokenService.createToken(token, manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
