package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.service.CityService;

import java.util.List;

/**
 * Created by TARAS on 16.05.2016.
 */
@Controller
public class AdminEditCityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/adm-edit-city", method = RequestMethod.GET)
    public ModelAndView getCityMenu() {
        List<City> cities = cityService.findAll();

        ModelAndView mav = new ModelAndView("adm-edit-city");
        mav.addObject("cityList", cities);

        return mav;
    }

    @RequestMapping(value = "/adm-edit-city", method = RequestMethod.POST)
    public String deleteCity(@RequestParam Long id){
        cityService.deleteCityById(id);
        return "redirect:/" + "adm-edit-city";
    }
}
