package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.dto.RoomDTO;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

/**
 * Created by TARAS on 18.05.2016.
 */
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
        RoomDTO roomDTO = new RoomDTO(room);

        if(roomDTO.getRate().equals("[]")){
            roomDTO.setRate(null);
        }
        model.getModelMap().addAttribute(AdminConst.ATR_ROOM, roomDTO);

        return model;
    }

    @RequestMapping(value = "/adm-update-room", method = RequestMethod.POST)
    public String submitRoomUpdate(@ModelAttribute("room") RoomDTO roomDTO, @RequestParam("managers") Long id) {

        User managerForRoom = userService.findById(id);
        roomDTO.setManager(managerForRoom);

        Room room = new Room(roomDTO);
        roomService.create(room);

        return "redirect:/" + AdminConst.EDIT_ROOM;
    }
}
