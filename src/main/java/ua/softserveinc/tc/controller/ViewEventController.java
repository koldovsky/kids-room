package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.ModelConstants.EventConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.dto.EventDTO;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.CalendarServiceImpl;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by dima- on 07.05.2016.
 */
@Controller
public class ViewEventController {

    @Autowired
    private CalendarServiceImpl calendarServiceImpl;
    @Autowired
    private RoomService roomServiceImpl;
    @Autowired
    private EventService eventServiceImpl;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String viewHome(Model model, Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.getUserByEmail(email);

            System.out.println(user.getEmail());
            if(userService.getUserByEmail(email).getRole().toString() == Role.USER.toString()) {
                model.addAttribute("rooms",roomServiceImpl.findAll());
            } else {
                model.addAttribute("rooms", roomServiceImpl.findByManger(user));
            }
            return EventConst.MAIN_PAGE;
        } catch (NullPointerException n) {
            return UsersConst.LOGIN_VIEW;
        }

    }

    @RequestMapping(value = "getCompanies/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getEvents(@PathVariable int id) {
        return calendarServiceImpl.eventsToString(id);
    }

    @RequestMapping(value = "getnewevent", method = RequestMethod.POST)
    public String getAjax(@RequestBody EventDTO eventDTO) {
        calendarServiceImpl.create(eventDTO);
        return EventConst.MAIN_PAGE;
    }
}


