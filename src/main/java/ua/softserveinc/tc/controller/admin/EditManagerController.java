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
@RequestMapping(value = "/adm-edit-manager")
public class EditManagerController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getManagerMenu() {
        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView mav = new ModelAndView(AdminConstants.EDIT_MANAGER);
        mav.addObject(AdminConstants.MANAGER_LIST, managers);

        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String managerBlockUnblock(@RequestParam Long id) {
        User manager = this.userService.findById(id);
        manager.setActive(!manager.isActive());

        this.userService.update(manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
