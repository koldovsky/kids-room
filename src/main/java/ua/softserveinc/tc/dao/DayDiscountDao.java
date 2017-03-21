package ua.softserveinc.tc.dao;

import java.util.Date;
import java.util.List;
import ua.softserveinc.tc.entity.DayDiscount;


public interface DayDiscountDao extends BaseDao<DayDiscount> {

  DayDiscount getDayDiscountById(long id);

  void updateDayDiscountById(DayDiscount newDayDiscount);

  List<DayDiscount> findDayDiscountsForCurrentPeriod(Date startDate,Date endDate);

}
