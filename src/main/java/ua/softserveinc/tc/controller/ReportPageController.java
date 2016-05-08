package ua.softserveinc.tc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportPageController {
    @RequestMapping(value = "/report**", method = RequestMethod.GET)
        public ModelAndView myKids(Principal principal) {
            ModelAndView model = new ModelAndView();
            model.setViewName("report");
            return model;
    }
}
