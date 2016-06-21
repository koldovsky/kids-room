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

/**
 * Controller class for "Room list" view. It's main controller for editing rooms.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/adm-edit-room")
public class EditRoomController {

    @Autowired
    private RoomService roomService;


    /**
     * Method send  model with all rooms into view.
     * Mapped by AdminConstants.EDIT_ROOM constant.
     *
     * @return model
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAllRoomsForm() {
        List<Room> rooms = this.roomService.findAll();

        ModelAndView model = new ModelAndView(AdminConstants.EDIT_ROOM);
        model.addObject(AdminConstants.ROOM_LIST, rooms);

        return model;
    }


    /**
     * Method receive room id from view. Set setActive() for room opposite to previous value.
     * This mean record will lock or unlock, based on the received state.
     * Redirect into view, which mapped by AdminConstants.EDIT_ROOM const.
     *
     * @return String value
     */
    @RequestMapping(method = RequestMethod.POST)
    public String roomBlockUnblock(@RequestParam Long id) {
        Room room = this.roomService.findById(id);
        room.setActive(!room.isActive());

        this.roomService.update(room);
        return "redirect:/" + AdminConstants.EDIT_ROOM;
    }
}
