package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import ua.softserveinc.tc.validator.RoomValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for "Add manager" view, which accompanies add new manager.
 */

@Controller
public class AddUpdateRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomValidator roomValidator;


    /**
     * Method call view, for add new room. Method send model into that view
     * with list of managers into (mapped by AdminConstants.UPDATE_ROOM const).
     *
     * @return mav (model into UI)
     */
    @GetMapping("/adm-add-room")
    public ModelAndView showAddRoomForm() {
        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView model = new ModelAndView(AdminConstants.ADD_ROOM);
        model.getModelMap().addAttribute(AdminConstants.ATR_ROOM, new RoomDto());
        model.addObject(AdminConstants.MANAGER_LIST, managers);

        return model;
    }

    /**
     * Method build model based based on parameters received from view with action AdminConstants.ADD_ROOM const.
     * Method send built Room object into Service layer with  method saveOrUpdate().
     *
     * @param roomDto (Data Transfer Object for Room, needed to get some fields in JSON)
     * @return string, witch redirect on other view
     */
    @PostMapping("/adm-add-room")
    public ModelAndView saveNewRoom(@ModelAttribute(AdminConstants.ATR_ROOM) RoomDto roomDto,
                                    BindingResult bindingResult) {
        roomValidator.validate(roomDto,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView(AdminConstants.ADD_ROOM).addObject(AdminConstants.MANAGER_LIST,
                    userService.findAllUsersByRole(Role.MANAGER));
        }

        Room room = getRoomFromDto(roomDto);
        room.setActive(true);
        roomService.saveOrUpdate(room);
        return new ModelAndView("redirect:/" + AdminConstants.EDIT_ROOM);
    }
    /**
     * Method mapping into view, with update room form. Method send empty model into view
     * with list of managers (view mapping by AdminConstants.UPDATE_ROOM const).
     *
     * @return mav (model into UI)
     */
    @GetMapping("/adm-update-room")
    public ModelAndView showUpdateRoomForm(@RequestParam Long id) {
        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);
        Room room = this.roomService.findByIdTransactional(id);
        RoomDto roomDto = new RoomDto(room);

        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_ROOM);
        model.addObject(AdminConstants.MANAGER_LIST, managers);
        model.getModelMap().addAttribute(AdminConstants.ATR_ROOM, roomDto);

        return model;
    }


    /**
     * Method build model based based on parameters received from view mapped by AdminConstants.UPDATE_ROOM.
     * Method send built Room object into Service layer with method saveOrUpdate().
     *
     * @param roomDto (Data Transfer Object for Room, needed to get some fields in JSON)
     * @return string, which redirect on other view
     */
    @PostMapping("/adm-update-room")
    public ModelAndView submitRoomUpdate(@ModelAttribute(AdminConstants.ATR_ROOM) RoomDto roomDto,
                                         BindingResult bindingResult) {
        roomValidator.validate(roomDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView(AdminConstants.UPDATE_ROOM).addObject(AdminConstants.MANAGER_LIST,
                    userService.findAllUsersByRole(Role.MANAGER));
        }
        Room room = getRoomFromDto(roomDto);
        roomService.saveOrUpdate(room);
        return new ModelAndView("redirect:/" + AdminConstants.EDIT_ROOM);
    }

    private Room getRoomFromDto(RoomDto roomDto) {
        List<Long> idManagers = JsonUtil.fromJsonList(roomDto.getManagers(), UserDto[].class).stream()
                .map(UserDto::getId).collect(Collectors.toList());
        List<User> managers = userService.findAll(idManagers);
        Room room = RoomDto.getRoomObjectFromDtoValues(roomDto);
        room.setManagers(managers);
        return room;
    }

}
