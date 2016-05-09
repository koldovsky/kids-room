package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import javax.validation.Valid;

/**
 * Created by Chak on 07.05.2016.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String start(Model model){
        return "index";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, @Valid User u, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        user.setRole(Role.USER);
        userService.create(user);
        return "login";
    }
  /*  @RequestMapping("/some")
    public String some(){
        return "listChildren";
    }*/
}
/*
 @RequestMapping(value = "/report**", method = RequestMethod.GET)
    public ModelAndView myKids(Principal principal) {
        ModelAndView model = new ModelAndView();
        model.setViewName("report");

        List<User> parentsList = userService.getAllParents();

        ModelMap modelMap = model.getModelMap();
        modelMap.addAttribute("parents", parentsList);
        return model;
    }
}
*/
