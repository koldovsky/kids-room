package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.service.DayOffService;

import java.util.List;


@RestController

public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @RequestMapping(value = "/adm-days-off", method = RequestMethod.GET)
    public ResponseEntity<List<DayOff>> listAllUsers() {
        List<DayOff> daysOff = dayOffService.findAll();
        if(daysOff.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(daysOff, HttpStatus.OK);
    }



}
