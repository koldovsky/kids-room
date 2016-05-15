package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.service.CalendarServiceImpl;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String viewHome(Model model) {
        model.addAttribute("rooms",roomServiceImpl.findAll());
        return "index";
    }

    @RequestMapping(value = "getCompanies/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getEvents(@PathVariable int id) {
        return calendarServiceImpl.eventsToString(id);
    }

    @RequestMapping(value = "getnewevent", method = RequestMethod.POST)
    public String getAjax(@RequestBody NewEvent newEvent) {
        System.out.println(newEvent.toString());
        return "index";
    }
}

class NewEvent{
    String title;

    String startTime;

    String endTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "NewEvent{" +
                "title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

