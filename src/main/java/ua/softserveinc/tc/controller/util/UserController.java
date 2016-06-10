package ua.softserveinc.tc.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.ErrorPages;
import ua.softserveinc.tc.constants.UserConstants;

/**
 * Created by Chak on 07.05.2016.
 */
@Controller
public class UserController {

    @RequestMapping(value = "/rules ", method = RequestMethod.GET)
    public String getRules() {
        return UserConstants.Model.RULES_VIEW;
    }

    @RequestMapping("/accessDenied")
    public String handleError403() {
        return  ErrorPages.ACCESS_DENIED_VIEW;
    }
}


