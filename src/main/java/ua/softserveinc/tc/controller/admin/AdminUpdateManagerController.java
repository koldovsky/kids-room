package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by TARAS on 18.05.2016.
 */
@Controller
public class AdminUpdateManagerController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/adm-update-manager", method = RequestMethod.GET)
    public ModelAndView updateManager(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView(AdminConst.UPDATE_MANAGER);

        User manager = userService.findById(id);
        model.getModelMap().addAttribute(AdminConst.ATR_MANAGER, manager);

        return model;
    }

    @RequestMapping(value = "/adm-update-manager", method = RequestMethod.POST)
    public String submitManagerUpdate(@ModelAttribute("manager") User manager) {
        manager.setRole(Role.MANAGER);
        manager.setConfirmed(true);
        userService.update(manager);

        return "redirect:/" + AdminConst.EDIT_MANAGER;
    }
}
