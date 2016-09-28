package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;

@RestController
@RequestMapping(value = "/adm-edit-room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> allRooms() {
        List<Room> rooms = roomService.findAll();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
