package ua.softserveinc.tc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Nestor on 07.05.2016.
 */

@Controller
public class MyKidsPageController {

    @RequestMapping(value = "/mykids**", method = RequestMethod.GET)
    public ModelAndView myKids(){
        ModelAndView model = new ModelAndView();
        model.setViewName("mykids");
        return model;
    }
}
