package ua.softserveinc.tc.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.util.Log;

/**
 * Created by edward on 5/8/16.
 */
@Controller
public class AllKidsListController {

    private static @Log org.slf4j.Logger LOG;

    @RequestMapping(value = ChildConstants.View.ALL_KIDS_URL, method = RequestMethod.GET)
    public ModelAndView getKidsList() {
        ModelAndView model = new ModelAndView();
        model.setViewName(ChildConstants.View.ALL_KIDS);
        LOG.error("This is Error message", new Exception("Testing"));
        LOG.debug("Hello");

        return model;
    }

}
