package ua.softserveinc.tc.controller.util;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.constants.UserConstants;

/**
 * Created by Chak on 07.05.2016.
 */
@Controller
public class UserController {

    @Secured({"ROLE_ANONYMOUS"})
    @RequestMapping(value = "/login ", method = RequestMethod.GET)
    public String login() {
        return UserConstants.Model.LOGIN_VIEW;
    }

    @RequestMapping(value = "/rules ", method = RequestMethod.GET)
    public String getRules() {
        return UserConstants.Model.RULES_VIEW;
    }

    @RequestMapping("/accessDenied")
    public String handleError403() {
        return  ErrorConstants.ACCESS_DENIED_VIEW;
    }
}


