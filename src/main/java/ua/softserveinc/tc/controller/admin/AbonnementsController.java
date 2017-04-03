package ua.softserveinc.tc.controller.admin;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.mapper.PaginationMapper;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.util.JsonUtil;
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

    @Autowired
    PaginationMapper paginationMapper;

    @GetMapping("adm-all-abonnements")
    public List<AbonnementDto> selectAbonnements() {
        return abonnementsService.findAllAbonements();
    }

    @GetMapping("adm-pag-abonnements")
    public DataTableOutput<AbonnementDto> paginateAbonnements(@RequestParam String parameters) {
        SortingPagination sortingPagination = paginationMapper.mapSortingPaginationFromJson(parameters);
        return abonnementsService.paginationAbonnements(sortingPagination);
    }

    @PutMapping("adm-update-abonnement")
    public AbonnementDto updateAbonnement(@RequestBody AbonnementDto abonnementDto,
                                          BindingResult bindingResult) {
        return abonnementsService.updateAbonnement(abonnementDto);
    }

    @PutMapping("adm-active-abonnement")
    public ResponseEntity<Boolean> updateActiveState(@RequestBody AbonnementDto abonnementDto) {
        abonnementsService.updateActiveState(abonnementDto.getId(), abonnementDto.isActive());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("adm-create-abonnement")
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
