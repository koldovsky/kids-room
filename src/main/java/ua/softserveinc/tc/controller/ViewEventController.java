package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.dto.EventDTO;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.EventService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dima- on 07.05.2016.
 */
@Controller

public class ViewEventController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private EventService eService;
    @Autowired
    private UserService uService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String viewHome() {
        return "index";
    }
    /*
        @ResponseBody
        @RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
        public final List<User> getUser(final Principal principal) {
            return uService.findAll();
                    //calendarService.findByRoomId(Integer.valueOf(principal.getName()));
        }
    /*
        @ResponseBody
            @RequestMapping(value = "/search/api/getSearchResult")
        public List<User> getSearchResultViaAjax() {

            //AjaxResponseBody result = new AjaxResponseBody();
            //logic
            return uService.findAll();

        }


        @ResponseBody
        @RequestMapping(value = "/search/api/getSearchResult")
        public String getSearchResultViaAjax() {

            //AjaxResponseBody result = new AjaxResponseBody();
            //logic
            return uService.findAll().toString();

        }
    */
    @RequestMapping(value = "getCompanies",  method = RequestMethod.GET)
    public @ResponseBody
    String getRaandom() {
        System.out.println(eService.findAll().toString());
        return eService.findAll().toString();
    }


    /*
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ModelAndView viewEvents(Principal principal) {
        ModelAndView model = new ModelAndView();

        model.setViewName("index");
        ModelMap modelMap = model.getModelMap();

//        List<EventDTO> event = calendarService.findByRoomId(1);

        Event even = new Event();
        even.setName("fefeegegegegege");
        even.setStartTime(new Date());
        even.setEndTime(new Date());
     // System.out.println(event);
        modelMap.addAttribute("events", even);


        return model;
    }
    */
}
