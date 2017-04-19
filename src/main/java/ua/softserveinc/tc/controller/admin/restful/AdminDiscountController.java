package ua.softserveinc.tc.controller.admin.restful;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.mapper.PaginationMapper;
import ua.softserveinc.tc.server.exception.NoSuchRowException;
import ua.softserveinc.tc.server.exception.ResponseException;
import ua.softserveinc.tc.server.exception.UserInputException;
import ua.softserveinc.tc.service.DayDiscountService;
import ua.softserveinc.tc.service.PersonalDiscountService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;

@RestController
@RequestMapping("/restful/admin/discounts/")
public class AdminDiscountController {

  @Autowired
  private DayDiscountService dayDiscountService;

  @Autowired
  private PersonalDiscountService personalDiscountService;

  @Autowired
  private UserService userService;

  @Autowired
  PaginationMapper paginationMapper;

  //Day discounts methods are below
  @GetMapping("day")
  public DataTableOutput<DayDiscountDTO> getAllDayDiscounts(@RequestParam String parameters) {
    SortingPagination sortingPagination = paginationMapper.mapSortingPaginationFromJson(parameters);
    return dayDiscountService.paginateDayDiscount(sortingPagination);
  }

  @GetMapping("day/{id}")
  public ResponseEntity<?> getDayDiscountById(@PathVariable Long id) {
    try {
      DayDiscountDTO dto = dayDiscountService.findDayDiscountById(id);
      return new ResponseEntity<DayDiscountDTO>(dto, HttpStatus.OK);
    } catch (NoSuchRowException ex) {
      return new ResponseEntity<ResponseException>(new ResponseException(ex.getReason()),
          HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("day")
  public ResponseEntity<?> addDayDiscount(@RequestBody DayDiscountDTO dto,BindingResult bindingResult) {
    try{
      dayDiscountService.addNewDayDiscount(dto, bindingResult);
      return new ResponseEntity<String>("ok", HttpStatus.OK);
    }catch (UserInputException ex){
      return new ResponseEntity<ResponseException>(new ResponseException(ex.getErrorList()), HttpStatus.NOT_ACCEPTABLE);
    }
  }

  @PutMapping("day")
  public ResponseEntity<?> updateDayDiscount(@RequestBody DayDiscountDTO dto, BindingResult bindingResult) {
    try {
      dayDiscountService.updateDayDiscountById(dto, bindingResult);
      return new ResponseEntity<String>("ok", HttpStatus.OK);
    }catch (UserInputException ex){
      return new ResponseEntity<ResponseException>(new ResponseException(ex.getErrorList()), HttpStatus.NOT_ACCEPTABLE);
    }
  }

  @PutMapping("day/state")
  public ResponseEntity<String> changeDayDiscountState(@RequestBody DayDiscountDTO dto) {
    dayDiscountService.changeDayDiscountState(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

  //Personal discounts methods are below
  @GetMapping("personal")
  public DataTableOutput<PersonalDiscountDTO> getAllPersonalDiscounts(
      @RequestParam String parameters) {
    SortingPagination sortingPagination = paginationMapper.mapSortingPaginationFromJson(parameters);
    return personalDiscountService.paginatePersonalDiscount(sortingPagination);
  }

  @GetMapping("personal/{id}")
  public PersonalDiscountDTO getPersonalDiscountById(@PathVariable Long id) {
    return personalDiscountService.findPersonalDiscountById(id);
  }

  @GetMapping("personal/user/{id}")
  public List<PersonalDiscountDTO> getPersonalDiscountsByUserId(@PathVariable Long id) {
    return personalDiscountService.findPersonalDiscountByUserId(id);
  }

  @GetMapping("personal/users")
  public List<UserDto> getUsers() {
    return userService.findUsersByRoleDto(Role.USER);
  }

  @GetMapping("personal/users/{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return userService.findUserByIdDto(id);
  }

  @PostMapping("personal/{id}")
  public ResponseEntity<String> addPersonalDiscount(@RequestBody PersonalDiscountDTO dto,
      @PathVariable Long id) {
    dto.setUser(userService.findUserByIdDto(id));
    personalDiscountService.addNewPersonalDiscount(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

  @PutMapping("personal/{id}")
  public ResponseEntity<String> updatePersonalDiscount(@RequestBody PersonalDiscountDTO dto,
      @PathVariable Long id) {
    dto.setUser(userService.findUserByIdDto(id));
    personalDiscountService.updatePersonalDiscountById(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

  @PutMapping("personal/state")
  public ResponseEntity<String> changePersonalDiscountState(@RequestBody Map<String, String> dto) {
    personalDiscountService.changePersonalDiscountState(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

}
