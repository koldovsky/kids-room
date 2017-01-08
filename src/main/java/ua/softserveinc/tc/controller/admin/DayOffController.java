package ua.softserveinc.tc.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.RoomService;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adm-days-off")
@Slf4j
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<List<DayOff>> allDaysOff() {
        List<DayOff> daysOff = dayOffService.findAll().stream()
                .sorted(Comparator.comparing(DayOff::getId).reversed())
                .collect(Collectors.toList());
        if (daysOff.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(daysOff, HttpStatus.OK);
    }

    @GetMapping(value = "/day/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DayOff> getDayOff(@PathVariable("id") long id) {

        DayOff currentDay = dayOffService.findById(id);
        if (currentDay == null) {
            System.out.println("While getting user with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDay, HttpStatus.OK);
    }

    @PostMapping("/day/")
    public ResponseEntity<Void> createDayOff(@RequestBody DayOff dayOff, UriComponentsBuilder ucBuilder) {
        if (dayOffService.dayOffExist(dayOff.getName(), dayOff.getStartDate())) {
            log.warn("There is another day off with the same name: " + dayOff.getName() + ", or" +
                    "with the same start date: " + dayOff.getStartDate());
        }
        dayOffService.create(dayOff);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/day/{id}").buildAndExpand(dayOff.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/day/{id}")
    public ResponseEntity<DayOff> updateDayOff(@PathVariable("id") long id, @RequestBody DayOff dayOff) {
        DayOff currentDay = dayOffService.findById(id);

        if (currentDay == null) {
            log.error("While updating user with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentDay.setName(dayOff.getName());
        currentDay.setStartDate(dayOff.getStartDate());
        currentDay.setEndDate(dayOff.getEndDate());

        Set<Room> currentRooms = new HashSet<>();
        for (Room room : dayOff.getRooms()) {
            currentRooms.add(roomService.findByIdTransactional(room.getId()));
        }
        currentDay.setRooms(currentRooms);

        dayOffService.update(currentDay);
        return new ResponseEntity<>(currentDay, HttpStatus.OK);
    }

    @DeleteMapping("/day/{id}")
    public ResponseEntity<DayOff> deleteUser(@PathVariable("id") long id) {

        DayOff currentDay = dayOffService.findById(id);
        if (currentDay == null) {
            log.error("While deleting user with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dayOffService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
