package ua.softserveinc.tc.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ChildConstants;


@Controller
public class AllKidsListController {

    @GetMapping(ChildConstants.View.ALL_KIDS_URL)
    public ModelAndView getKidsList() {
        ModelAndView model = new ModelAndView();
        model.setViewName(ChildConstants.View.ALL_KIDS);

        return model;
    }

}
