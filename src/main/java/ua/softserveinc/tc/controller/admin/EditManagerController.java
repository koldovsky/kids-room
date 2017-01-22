package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

/**
 * Controller class for "Manager list" view. It's main controller for editing managers.
 */
@Controller
public class EditManagerController {

    @Autowired
    private UserService userService;


    /**
     * Method send  model with all managers into view.
     * Mapped by AdminConstants.EDIT_MANAGER constant.
     *
     * @return model
     */
    @GetMapping("/adm-edit-manager")
    public ModelAndView showAllManagersForm() {
        List<User> managers = this.userService.findAllUsersByRole(Role.MANAGER);

        ModelAndView model = new ModelAndView(AdminConstants.EDIT_MANAGER);
        model.addObject(AdminConstants.MANAGER_LIST, managers);

        return model;
    }


    /**
     * Method receive manager id from view. Set setActive() for manager opposite to previous value.
     * This mean record will lock or unlock, based on the received state.
     * Redirect into view, which mapped by AdminConstants.EDIT_MANAGER const
     *
     * @return String value
     */
    @PostMapping("/adm-edit-manager")
    public String managerBlockUnblock(@RequestParam Long id) {
        User manager = this.userService.findByIdTransactional(id);
        manager.setActive(!manager.isActive());

        this.userService.update(manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
