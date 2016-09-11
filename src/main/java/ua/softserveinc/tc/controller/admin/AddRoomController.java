package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import ua.softserveinc.tc.validator.TimeValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for "Add manager" view, which accompanies add new manager.
 * <p>
 */
@Controller
@RequestMapping(value = "/adm-add-room")
public class AddRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TimeValidator timeValidator;

    /**
     * Method call view, for add new room. Method send model into that view
     * with list of managers into (mapped by AdminConstants.UPDATE_ROOM const).
     *
     * @return mav (model into UI)
     */
    @RequestMapping(method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView saveNewRoom(@Valid @ModelAttribute(AdminConstants.ATR_ROOM) RoomDto roomDto,
                                    BindingResult bindingResult) {
        this.timeValidator.validate(roomDto, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView(AdminConstants.ADD_ROOM).addObject(AdminConstants.MANAGER_LIST,
                    this.userService.findAllUsersByRole(Role.MANAGER));
            return model;
        }

        List<Long> idManagers = JsonUtil.fromJsonList(roomDto.getManagers(), UserDto[].class).stream()
                .map(UserDto::getId).collect(Collectors.toList());
        List<User> managers = this.userService.findAll(idManagers);
        Room room = RoomDto.getRoomObjectFromDtoValues(roomDto);
        room.setManagers(managers);
        room.setActive(true);

        this.roomService.saveOrUpdate(room);
        return new ModelAndView("redirect:/" + AdminConstants.EDIT_ROOM);
    }
}
