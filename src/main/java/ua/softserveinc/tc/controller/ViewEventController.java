package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.service.CalendarServiceImpl;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dima- on 07.05.2016.
 */
@Controller
public class ViewEventController {

    @Autowired
    private CalendarServiceImpl calendarServiceImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String viewHome() {
        return "index";
    }

    @RequestMapping(value = "getCompanies", method = RequestMethod.GET)
    public @ResponseBody
    String getEvents() {
        return calendarServiceImpl.eventsToString(2);
    }
}




/*
    @RequestMapping(value = "getCompanies",  method = RequestMethod.GET)
    public @ResponseBody
    String getRaandom(@RequestBody Info id) {

        return calendarServiceImpl.findByRoomId(Integer.valueOf(id.getId())).toString().substring(1,calendarServiceImpl.findByRoomId(2).toString().length()-1);
    }
}

class Info{
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
*/
