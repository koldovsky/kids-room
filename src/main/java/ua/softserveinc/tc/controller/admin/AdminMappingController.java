package ua.softserveinc.tc.controller.admin;

import static ua.softserveinc.tc.util.DateUtil.dateMonthAgo;
import static ua.softserveinc.tc.util.DateUtil.dateNow;
import static ua.softserveinc.tc.util.DateUtil.getStringDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.constants.AdminConstants.AdminModel;
import ua.softserveinc.tc.constants.AdminConstants.ViewNames;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.AbonnementsService;

import java.security.Principal;
import java.util.List;
import ua.softserveinc.tc.service.UserService;

@Controller
@RequestMapping("/")
public class AdminMappingController {

    @Autowired
    private AbonnementsService abonnementsService;

    @Autowired
    private UserService userService;

    @GetMapping(AdminConstants.ABONNEMENTS)
    public String getAdminAbonnements(Model model) {
        List<AbonnementDto> abonnements = abonnementsService.findAllAbonements();
        model.addAttribute(AdminConstants.AdminModel.ABONNEMENTS_MODEL, abonnements);
        return AdminConstants.ViewNames.ABONNEMENTS_VIEW;
    }

    @GetMapping(AdminConstants.UPDATE_ABONNEMENT)
    public String getAdminUpdateAbonnement(@RequestParam Long id, Model model) {
        model.addAttribute(AdminConstants.AdminModel.ABONNEMENT, abonnementsService.findAbonnement(id));
        return AdminConstants.ViewNames.UPDATE_ABONNEMENT_VIEW;
    }

    @GetMapping(AdminConstants.CREATE_ABONNEMENT)
    public String getAdminCreateAbonnement(Model model) {
        model.addAttribute(AdminConstants.AdminModel.ABONNEMENT, new AbonnementDto());
        return AdminConstants.ViewNames.CREATE_ABONNEMENT_VIEW;
    }

    @GetMapping(AdminModel.DISCOUNTS)
    public String getAdminDiscount(Principal principal, Model model){
        //List<AbonnementDto> abonnements = abonnementsService.findAllAbonements();
        //model.addAttribute(AdminConstants.AdminModel.ABONNEMENTS_MODEL, abonnements);
        return ViewNames.DISCOUNT_VIEW;
    }

}
