package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller class for "Room list" view. It's main controller for editing rooms.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class EditRoomController {

    @Autowired
    private RoomService roomService;

    private Logger logger = Logger.getLogger("EditRoomController");

    /**
     * Method send  model with all rooms into view.
     * Mapped by AdminConstants.EDIT_ROOM constant.
     *
     * @return model
     */
    @GetMapping("/adm-edit-room")
    public ModelAndView showAllRoomsForm() {
        List<Room> rooms = roomService.findAll();

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
    @PostMapping("/adm-edit-room")
    public String roomBlockUnblock(@RequestParam Long id) {
        Room room = this.roomService.findById(id);
        room.setActive(!room.isActive());
        this.roomService.update(room);
        return "redirect:/" + AdminConstants.EDIT_ROOM;
    }

}
