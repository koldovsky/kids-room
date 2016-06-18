package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;


@Controller
public class EditRoomController {

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.GET)
    public ModelAndView getRoomMenu() {
        List<Room> rooms = this.roomService.findAll();

        ModelAndView mav = new ModelAndView(AdminConstants.EDIT_ROOM);
        mav.addObject(AdminConstants.ROOM_LIST, rooms);

        return mav;
    }

    @RequestMapping(value = "/adm-edit-room", method = RequestMethod.POST)
    public String roomBlockUnblock(@RequestParam Long id) {
        Room room = this.roomService.findById(id);
        if (room.isActive()) {
            room.setActive(false);
        } else {
            room.setActive(true);
        }
        this.roomService.update(room);
        return "redirect:/" + AdminConstants.EDIT_ROOM;
    }
}
