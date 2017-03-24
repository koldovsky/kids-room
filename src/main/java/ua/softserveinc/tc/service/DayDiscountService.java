package ua.softserveinc.tc.service;


import java.util.Date;
import java.util.List;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.DayDiscount;

public interface DayDiscountService extends BaseService<DayDiscount> {

  List<DayDiscountDTO> findAllDailyDiscounts();

  void addNewDayDiscount(DayDiscountDTO dto);

  DayDiscountDTO findDayDiscountById(long id);

  void updateDayDiscountById(DayDiscountDTO dto);

  void changeDayDiscountState(DayDiscountDTO dto);

  List<DayDiscountDTO> getDayDiscountsForPeriod(Date startDate,Date endDate);
}
