package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.service.CalendarServiceImpl;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.UserService;

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

    @RequestMapping(value = "getCompanies",  method = RequestMethod.GET)
    public @ResponseBody
    String getRaandom() {
        return calendarServiceImpl.findByRoomId(2).toString().substring(1,calendarServiceImpl.findByRoomId(2).toString().length()-1);
    }

   /* @RequestMapping(value = "/{address}",  method = RequestMethod.GET)
    public @ResponseBody
    String getRandom(@PathVariable final long address) {
        return calendarServiceImpl.findByRoomId(address).toString();
    }*/

}
