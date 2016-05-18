package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class ConfirmManagerController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/confirm-manager", method = RequestMethod.GET)
    public ModelAndView getConfirmForm(@RequestParam("token") Long id){
        ModelAndView model = new ModelAndView("confirm-manager");

        User manager = userService.findById(id);
        model.getModelMap().addAttribute("manager", manager);

        return model;
    }

    @RequestMapping(value = "/confirm-manager", method = RequestMethod.POST)
    public String submitConfirmManagerRegister(@ModelAttribute("manager") User manager) {

        manager.setEnabled(true);
        userService.confirmManagerRegistrationUpdate(manager);

        return "redirect:/" + "login";
    }

}
