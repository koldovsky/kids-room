package ua.softserveinc.tc.service;

import java.util.List;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.PersonalDiscount;

public interface PersonalDiscountService extends BaseService<PersonalDiscount> {

  List<PersonalDiscountDTO> findAllPersonalDiscounts();

  void addNewPersonalDiscount(PersonalDiscountDTO personalDiscountDTO);

  void updatePersonalDiscountById(PersonalDiscountDTO personalDiscountDTO);

  PersonalDiscountDTO findPersonalDiscountById(long id);

  void changePersonalDiscountState(PersonalDiscountDTO personalDiscountDTO);

  PersonalDiscountDTO getPersonalDiscountByUserId(long id);

}
