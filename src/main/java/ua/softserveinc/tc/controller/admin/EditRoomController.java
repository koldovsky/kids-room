package ua.softserveinc.tc.controller.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.DeactivateRoomDto;
import ua.softserveinc.tc.dto.InfoDeactivateRoomDto;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.validator.RoomValidatorImpl;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.*;

/**
 * Controller class for "Room list" view. It's main controller for editing rooms.
 */
@Controller
public class EditRoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomValidatorImpl roomValidator;

    @Autowired
    private MailService mailService;

    @Autowired
    private BookingDao bookingDao;

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
     * @param deactivateRoomDto room id and reason for deactivate
     * @return room with change active state
     */
    @PostMapping(value = "/adm-edit-room", consumes = "application/json")
    @ResponseBody
    public Boolean roomBlockUnblock(@RequestBody DeactivateRoomDto deactivateRoomDto) {
        RoomDto roomDto = new RoomDto(roomService.changeActiveState(Long.valueOf(deactivateRoomDto.getId())));
        List<InfoDeactivateRoomDto> infoDeactivateRoomDtoList = bookingDao.getInfoForDeactivate(roomDto.getId());
        if (deactivateRoomDto.getReason() != null && infoDeactivateRoomDtoList.size() > 0) {
            try {
                List<String> listEmailManagers = roomService.emailManagersByRoom(roomDto.getId());
                mailService.sendNotifyDeactivateRoom(listEmailManagers, roomDto.getName(), deactivateRoomDto.getReason(), infoDeactivateRoomDtoList);
            } catch (MessagingException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Notify email about deactivate room didn't send. With error " + e.getMessage());
            }
        }
        return roomDto.isActive();
    }

    /**
     * get booking warnings if bookings is active or have planning bookings
     * @param id room id
     * @return
     */
    @GetMapping(value = "/adm-edit-room/warnings", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String roomIsActiveBooking(@RequestParam Long id, Locale locale) {
        Room room = roomService.findByIdTransactional(id);
        List<String> warnings = roomValidator.checkRoomBookings(room, locale);

        return new Gson().toJson(warnings);
    }
}
