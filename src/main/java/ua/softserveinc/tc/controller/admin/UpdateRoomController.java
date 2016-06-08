package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

@Controller
public class UpdateRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(value = "/adm-update-room", method = RequestMethod.GET)
    public ModelAndView getUpdateRoomForm(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView(AdminConst.UPDATE_ROOM);

        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);
        model.addObject(AdminConst.MANAGER_LIST, managers);

        Room room = roomService.findById(id);
        RoomDto roomDto = new RoomDto(room);

        model.getModelMap().addAttribute(AdminConst.ATR_ROOM, roomDto);

        return model;
    }

    @RequestMapping(value = "/adm-update-room", method = RequestMethod.POST)
    public String submitRoomUpdate(@ModelAttribute("room") RoomDto roomDto, @RequestParam("managers") Long id) {

        User managerForRoom = userService.findById(id);
        roomDto.setManager(managerForRoom);

        Room room = new Room(roomDto);
        roomService.create(room);

        return "redirect:/" + AdminConst.EDIT_ROOM;
    }
}
