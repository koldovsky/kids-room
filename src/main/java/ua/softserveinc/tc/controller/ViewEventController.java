package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.ModelConstants.EventConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.dto.EventDTO;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by dima- on 07.05.2016.
 */
@Controller
public class ViewEventController {

    @Autowired
    GenericMapper<Event, EventDTO> genericMapper;
    @Autowired
    private CalendarService calendarService;
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

            if(userService.getUserByEmail(email).getRole() == Role.USER) {
                model.addAttribute("rooms", roomServiceImpl.findAll());
            } else {
                model.addAttribute("rooms", roomServiceImpl.findByManger(user));
            }
            return EventConst.MAIN_PAGE;
        } catch (NullPointerException n) {
            return UsersConst.LOGIN_VIEW;
        }
    }

    @RequestMapping(value = "getevents/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getEvents(@PathVariable int id) {
        return calendarService.eventsToString(id);
    }

    @RequestMapping(value = "getnewevent", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String getAjax(@RequestBody EventDTO eventDTO) {
        String res = calendarService.returnEventId((genericMapper.toEntity(eventDTO))).toString();

        return res;
    }

    @RequestMapping(value = "geteventforupdate", method = RequestMethod.POST)
    public void getEventForUpdate(@RequestBody EventDTO eventDTO) {
        calendarService.updateEvent(genericMapper.toEntity(eventDTO));
    }

    @RequestMapping (value = "geteventfordelete", method = RequestMethod.POST)
    public void getEventForDelete(@RequestBody EventDTO eventDTO) {
        calendarService.deleteEvent(genericMapper.toEntity(eventDTO));
    }
}
