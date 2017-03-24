package ua.softserveinc.tc.dao;

import java.util.Date;
import java.util.List;
import ua.softserveinc.tc.entity.DayDiscount;
import ua.softserveinc.tc.entity.PersonalDiscount;


public interface PersonlaDiscountDao extends BaseDao<PersonalDiscount> {

  PersonalDiscount getPersonalDiscountById(long id);

  void updatePersonalDiscountById(PersonalDiscount newPersonalDiscount);

  void updatePersonalDiscountState(PersonalDiscount newPersonalDiscount);

  List<PersonalDiscount> getPersonalDiscountByUser(long id);

}
