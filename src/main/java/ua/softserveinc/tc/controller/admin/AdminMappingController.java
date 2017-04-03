package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.AdminConstants.AdminModel;
import ua.softserveinc.tc.constants.AdminConstants.ViewNames;
import ua.softserveinc.tc.service.AbonnementsService;
import java.security.Principal;


@Controller
@RequestMapping("/")
public class AdminMappingController {

    @Autowired
    AbonnementsService abonnementsService;

    @GetMapping(AdminConstants.ABONNEMENTS)
    public String getAdminAbonnements() {
//        List<AbonnementDto> abonnements = abonnementsService.findAllAbonements();
//        model.addAttribute(AdminConstants.AdminModel.ABONNEMENTS_MODEL, abonnements);
        return AdminConstants.ViewNames.ABONNEMENTS_VIEW;
    }

    @GetMapping(AdminModel.DISCOUNTS)
    public String getAdminDiscount(Principal principal, Model model){
        //List<AbonnementDto> abonnements = abonnementsService.findAllAbonements();
        //model.addAttribute(AdminConstants.AdminModel.ABONNEMENTS_MODEL, abonnements);
        return ViewNames.DISCOUNT_VIEW;
    }

    @GetMapping(AdminConstants.ROOM)
    public String getAdminRoom(Principal principal, Model model){
        return AdminConstants.ROOM;
    }
}
