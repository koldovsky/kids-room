package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;


@Controller
public class EditRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.GET)
    public ModelAndView getRoomMenu() {
        List<Room> rooms = roomService.findAll();

        ModelAndView mav = new ModelAndView(AdminConst.EDIT_ROOM);
        mav.addObject(AdminConst.ROOM_LIST, rooms);

        return mav;
    }

    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.POST)
    public String roomBlockUnblock(@RequestParam Long id) {
        //TODO: block&unblock room
        userService.deleteUserById(id);
        return "redirect:/" + AdminConst.EDIT_ROOM;
    }
}
