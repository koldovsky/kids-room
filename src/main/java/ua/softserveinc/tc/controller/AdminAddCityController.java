package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.service.CityService;

/**
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class AdminAddCityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/adm-add-city", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute City city) {
        cityService.create(city);
        return "redirect:/" + "adm-edit-city";
    }

    @RequestMapping(value = "/adm-add-city", method = RequestMethod.GET)
    public String showCreateCityForm() {
        return "adm-add-city";
    }

}
