package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nestor on 07.05.2016.
 */

@Controller
public class RegisterNewKidController {
    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registerkid", method = RequestMethod.GET)
    public String registerKid(Model model){
        if(!model.containsAttribute("child")) {
            model.addAttribute("child", new Child());
        }
        return MyKidsConst.KID_REGISTRATION_VIEW;
    }

    @RequestMapping(value="/registerkid", method = RequestMethod.POST)
    public String submit(@ModelAttribute Child child, Principal principal){
        child.setParentId(
                userService.getUserByEmail(
                        principal.getName()));
        childService.create(child);
        return "redirect:/" + MyKidsConst.MY_KIDS_VIEW;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}