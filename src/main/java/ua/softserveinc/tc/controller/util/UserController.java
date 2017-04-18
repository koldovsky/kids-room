package ua.softserveinc.tc.controller.util;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.constants.ManagerConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import javax.inject.Inject;


@Controller
public class UserController {

    @Inject
    private MessageSource messageSource;

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
    public ModelAndView handleError403(Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ErrorConstants.ERROR_VIEW);
        modelAndView.addObject(ErrorConstants.ERROR_MESSAGE,
                messageSource.getMessage(ErrorConstants.MESSAGE_ACCESS_DENIED, null, locale));
        modelAndView.addObject(ErrorConstants.ERROR_FILE, ErrorConstants.DEFAULT_ERROR_FILE_NAME);

        return modelAndView;
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
