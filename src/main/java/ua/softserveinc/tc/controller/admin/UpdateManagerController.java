package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import javax.validation.Valid;

/**
 * Controller class for "Update manager" view, which accompanies manager updates.
 * <p>
 * Created by TARAS on 18.05.2016.
 */
@Controller
@RequestMapping(value = "/adm-update-manager")
public class UpdateManagerController {

    @Autowired
    private UserService userService;


    /**
     * Method open "Update manager" view. Send model with values into view.
     * Mapped by AdminConstants.UPDATE_MANAGER constant.
     *
     * @return model
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showUpdateManagerForm(@RequestParam Long id) {
        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_MANAGER);

        User manager = this.userService.findById(id);
        model.getModelMap().addAttribute(AdminConstants.ATR_MANAGER, manager);

        return model;
    }


    /**
     * Method saving model with updated values received from view. Check value validation.
     * Redirect into view, witch mapped by AdminConstants.EDIT_MANAGER const.
     *
     * @param manager
     * @param bindingResult
     * @return String value
     */
    @RequestMapping(method = RequestMethod.POST)
    public String submitManagerUpdate(@Valid @ModelAttribute(AdminConstants.ATR_MANAGER) User manager,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AdminConstants.UPDATE_MANAGER;
        }
        manager.setRole(Role.MANAGER);
        this.userService.update(manager);
        return "redirect:/" + AdminConstants.EDIT_MANAGER;
    }
}
