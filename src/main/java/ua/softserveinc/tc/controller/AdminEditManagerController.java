package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.AdminConst;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

/**
 * Created by TARAS on 16.05.2016.
 */

@Controller
public class AdminEditManagerController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.GET)
    public ModelAndView getManagerMenu() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView mav = new ModelAndView(AdminConst.EDIT_MANAGER);//"adm-edit-manager"
        mav.addObject(AdminConst.MANAGER_LIST, managers);//"managerList"

        return mav;
    }

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.POST)
    public String deleteManager(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/" + AdminConst.EDIT_MANAGER;//"adm-edit-manager"
    }

}
