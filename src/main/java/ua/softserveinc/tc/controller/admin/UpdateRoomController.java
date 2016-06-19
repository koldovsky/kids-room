package ua.softserveinc.tc.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.JsonUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/adm-update-room")
public class UpdateRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showUpdateRoomForm(@RequestParam("id") Long id) throws JsonProcessingException {
        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_ROOM);

        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);
        model.addObject(AdminConstants.MANAGER_LIST, managers);

        Room room = this.roomService.findById(id);
        RoomDto roomDto = new RoomDto(room);

        model.getModelMap().addAttribute(AdminConstants.ATR_ROOM, roomDto);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitRoomUpdate(@Valid @ModelAttribute(AdminConstants.ATR_ROOM) RoomDto roomDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AdminConstants.UPDATE_ROOM;
        }

        List<Long> idManagers = JsonUtil.fromJsonList(roomDto.getManagers(), UserDto[].class).stream()
                .map(UserDto::getId).collect(Collectors.toList());
        List<User> managers = this.userService.findAll(idManagers);
        Room room = new Room(roomDto);
        room.setManagers(managers);

        this.roomService.saveOrUpdate(room);
        return "redirect:/" + AdminConstants.EDIT_ROOM;
    }
}
