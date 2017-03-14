package ua.softserveinc.tc.controller.admin.restful;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  private List<DayDiscountDTO> getAllDailyDiscount(){
    return dayDiscountService.findAllDailyDiscounts();
  }
}
