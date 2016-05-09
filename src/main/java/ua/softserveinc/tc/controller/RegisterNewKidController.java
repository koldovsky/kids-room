package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

/**
 * Created by Nestor on 07.05.2016.
 */

@Controller
public class RegisterNewKidController {
    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registerkid", method = RequestMethod.GET)
    public String registerKid(Model model){
        model.addAttribute("child", new Child());
        return "registerkid";
    }

    @RequestMapping(value="/registerkid", method = RequestMethod.POST)
    public String submit(@ModelAttribute Child child, Principal principal){
        child.setParentId(
                userService.getUserByEmail(
                        principal.getName()));
        childService.create(child);
        return "registerkid";
    }
}