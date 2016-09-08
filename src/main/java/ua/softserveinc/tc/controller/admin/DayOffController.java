package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.service.DayOffService;

import java.util.List;


@RestController
@RequestMapping(value = "/adm-days-off")
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAllManagersForm() {
        List<DayOff> daysOff = dayOffService.findAll();

        ModelAndView model = new ModelAndView("adm-days-off");
        model.addObject("daysOff", daysOff);

        return model;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<DayOff>> allDaysOff() {
        List<DayOff> daysOff = dayOffService.findAll();
        if(daysOff.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(daysOff, HttpStatus.OK);
    }

}
