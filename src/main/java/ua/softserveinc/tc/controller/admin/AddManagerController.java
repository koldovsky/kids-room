package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.dto.UserDto;
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

    @GetMapping("getManager/{id}")
    public UserDto showAddManagerForm(@PathVariable Long id) {
        return new UserDto(userService.findUserId(id));
    }

    @PutMapping("/getmanagerforupdate")
    public ReturnModel getManagerForUpdate(@Valid @RequestBody User manager,
                                           BindingResult bindingResult) {
        return userService.adminUpdateManager(manager, bindingResult);
    }
    private void consoleLog(){
        System.out.println("Hello world");
    }
    private void consoleLog2(){
        System.out.println("Hello world2");
    }

}
