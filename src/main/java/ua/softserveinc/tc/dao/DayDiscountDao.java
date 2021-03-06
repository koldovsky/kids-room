package ua.softserveinc.tc.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import ua.softserveinc.tc.entity.DayDiscount;


public interface DayDiscountDao extends BaseDao<DayDiscount> {

  List<DayDiscount> getAllActiveDayDiscount();

  List<DayDiscount> getDayDiscountForCurrentDays(LocalDate startDate, LocalDate endDate,
      LocalTime startTime, LocalTime endTime, Boolean state);

  List<DayDiscount> getDayDiscountForValidate(LocalDate startDate, LocalDate endDate,
      LocalTime startTime, LocalTime endTime, Long id);

  void changeDayDiscountState(Long id, Boolean state);

}
