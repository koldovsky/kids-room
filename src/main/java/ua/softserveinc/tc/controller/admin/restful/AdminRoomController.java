package ua.softserveinc.tc.controller.admin.restful;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.DeactivateRoomDto;
import ua.softserveinc.tc.dto.InfoDeactivateRoomDto;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.RoomValidator;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restful/admin/rooms/")
public class AdminRoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomValidator roomValidator;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    private Logger logger = Logger.getLogger("EditRoomController");


    @GetMapping()
    public List<RoomDto> listRooms() {
        List<Room> listRoom = roomService.findAll();
        return listRoom.stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/deactivate-check")
    public List<String> roomIsActiveBooking(@RequestParam Long id, Locale locale) {
        Room room = roomService.findByIdTransactional(id);
        List<String> warnings = roomValidator.checkRoomBookings(room, locale);

        return warnings;
    }

    @PostMapping(value = "/deactivate")
    public Boolean roomBlockUnblock(@RequestBody DeactivateRoomDto deactivateRoomDto) {
        RoomDto roomDto = new RoomDto(roomService.changeActiveState(Long.valueOf(deactivateRoomDto.getId())));
        List<InfoDeactivateRoomDto> infoDeactivateRoomDtoList = bookingDao.getInfoForDeactivate(roomDto.getId());
        if (deactivateRoomDto.getReason() != null && infoDeactivateRoomDtoList.size() > 0) {
            try {
                List<String> listEmailManagers = roomService.emailManagersByRoom(roomDto.getId());
                mailService.sendNotifyDeactivateRoom(listEmailManagers, roomDto.getName(), deactivateRoomDto.getReason(), infoDeactivateRoomDtoList);
            } catch (MessagingException e) {
                logger.log(Level.WARNING, "Notify email about deactivate room didn't send. With error " + e.getMessage());
            }
        }
        return roomDto.isActive();
    }

    @PostMapping()
    public String addRoom(@RequestBody RoomDto dto, BindingResult bindingResult) {
        return roomService.saveOrUpdate(dto, bindingResult);
    }

    @PutMapping()
    public String updateRoom(@RequestBody RoomDto dto, BindingResult bindingResult) {
        return roomService.saveOrUpdate(dto, bindingResult);
    }

    @GetMapping(value = "/list-manager")
    public List<UserDto> listMsnagers() {
        List<User> listUser = userDao.findAllUsersByRole(Role.MANAGER);
        return listUser.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public RoomDto getRoomById(@PathVariable long id) {
        return new RoomDto(roomService.findByIdTransactional(id));

    }
}

