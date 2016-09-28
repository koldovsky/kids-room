package ua.softserveinc.tc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/adm-days-off")
public class DayOffPageController {


    @RequestMapping(method = RequestMethod.GET)
    public String getDaysOffPage() {
        return "adm-days-off";
    }
}
