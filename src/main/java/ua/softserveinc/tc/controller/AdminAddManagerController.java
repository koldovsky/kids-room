package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class AdminAddManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public String showCreateManagerForm() {
        return "adm-add-manager";
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@ModelAttribute User user, BindingResult bindingResult) {

        user.setRole(Role.MANAGER);

        userService.create(user);

        String token = user.getId().toString();//UUID.randomUUID().toString();
        mailService.buildConfirmRegisterManager("Confirmation registration", user, token);

        return "redirect:/" + "adm-edit-manager";
    }
}
