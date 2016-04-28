package ua.softserveinc.tc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/allUsers", "/" })
    public String getAllUsers(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("roleList", Role.values());
        return "allUsers";
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String creatingAppartment(Model model, @RequestParam(value = "enable", required = false) String sEnable,
            @RequestParam(value = "userRole") Role role, @RequestParam(value = "id") int id) {
        boolean enable = ((sEnable == null) ? false : true);
        User u = userService.findById(id);
        u.setRole(role);
        u.setEnable(enable);
        userService.update(u);
        return "redirect:/allUsers";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {

        model.addAttribute("roleList", Role.values());
        model.addAttribute("user", new User());
        return "userForm";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/allUsers";
    }

}
