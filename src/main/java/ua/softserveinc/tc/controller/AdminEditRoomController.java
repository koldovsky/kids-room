package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

/**
 * Created by TARAS on 16.05.2016.
 */
@Controller
public class AdminEditRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.GET)
    public ModelAndView getRoomMenu() {
        List<Room> rooms = roomService.findAll();

        ModelAndView mav = new ModelAndView("adm-edit-room");
        mav.addObject("roomList", rooms);

        return mav;
    }

    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.POST)
    public String deleteRoom(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/" + "adm-edit-room";
    }
}
