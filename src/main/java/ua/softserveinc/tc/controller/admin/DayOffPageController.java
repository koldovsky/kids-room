package ua.softserveinc.tc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DayOffPageController {


    @GetMapping("/adm-days-off")
    public String getDaysOffPage() {
        return "adm-days-off";
    }
}
