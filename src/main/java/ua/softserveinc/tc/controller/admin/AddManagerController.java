package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;


@Controller
public class AddManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    private javax.validation.Validator validator;


    public AddManagerController() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public ModelAndView showCreateManagerForm() {
        ModelAndView model = new ModelAndView(AdminConst.ADD_MANAGER);
        model.addObject(AdminConst.ATR_MANAGER, new User());

        return model;
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@ModelAttribute(AdminConst.ATR_MANAGER) User manager, BindingResult bindingResult,
                              SessionStatus status) {

        Set<ConstraintViolation<User>> violations = validator.validate(manager);
        for (ConstraintViolation<User> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            bindingResult.addError(new FieldError(AdminConst.ATR_MANAGER, propertyPath, "Invalid " + propertyPath
                    + "(" + message + ")"));
        }
        if (bindingResult.hasErrors()) {
            return AdminConst.ADD_MANAGER;
        }
        status.setComplete();

        manager.setRole(Role.MANAGER);
        manager.setActive(true);
        manager.setConfirmed(false);
        userService.create(manager);

        String token = UUID.randomUUID().toString();
        tokenService.createToken(token, manager);
        mailService.buildConfirmRegisterManager("Confirmation registration", manager, token);

        return "redirect:/" + AdminConst.EDIT_MANAGER;
    }

}
