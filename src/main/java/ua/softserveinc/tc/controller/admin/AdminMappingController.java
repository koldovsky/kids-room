package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.service.AbonnementsService;

import java.security.Principal;
import java.util.List;

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
}
