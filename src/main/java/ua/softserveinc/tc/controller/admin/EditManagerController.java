package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

@Controller
public class EditManagerController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.GET)
    public ModelAndView getManagerMenu() {
        List<User> managers = userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView mav = new ModelAndView(AdminConstants.EDIT_MANAGER);
        mav.addObject(AdminConstants.MANAGER_LIST, managers);

        return mav;
    }

    @RequestMapping(value = "/adm-edit-manager", method = RequestMethod.POST)
    public String managerBlockUnblock(@RequestParam Long id) {
        User manager = userService.findById(id);
        if (manager.isActive()) {
            manager.setActive(false);
        } else {
            manager.setActive(true);
        }
        userService.update(manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
