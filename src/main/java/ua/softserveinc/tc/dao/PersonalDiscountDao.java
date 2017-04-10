package ua.softserveinc.tc.dao;

import java.time.LocalTime;
import java.util.List;
import ua.softserveinc.tc.entity.PersonalDiscount;


public interface PersonalDiscountDao extends BaseDao<PersonalDiscount> {

  List<PersonalDiscount> getPersonalDiscountByUser(long id);

  List<PersonalDiscount> getPersonalDiscountByPeriod(LocalTime startTime, LocalTime endTime,
      Long id);

  void changePersonalDiscountState(Long id, Boolean state);

}
