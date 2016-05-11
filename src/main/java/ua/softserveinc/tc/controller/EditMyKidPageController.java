package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


/**
 * Created by Nestor on 10.05.2016.
 */

@Controller
public class EditMyKidPageController {

    @Autowired
    ChildService childService;

    @Autowired
    UserService userService;

    @RequestMapping(value="/editmykid",
            method = RequestMethod.GET)
    public ModelAndView selectKid(
                                  @RequestParam("kidId") String kidId,
                                  Principal principal)
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("editmykid");

        Child kidToEdit = childService
                .findById(Long.parseLong(kidId));
        model.getModelMap().addAttribute("kidToEdit", kidToEdit);
        return model;
    }

    @RequestMapping(value="/editmykid",
            method = RequestMethod.POST)
    public String submit(@ModelAttribute Child kidToEdit, Principal principal){
        kidToEdit.setParentId(
                userService.getUserByEmail(
                        principal.getName()));
        childService.update(kidToEdit);
        return "redirect:/mykids";
    }

}
