package ua.softserveinc.tc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nestor on 07.05.2016.
 */

@Controller
public class MyKidsPageController {
    @Autowired
    UserService us;

    @RequestMapping(value = "/mykids", method = RequestMethod.GET)
    public ModelAndView myKids(Principal principal) {

        ModelAndView model = new ModelAndView();
        model.setViewName("mykids");
        String username = principal.getName();
        System.out.println(username);

        User user = us.getUserByEmail(username);

        List<Child> myKids = new ArrayList<>(user.getChildren());
        myKids.sort(
                (o1, o2)->{
                    if(o1.getId()>o2.getId())
                        return 1;
                    else if(o1.getId()<o2.getId())
                        return -1;
                    return 0;
            });

        ModelMap modelMap = model.getModelMap();

        if(!modelMap.containsAttribute("kids")) {
            modelMap.addAttribute("kids", myKids);
        }

        return model;
    }
}