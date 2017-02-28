package ua.softserveinc.tc.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserMappingController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping(UserConstants.USER_CALENDAR)
    public String getUserCalendar(Model model, Principal principal) {
        model.addAttribute(UserConstants.Entity.ROOMS, roomService.getTodayActiveRooms());
        model.addAttribute(UserConstants.Entity.KIDS, userService.getEnabledChildren(user));
        model.addAttribute(UserConstants.Entity.USERID, user.getId());
    }
}
