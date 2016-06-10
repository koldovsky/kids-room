package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.model.AdminConst;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

/**
 * Created by TARAS on 18.05.2016.
 * <p>
 * Controller class for "adm-add-room", witch accompanies add new room's into system.
 */
@Controller
public class AddRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    /**
     * Method call view, with form to add new room's. Method send model into that
     * form with list of managers into form(form with action=adm-add-room).
     *
     * @return mav (model into UI)
     */
    @RequestMapping(value = "/adm-add-room", method = RequestMethod.GET)
    public ModelAndView showCreateRoomForm() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView mav = new ModelAndView(AdminConst.ADD_ROOM);
        mav.addObject(AdminConst.MANAGER_LIST, managers);

        return mav;
    }

    /**
     * Method build model based based on parameters received from view with action "adm-add-room".
     * Method send built Room object into Service layer with  method create().
     *
     * @param roomDto (Data Transfer Object for Room, needed to get json of rate's in String type)
     * @param id      (chosen on view manager id from list)
     * @return string, witch redirect on other view
     */
    @RequestMapping(value = "/adm-add-room", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute RoomDto roomDto, @RequestParam("managers") Long id) {
        User managerForRoom = userService.findById(id);
        roomDto.setManager(managerForRoom);

        Room room = new Room(roomDto);
        roomService.create(room);

        return "redirect:/" + AdminConst.EDIT_ROOM;
    }
}
