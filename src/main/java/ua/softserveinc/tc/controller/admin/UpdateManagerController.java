package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class UpdateManagerController {

    @Autowired
    private UserService userService;


    /**
     * Method open "Update manager" view. Send model with values into view.
     * Mapped by AdminConstants.UPDATE_MANAGER constant.
     *
     * @return model
     */
    @GetMapping("/adm-update-manager")
    public ModelAndView showUpdateManagerForm(@RequestParam Long id) {
        User manager = this.userService.findById(id);

        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_MANAGER);
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
    @PostMapping("/adm-update-manager")
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
