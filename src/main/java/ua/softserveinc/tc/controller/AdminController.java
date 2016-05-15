package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.CityService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;


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


    /*Staff for Managers*/

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.GET)
    public ModelAndView getManagerMenu() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

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
    public String deleteManager(@RequestParam Long id){
        userService.deleteUserById(id);
        return "redirect:/" + "adm-edit-manager";
    }

    @RequestMapping(value="/adm-update-manager", method = RequestMethod.GET)
    public ModelAndView updateManager(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("adm-update-manager");

        User manager = userService.findById(id);
        model.getModelMap().addAttribute("manager", manager);

        return model;
    }

    @RequestMapping(value="/adm-update-manager", method = RequestMethod.POST)
    public String submitManagerUpdate(@ModelAttribute ("manager") User manager){
        manager.setRole(Role.MANAGER);
        userService.update(manager);

        return "redirect:/" + "adm-edit-manager";
    }



    /*Staff for Rooms*/

    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.GET)
    public ModelAndView getRoomMenu() {
        List<Room> rooms = roomService.findAll();

        ModelAndView mav = new ModelAndView("adm-edit-room");
        mav.addObject("roomList", rooms);
        return mav;
    }

    @RequestMapping(value = "/adm-add-room", method = RequestMethod.GET)
    public ModelAndView showCreateRoomForm() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);
        List<City> cities = cityService.findAll();

        ModelAndView mav = new ModelAndView("adm-add-room");
        mav.addObject("managerList", managers);
        mav.addObject("cityList", cities);

        return mav;
    }

    @RequestMapping(value = "/adm-add-room", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute Room room) {
        roomService.create(room);
        return "adm-add-room";
    }

    @RequestMapping(value="/adm-update-room", method = RequestMethod.GET)
    public ModelAndView updateRoom(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("adm-update-room");

        Room room = roomService.findById(id);
        model.getModelMap().addAttribute("room", room);

        return model;
    }

    @RequestMapping(value="/adm-update-room", method = RequestMethod.POST)
    public String submitRoomUpdate(@ModelAttribute ("room") Room room){
        roomService.update(room);
        return "redirect:/" + "adm-edit-room";
    }

    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.POST)
    public String deleteRoom(@RequestParam Long id){
        userService.deleteUserById(id);
        return "redirect:/" + "adm-edit-room";
    }



    /*Staff for Cities*/

    @RequestMapping(value = "/adm-edit-city", method = RequestMethod.GET)
    public ModelAndView getCityMenu() {
        List<City> cities = cityService.findAll();

        ModelAndView mav = new ModelAndView("adm-edit-city");
        mav.addObject("cityList", cities);
        return mav;
    }

    @RequestMapping(value = "/adm-add-city", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute City city) {
        cityService.create(city);
        return "redirect:/" + "adm-edit-city";
    }

    @RequestMapping(value = "/adm-add-city", method = RequestMethod.GET)
    public String showCreateCityForm() {
        return "adm-add-city";
    }


    @RequestMapping(value = "/adm-edit-city", method = RequestMethod.POST)
    public String deleteCity(@RequestParam Long id){
        cityService.deleteCityById(id);
        return "redirect:/" + "adm-edit-city";
    }

}
