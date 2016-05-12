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
        System.out.println(id);


        return calendarServiceImpl.eventsToString(id);
    }

   @RequestMapping(value = "getCompanies", method = RequestMethod.POST)
    public @ResponseBody
    String getEvents(@ModelAttribute Integer id) {
       System.out.println(id);
       return calendarServiceImpl.eventsToString(1);
   }
  @RequestMapping(value="/{ids}", method = RequestMethod.GET)
  public @ResponseBody String getEventJSON(@PathVariable String ids) {

      return calendarServiceImpl.eventsToString(Integer.valueOf(ids));
  }
}

/*
    @RequestMapping(value = "getCompanies",  method = RequestMethod.GET)
    public @ResponseBody
    String getRaandom(@RequestBody Info id) {

        return calendarServiceImpl.findByRoomId(Integer.valueOf(id.getId())).toString()
        .substring(1,calendarServiceImpl.findByRoomId(id.getId()).toString().length()-1);
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
