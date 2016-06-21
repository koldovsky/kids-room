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

/**
 * Controller class for "Manager list" view. It's main controller for editing managers.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/adm-edit-manager")
public class EditManagerController {

    @Autowired
    private UserService userService;


    /**
     * Method send  model with all managers into view.
     * Mapped by AdminConstants.EDIT_MANAGER constant.
     *
     * @return model
     */
    @RequestMapping(method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.POST)
    public String managerBlockUnblock(@RequestParam Long id) {
        User manager = this.userService.findById(id);
        manager.setActive(!manager.isActive());

        this.userService.update(manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
