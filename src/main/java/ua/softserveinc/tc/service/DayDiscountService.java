package ua.softserveinc.tc.service;


import java.util.List;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.DayDiscount;

public interface DayDiscountService extends BaseService<DayDiscount> {
  List<DayDiscountDTO> findAllDailyDiscounts();
}
