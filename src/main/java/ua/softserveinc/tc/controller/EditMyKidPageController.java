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
                                  @RequestParam("kidPosition") String kidPosition,
                                  Principal principal){
        System.out.println(kidPosition);
        ModelAndView model = new ModelAndView();
        model.setViewName("editmykid");
        List<Child> kids = new ArrayList<>(
                userService.getUserByEmail(
                        principal.getName())
                                .getChildren());
        kids.sort((o1, o2)->{
            if(o1.getId()>o2.getId())
                return 1;
            else if(o1.getId()<o2.getId())
                return -1;
            return 0;
        });

        Child kidToEdit = kids.get(Integer.parseInt(kidPosition));
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
