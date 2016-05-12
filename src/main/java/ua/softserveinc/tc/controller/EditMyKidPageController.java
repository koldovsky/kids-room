package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.ChildValidator;

import java.security.Principal;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Nestor on 10.05.2016.
 */

@Controller
public class EditMyKidPageController {

    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChildValidator validator;

    @RequestMapping(value="/editmykid",
            method = RequestMethod.GET)
    public ModelAndView selectKid(
            @RequestParam("kidId") String kidId,
            Principal principal)
    {
        ModelAndView model = new ModelAndView();
        model.setViewName(MyKidsConst.KID_EDITING_VIEW);

        Child kidToEdit = childService
                .findById(Long.parseLong(kidId));

        model.getModelMap()
                .addAttribute(MyKidsConst.KID_ATTRIBUTE, kidToEdit);
        return model;
    }

    @RequestMapping(value="/editmykid",
            method = RequestMethod.POST)
    public String submit(
            @ModelAttribute(value = MyKidsConst.KID_ATTRIBUTE) Child kidToEdit,
            Principal principal,
            BindingResult bindingResult){
        kidToEdit.setParentId(
                userService.getUserByEmail(
                        principal.getName()));
        kidToEdit.setEnabled(true);
        validator.validate(kidToEdit, bindingResult);

        if(bindingResult.hasErrors()) {
            return MyKidsConst.KID_EDITING_VIEW;
        }

        childService.update(kidToEdit);
        return "redirect:/" + MyKidsConst.MY_KIDS_VIEW;
    }

    @RequestMapping(value = "/removemykid",
    method = RequestMethod.GET)
    public String removeKid(@RequestParam("id") String id){
        Child kidToRemove = childService.findById(Long.parseLong(id));
        kidToRemove.setEnabled(false);
        childService.update(kidToRemove);
        return "redirect:/" + MyKidsConst.MY_KIDS_VIEW;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

}
