package ua.softserveinc.tc.controller.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

/**
 * Created by dima- on 07.05.2016.
 */
@Controller
public class ViewEventController {

    @Autowired
    GenericMapper<Event, EventDto> genericMapper;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private RoomService roomServiceImpl;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String viewHome(Model model, Principal principal) {
        if (principal == null) {
            return UserConstants.Model.LOGIN_VIEW;
        } else {
            String email = principal.getName();
            User user = userService.getUserByEmail(email);

            switch (user.getRole()) {
                case USER:
                    model.addAttribute(UserConstants.Entity.ROOMS, roomServiceImpl.findAll());
                    model.addAttribute(UserConstants.Entity.KIDS, user.getEnabledChildren());
                    model.addAttribute(UserConstants.Entity.USERID, user.getId());
                    return EventConstants.View.MAIN_PAGE;
                case MANAGER:
                    model.addAttribute(UserConstants.Entity.ROOMS, user.getRooms());
                    return EventConstants.View.MAIN_PAGE;
                default:
                    return ReportConstants.STATISTICS_VIEW;
            }
        }
    }

    @RequestMapping(value = "getevents/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getEvents(@PathVariable int id) {
        return new Gson().toJson(calendarService.findByRoomId(id));
    }

    @RequestMapping(value = "getnewevent", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getAjax(@RequestBody EventDto eventDto) {
        return calendarService.create(genericMapper.toEntity(eventDto)).toString();
    }

    @RequestMapping(value = "geteventforupdate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void getEventForUpdate(@RequestBody EventDto eventDto) {
        calendarService.updateEvent(genericMapper.toEntity(eventDto));
    }

    @RequestMapping(value = "geteventfordelete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void getEventForDelete(@RequestBody EventDto eventDto) {
        calendarService.deleteEvent(genericMapper.toEntity(eventDto));
    }
}
