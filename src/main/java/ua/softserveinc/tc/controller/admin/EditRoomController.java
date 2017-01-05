package ua.softserveinc.tc.controller.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Logger logger = Logger.getLogger("EditRoomController");

    /**
     * Method send  model with all rooms into view.
     * Mapped by AdminConstants.EDIT_ROOM constant.
     *
     * @return model
     */
    @RequestMapping(method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> roomBlockUnblock(@RequestParam Long id) {
        Room room = this.roomService.findById(id);
        if(!room.isActive() || isRoomWithoutBookings(room)) {
            room.setActive(!room.isActive());
            this.roomService.update(room);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("error");
        }
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    private boolean isRoomWithoutBookings(Room room) {
        List<BookingDto> bookings = roomService.getAllFutureBookings(room);
        return bookings.isEmpty();
    }

}
