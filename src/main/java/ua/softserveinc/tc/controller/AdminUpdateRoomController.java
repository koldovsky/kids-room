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
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class AdminUpdateRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-update-room", method = RequestMethod.GET)
    public ModelAndView updateRoom(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("adm-update-room");

        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

        model.addObject("managerList", managers);


        Room room = roomService.findById(id);
        model.getModelMap().addAttribute("room", room);

        return model;
    }

    @RequestMapping(value = "/adm-update-room", method = RequestMethod.POST)
    public String submitRoomUpdate(@ModelAttribute("room") Room room, @RequestParam("cities") Long idCity,
                                   @RequestParam("managers") Long id) {

        User manager = userService.findById(id);
        room.setManager(manager);

        roomService.update(room);

        return "redirect:/" + "adm-edit-room";
    }
}
