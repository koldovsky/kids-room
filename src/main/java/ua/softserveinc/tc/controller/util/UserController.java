package ua.softserveinc.tc.controller.util;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.constants.UserConstants;


@Controller
public class UserController {

    @Secured({"ROLE_ANONYMOUS"})
    @GetMapping("/login")
    public String login(Model model) {
        return UserConstants.Model.LOGIN_VIEW;
    }

    @GetMapping("/rules")
    public String getRules() {
        return UserConstants.Model.RULES_VIEW;
    }

    @GetMapping("/accessDenied")
    public String handleError403() {
        return ErrorConstants.ACCESS_DENIED_VIEW;
    }
}
