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
public class AdminAddRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-add-room", method = RequestMethod.GET)
    public ModelAndView showCreateRoomForm() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView mav = new ModelAndView("adm-add-room");
        mav.addObject("managerList", managers);

        return mav;
    }

    @RequestMapping(value = "/adm-add-room", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute Room room, @RequestParam("cities") Long idCity,
                           @RequestParam("managers") Long id) {

        User manager = userService.findById(id);

        room.setManager(manager);

        roomService.create(room);
        return "adm-add-room";
    }
}
