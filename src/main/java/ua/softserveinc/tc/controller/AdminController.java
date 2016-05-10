package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.CityService;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by TARAS on 07.05.2016.
 */

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.GET)
    public String adminEditManager(Model model){
        return "adm-edit-manager";
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.GET)
    public String adminAddManager(Model model){
        model.addAttribute("roleList", Role.values());
        model.addAttribute("user", new User());

        return "adm-add-manager";
    }

    @RequestMapping(value = "/adm-add-manager", method = RequestMethod.POST)
    public String saveManager(@ModelAttribute User user){
        user.setRole(Role.MANAGER);
        userService.create(user);
        return "adm-add-manager";
    }

    @RequestMapping(value = "/adm-edit-location", method = RequestMethod.GET)
    public String adminEditLocation(Model model){
        return "adm-edit-location";
    }


    @RequestMapping(value = "/adm-add-location", method = RequestMethod.GET)
    public String adminAddLocation(Model model){
        model.addAttribute(new City());

        return "adm-add-location";
    }

    @RequestMapping(value = "/adm-add-location", method = RequestMethod.POST)
    public String saveLocation(@ModelAttribute City city){

        city.setNameRoom("ssss");
        cityService.create(city);
        return "adm-add-location";
    }
}
