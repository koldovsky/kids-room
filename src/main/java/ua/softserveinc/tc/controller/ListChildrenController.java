package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import java.security.Principal;
import java.util.List;

/**
 * Created by Петришак on 08.05.2016.
 */
@Controller
public class ListChildrenController {

    @Autowired
    ChildService childService;

    @RequestMapping(value = "/listChildren", method = RequestMethod.GET)
    public ModelAndView parentBookings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");
        ModelMap modelMap = modelAndView.getModelMap();


        List<Child> listChildren = childService.getAllChildren();
        modelMap.addAttribute("AllChildren", listChildren);
        return modelAndView;
    }
}
