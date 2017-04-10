package ua.softserveinc.tc.controller.util;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.constants.ManagerConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Principal principal) {
        String resultView;
        if(principal == null){
            resultView = UserConstants.Model.LOGIN_VIEW;
            return resultView;
        }else{
           return abort_login_page(principal);
        }
    }

    @GetMapping("/rules")
    public String getRules() {
        return UserConstants.Model.RULES_VIEW;
    }

    @GetMapping("/accessDenied")
    public String handleError403() {
        return ErrorConstants.ACCESS_DENIED_VIEW;
    }

    private String abort_login_page(Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        String view;
        switch (user.getRole()) {
            case USER:
                if (user.getChildren().isEmpty() || userService.getEnabledChildren(user).isEmpty()) {
                    view = "redirect:/" + ChildConstants.View.MY_KIDS;
                } else {
                    view = "redirect:/" + UserConstants.USER_CALENDAR;
                }
                break;
            case MANAGER:
                view = "redirect:/" + ManagerConstants.MANAGER_EDIT_BOOKING;
                break;
            case ADMINISTRATOR:
                view =  "redirect:/" + AdminConstants.EDIT_MANAGER;
                break;
            default:
                view = "redirect:/";
        }
        return view;
    }
}
