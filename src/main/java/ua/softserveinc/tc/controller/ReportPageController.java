package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.User;

import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportPageController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/report**", method = RequestMethod.GET)
    public ModelAndView myKids(Principal principal) {
        ModelAndView model = new ModelAndView();
        model.setViewName("report");

        List<User> parentsList = userService.getAllParents();

        ModelMap modelMap = model.getModelMap();
        modelMap.addAttribute("parents", parentsList);
        return model;
    }
}
