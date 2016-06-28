package ua.softserveinc.tc.controller.admin;

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


/**
 * Controller class for "Update room" view, which accompanies room updates.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/adm-update-room")
public class UpdateRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    /**
     * Method mapping into view, with update room form. Method send empty model into view
     * with list of managers (view mapping by AdminConstants.UPDATE_ROOM const).
     *
     * @return mav (model into UI)
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showUpdateRoomForm(@RequestParam Long id) {
        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_ROOM);

        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);
        model.addObject(AdminConstants.MANAGER_LIST, managers);

        Room room = this.roomService.findById(id);
        RoomDto roomDto = new RoomDto(room);

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
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitRoomUpdate(@Valid @ModelAttribute(AdminConstants.ATR_ROOM) RoomDto roomDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(AdminConstants.UPDATE_ROOM).addObject(AdminConstants.MANAGER_LIST,
                    this.userService.findAllUsersByRole(Role.MANAGER));
        }

        List<Long> idManagers = JsonUtil.fromJsonList(roomDto.getManagers(), UserDto[].class).stream()
                .map(UserDto::getId).collect(Collectors.toList());
        List<User> managers = this.userService.findAll(idManagers);
        Room room = RoomDto.getRoomObjectFromDtoValues(roomDto);
        room.setManagers(managers);

        this.roomService.saveOrUpdate(room);
        return new ModelAndView("redirect:/" + AdminConstants.EDIT_ROOM);
    }
}
