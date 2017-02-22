package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.EntryConstants;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.constants.ManagerConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

/**
 * Created by Tat0 on 21.02.2017.
 */
@Controller
public class ManagerMappingController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("/manager-calendar")
    public String getManagerCalendar(Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute(UserConstants.Entity.ROOMS, userService.getActiveRooms(user));
        return ManagerConstants.MANAGER_CALENDAR;

    }

}
