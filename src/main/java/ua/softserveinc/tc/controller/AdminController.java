package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.entity.City;
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
        List<User> managers = userService.findAllManagers();

        ModelAndView mav = new ModelAndView(AdminConst.EDIT_MANAGER);
        mav.addObject(AdminConst.MANAGER_LIST, managers);

        return mav;
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public String showCreateManagerForm() {
        return AdminConst.ADD_MANAGER;
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@ModelAttribute User user) {
        user.setRole(Role.MANAGER);
        userService.create(user);
        return AdminConst.ADD_MANAGER;
    }

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.POST)
    public String deleteManager(@RequestParam Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return AdminConst.EDIT_MANAGER;
    }


    @RequestMapping(value = "/adm-edit-location", method = RequestMethod.GET)
    public ModelAndView getLocationMenu() {
        List<Room> rooms = roomService.findAll();

        ModelAndView mav = new ModelAndView(AdminConst.EDIT_LOCATION);
        mav.addObject(AdminConst.ROOM_LIST, rooms);
        return mav;
    }

    @RequestMapping(value = "/adm-add-location", method = RequestMethod.GET)
    public ModelAndView showCreateLocationForm() {
        List<User> managers = userService.findAllManagers();
        List<City> cities = cityService.findAll();

        ModelAndView mav = new ModelAndView(AdminConst.ADD_LOCATION);
        mav.addObject(AdminConst.MANAGER_LIST, managers);
        mav.addObject(AdminConst.CITY_LIST, cities);

        return mav;
    }

    @RequestMapping(value = "/adm-add-location", method = RequestMethod.POST)
    public String saveLocation(@ModelAttribute Room room) {
        roomService.create(room);
        return AdminConst.ADD_LOCATION;
    }
}
