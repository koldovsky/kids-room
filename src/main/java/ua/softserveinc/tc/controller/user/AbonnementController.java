package ua.softserveinc.tc.controller.user;

import org.opensaml.ws.message.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.UserAbonnementInfoDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.dto.UserAbonnementsDto;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/restful/abonnement")
public class AbonnementController {

    @Autowired
    private AbonnementsService abonnementsService;


    @GetMapping("/{userId}")
    public List<UserAbonnementInfoDto> getAbonnementInfoForUser(@PathVariable Long userId) {
        return abonnementsService.getAbonnementInfoByUserId(userId);
    }

    @GetMapping
    public List<AbonnementDto> getAbonnementsToBuy() {
        return abonnementsService.findAllAbonements();
    }

    @PostMapping("/send-email")
    public void sendNotificationToAdmin(@RequestBody UserAbonnementsDto dto) {
        abonnementsService.sendNotificationToAssignAbonnement(dto);
    }
}