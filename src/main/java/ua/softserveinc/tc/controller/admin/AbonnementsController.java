package ua.softserveinc.tc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.UsersAbonnementAssignmentDto;
import ua.softserveinc.tc.dto.UserAssigmentDto;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.mapper.PaginationMapper;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.validator.AbonnementsValidator;

import java.util.List;

@RestController
@RequestMapping("/")
public class AbonnementsController {

    @Autowired
    private Environment environment;

    @Autowired
    private AbonnementsService abonnementsService;

    @Autowired
    private AbonnementsValidator abonnementsValidator;

    @Autowired
    private PaginationMapper paginationMapper;

    @GetMapping("adm-all-abonnements")
    public List<AbonnementDto> selectAbonnements() {
        return abonnementsService.findAllAbonements();
    }

    @GetMapping("adm-pag-abonnements")
    public DataTableOutput<AbonnementDto> paginateAbonnements(@RequestParam String parameters) {
        SortingPagination sortingPagination = paginationMapper.mapSortingPaginationFromJson(parameters);
        return abonnementsService.paginationAbonnements(sortingPagination);
    }

    @GetMapping("adm-all-abonnement-assigment")
    public DataTableOutput<UserAssigmentDto> getAbonnementsAssigment(@RequestParam String parameters) {
        SortingPagination sortingPagination = paginationMapper.mapSortingPaginationFromJson(parameters);
        return abonnementsService.findAllPurchasedAbonnements(sortingPagination);
    }

    @GetMapping("adm-abonnement/{id}")
    public AbonnementDto selectAbonnement(@PathVariable long id) {
        return abonnementsService.findAbonnement(id);
    }

    @PutMapping("adm-abonnement")
    public AbonnementDto updateAbonnement(@RequestBody AbonnementDto abonnementDto,
                                          BindingResult bindingResult) {
        return abonnementsService.updateAbonnement(abonnementDto);
    }

    @PutMapping("adm-active-abonnement")
    public ResponseEntity<Boolean> updateActiveState(@RequestBody AbonnementDto abonnementDto) {
        abonnementsService.updateActiveState(abonnementDto);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("adm-assign-abonnement")
    public ResponseEntity<?> assignUser(@RequestBody UsersAbonnementAssignmentDto usersAbonnementAssignmentDto) {
        abonnementsService.assignUserToAbonnement(usersAbonnementAssignmentDto);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("adm-abonnement")
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

    @GetMapping("abonnement-max-price")
    public long getMaxPrice() {
        return abonnementsService.getMaxAbonnementsPrice();
    }

    @GetMapping("abonnement-min-price")
    public long getMinPrice() {
        return abonnementsService.getMinAbonnementsPrice();
    }
}
