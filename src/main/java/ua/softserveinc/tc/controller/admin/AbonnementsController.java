package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.pagination.DataTableInput;
import ua.softserveinc.tc.dto.pagination.DataTableOutput;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.validator.AbonnementsValidator;

import java.util.List;

@RestController
@RequestMapping("/")
public class AbonnementsController {

    @Autowired
    Environment environment;

    @Autowired
    AbonnementsService abonnementsService;

    @Autowired
    AbonnementsValidator abonnementsValidator;

    @GetMapping(AbonnementConstants.RestQueries.SELECT_ABONNEMENTS)
    public List<AbonnementDto> selectAbonnements() {
        List<AbonnementDto> abonnementDtos = abonnementsService.findAllAbonements();
        return abonnementsService.findAllAbonements();
    }

    @PostMapping(AbonnementConstants.RestQueries.SELECT_ABONNEMENTS)
    public DataTableOutput<AbonnementDto> postSelectAbonnements(@RequestBody DataTableInput dataTable) {
        return abonnementsService.paginationAbonnements(dataTable);
    }

    @PutMapping(AbonnementConstants.RestQueries.UPDATE_ABONNEMENT)
    public AbonnementDto updateAbonnement(@RequestBody AbonnementDto abonnementDto) {
        return abonnementsService.updateAbonnement(abonnementDto);
    }

    @PutMapping(AbonnementConstants.RestQueries.UPDATE_ACTIVE_STATE)
    public ResponseEntity<Boolean> updateActiveState(@RequestBody AbonnementDto abonnementDto) {
        abonnementsService.updateActiveState(abonnementDto.getId(), abonnementDto.isActive());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(AbonnementConstants.RestQueries.CREATE_ABONNEMENT)
    public ResponseEntity<?> createAbonnement(@RequestBody AbonnementDto abonnementDto,
                                              BindingResult bindingResult) {
        abonnementsValidator.validate(abonnementDto, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(item -> {
                System.out.println(item.getCode());
                System.out.println(environment.getProperty(item.getCode()));
            });
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(abonnementsService.createAbonnement(abonnementDto), HttpStatus.OK);
    }
}
