package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;

/**
 * Created by edward on 5/8/16.
 */
@Controller
public class AllKidsListController {

    @Autowired
    ChildService childService;

    @RequestMapping(value = "/allkidslist", method = RequestMethod.GET)
    public ModelAndView kidsList() {
        ModelAndView model = new ModelAndView();
        model.setViewName("allkidslist");

        List<Child> childList = childService.findAll();
        model.addObject("kids", childList);

        return model;
    }
}
