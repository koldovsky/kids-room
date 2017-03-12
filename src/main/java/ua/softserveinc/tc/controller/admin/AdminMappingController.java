package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.service.AbonnementsService;

import java.util.List;

@Controller
@RequestMapping("/")
public class AdminMappingController {

    @Autowired
    AbonnementsService abonnementsService;

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

}
