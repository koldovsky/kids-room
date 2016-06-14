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


@Controller
public class AddManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Log
    private static Logger log;
    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public ModelAndView showCreateManagerForm() {
        ModelAndView model = new ModelAndView(AdminConstants.ADD_MANAGER);
        model.addObject(AdminConstants.ATR_MANAGER, new User());

        return model;
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@Valid @ModelAttribute(AdminConstants.ATR_MANAGER) User manager,
                              BindingResult bindingResult) {
        userValidator.validateIfEmailExist(manager, bindingResult);
        if (bindingResult.hasErrors()) {
            return AdminConstants.ADD_MANAGER;
        }

        manager.setRole(Role.MANAGER);
        manager.setActive(true);
        manager.setConfirmed(false);

        String token = UUID.randomUUID().toString();

        try {
            mailService.buildConfirmRegisterManager("Confirmation registration", manager, token);
        } catch (MessagingException | MailSendException e) {
            log.error("Error! Sending email!!!", e);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            return AdminConstants.ADD_MANAGER;
        }
        userService.create(manager);
        tokenService.createToken(token, manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }

}
