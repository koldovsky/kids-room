package ua.softserveinc.tc.controller.admin.restful;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.DayDiscount;
import ua.softserveinc.tc.service.DayDiscountService;

/**
 * Created by Tat0 on 15.03.2017.
 */
@RestController
@RequestMapping("/restful/admin/discounts/")
public class AdminDiscountController {

  @Autowired
  private DayDiscountService dayDiscountService;

  @GetMapping()
  private List<DayDiscountDTO> getAllDailyDiscount() {
    return dayDiscountService.findAllDailyDiscounts();
  }

  @GetMapping("/{id}")
  public DayDiscountDTO getDayDiscountById(@PathVariable long id) {
    return dayDiscountService.findDayDiscountById(id);
  }

  @GetMapping("/{startDate}/{endDate}/{startTime}/{endTime}")
  public ResponseEntity<String> checkUniqueOfDayDiscount(@PathVariable String startDate,
      @PathVariable String endDate,@PathVariable String startTime ,@PathVariable String endTime) {
    System.out.println(
    dayDiscountService.getDayDiscountsForPeriod(LocalDate.parse(startDate),LocalDate.parse(endDate),
        LocalTime.parse(startTime),LocalTime.parse(endTime)).size()
    );
    return new ResponseEntity<String>("lol",HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<String> addDayDiscount(@RequestBody DayDiscountDTO dto) {
    dayDiscountService.addNewDayDiscount(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

  @PutMapping()
  public ResponseEntity<String> updateDayDiscount(@RequestBody DayDiscountDTO dto) {
    dayDiscountService.updateDayDiscountById(dto);
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }

}
