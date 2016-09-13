package ua.softserveinc.tc.controller.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.util.Log;

import java.util.List;

@RestController
@RequestMapping(value = "/adm-days-off")
public class DayOffController {

    @Log
    private static Logger log;

    @Autowired
    private DayOffService dayOffService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<DayOff>> allDaysOff() {
        List<DayOff> daysOff = dayOffService.findAll();
        if (daysOff.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(daysOff, HttpStatus.OK);
    }

    @RequestMapping(value = "/day/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DayOff> getUser(@PathVariable("id") long id) {

        DayOff currentDay = dayOffService.findById(id);
        if (currentDay == null) {
            System.out.println("While getting user with id " + id + " not found");
            return new ResponseEntity<DayOff>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DayOff>(currentDay, HttpStatus.OK);
    }

    @RequestMapping(value = "/day/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DayOff> updateDayOff(@PathVariable("id") long id, @RequestBody DayOff dayOff) {
        DayOff currentDay = dayOffService.findById(id);

        if (currentDay == null) {
            log.error("While updating user with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentDay.setName(dayOff.getName());
        currentDay.setStartDate(dayOff.getStartDate());
        currentDay.setEndDate(dayOff.getEndDate());

        dayOffService.upsert(currentDay);
        return new ResponseEntity<>(currentDay, HttpStatus.OK);
    }

    @RequestMapping(value = "/day/{id}", method = RequestMethod.DELETE)
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
