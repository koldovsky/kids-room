package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {
@Autowired
    UserService us;
    @RequestMapping(method = RequestMethod.GET)
    public String start(Model model){

        return "index";
    }

}
