package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.entity.ReturnModel;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import javax.validation.Valid;

@RestController
public class AddManagerController {

    @Autowired
    private UserService userService;

    @PostMapping("/admincreatenewmanager")
    public ReturnModel createNewManagerDialogForm(@Valid @RequestBody User manager,
                                                  BindingResult bindingResult) {
        return  userService.adminAddManager(manager, bindingResult);
    }
}
