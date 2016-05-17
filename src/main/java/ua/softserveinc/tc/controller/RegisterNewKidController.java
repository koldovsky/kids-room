package ua.softserveinc.tc.controller;

import org.omg.CosNaming.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.ChildValidator;

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

    @Autowired
    private ChildValidator childValidator;

    @RequestMapping(value = "/registerkid", method = RequestMethod.GET)
    public String registerKid(Model model){
        if(!model.containsAttribute(MyKidsConst.KID_ATTRIBUTE)) {
            model.addAttribute(MyKidsConst.KID_ATTRIBUTE, new Child());
        }
        return MyKidsConst.KID_REGISTRATION_VIEW;
    }

    @RequestMapping(value="/registerkid", method = RequestMethod.POST)
    public String submit(@ModelAttribute(value = MyKidsConst.KID_ATTRIBUTE) Child child,
                         Principal principal,
                         BindingResult bindingResult){

        child.setParentId(
                userService.getUserByEmail(
                        principal.getName()));

        childValidator.validate(child, bindingResult);

        if(bindingResult.hasErrors()) {
            return MyKidsConst.KID_REGISTRATION_VIEW;
        }

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