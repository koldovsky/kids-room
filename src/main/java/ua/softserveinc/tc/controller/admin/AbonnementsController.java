package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.service.AbonnementsService;

import java.util.List;

@RestController
@RequestMapping("/")
public class AbonnementsController {

    @Autowired
    AbonnementsService abonnementsService;

    @PostMapping(AbonnementConstants.RestQueries.UPDATE_ABONNEMENT)
    public void updateAbonnement(@ModelAttribute(AdminConstants.ATR_ABONNEMENT) AbonnementDto abonnementDto,
                                    BindingResult bindingResult) {
        System.out.println(abonnementDto);
        abonnementsService.updateAbonnement(abonnementDto);
    }

}
