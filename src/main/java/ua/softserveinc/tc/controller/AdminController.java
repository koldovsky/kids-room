package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.CityService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by TARAS on 07.05.2016.
 */

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    CityService cityService;

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.GET)
    public ModelAndView getManagerMenu() {
        List<User> managers = userService.findAll().stream().filter(user -> Role.MANAGER == user.getRole())
                .collect(Collectors.toList());

        ModelAndView mav = new ModelAndView("adm-edit-manager");
        mav.addObject("managerList", managers);

        return mav;
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public String showCreateManagerForm() {
        return "adm-add-manager";
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@ModelAttribute User user) {
        user.setRole(Role.MANAGER);
        userService.create(user);
        return "adm-add-manager";
    }

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.POST)
    public String deleteManager(@RequestParam Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return "adm-edit-manager";
    }


    @RequestMapping(value = "/adm-edit-location", method = RequestMethod.GET)
    public ModelAndView getLocationMenu() {
        List<Room> rooms = roomService.findAll();

        ModelAndView mav = new ModelAndView("adm-edit-location");
        mav.addObject("roomList", rooms);
        return mav;
    }

    @RequestMapping(value = "/adm-add-location", method = RequestMethod.GET)
    public String showCreateLocation() {
        return "adm-add-location";
    }

    @RequestMapping(value = "/adm-add-location", method = RequestMethod.POST)
    public String saveLocation(@ModelAttribute Room room) {
        roomService.create(room);
        return "adm-add-location";
    }
}
