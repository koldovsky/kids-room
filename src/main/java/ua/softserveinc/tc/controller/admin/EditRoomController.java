package ua.softserveinc.tc.controller.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.validator.RoomValidatorImpl;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Controller class for "Room list" view. It's main controller for editing rooms.
 */
@Controller
public class EditRoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomValidatorImpl roomValidator;

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
     * change room active state
     * @param id room id
     * @return room with change active state
     */
    @PostMapping(value = "/adm-edit-room", consumes = "application/json")
    @ResponseBody
    public Boolean roomBlockUnblock(@RequestBody Long id) {
        RoomDto roomDto = new RoomDto(roomService.changeActiveState(id));

        return roomDto.isActive();
    }

    /**
     * get booking warnings if bookings is active or have planning bookings
     * @param id room id
     * @return
     */
    @GetMapping("/adm-edit-room/warnings")
    @ResponseBody
    public String roomIsActiveBooking(@RequestParam Long id) {
        Room room = roomService.findByIdTransactional(id);
        List<String> warnings = roomValidator.checkRoomBookings(room);

        return new Gson().toJson(warnings);
    }
}
