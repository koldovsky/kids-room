package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

/**
 * Created by Nestor on 07.05.2016.
 */

@Controller
public class MyKidsPageController {
    @Autowired
    UserService us;

    @RequestMapping(value = "/mykids**", method = RequestMethod.GET)
    public ModelAndView myKids(Principal principal){
        ModelAndView model = new ModelAndView();
        model.setViewName("mykids");
        String username = principal.getName();
        System.out.println(username);

        User u = us.getUserByEmail(username);
        System.out.println(u.getId());
        System.out.println(u.getChildren());

        return model;
    }
}

