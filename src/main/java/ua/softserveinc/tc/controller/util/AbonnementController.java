package ua.softserveinc.tc.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.UserAbonnementInfoDto;
import ua.softserveinc.tc.service.AbonnementsService;

import java.util.List;

@RestController
@RequestMapping("/restful/abonnement")
public class AbonnementController {

    @Autowired
    AbonnementsService abonnementsService;

    @GetMapping("/{userId}")
    public List<UserAbonnementInfoDto> getMyDto(@PathVariable Long userId) {
        return abonnementsService.getAbonnementInfoByUserId(userId);
    }
}