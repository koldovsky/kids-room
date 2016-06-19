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


@Controller
@RequestMapping(value = "/adm-update-manager")
public class UpdateManagerController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showUpdateManagerForm(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView(AdminConstants.UPDATE_MANAGER);

        User manager = this.userService.findById(id);
        model.getModelMap().addAttribute(AdminConstants.ATR_MANAGER, manager);

        return model;
    }

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
