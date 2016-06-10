package ua.softserveinc.tc.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.model.AllKidsConst;

/**
 * Created by edward on 5/8/16.
 */
@Controller
public class AllKidsListController {

    @RequestMapping(value = AllKidsConst.ALL_KIDS_URL, method = RequestMethod.GET)
    public ModelAndView getKidsList() {
        ModelAndView model = new ModelAndView();
        model.setViewName(AllKidsConst.ALL_KIDS_VIEW);

        return model;
    }

}
