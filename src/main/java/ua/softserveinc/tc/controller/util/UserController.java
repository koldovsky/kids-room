package ua.softserveinc.tc.controller.util;

import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.constants.UserConstants;

import javax.inject.Inject;
import java.util.Locale;


@Controller
public class UserController {

    @Inject
    private MessageSource messageSource;

    @Secured({"ROLE_ANONYMOUS"})
    @GetMapping("/login")
    public String login() {
        return UserConstants.Model.LOGIN_VIEW;
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
}
