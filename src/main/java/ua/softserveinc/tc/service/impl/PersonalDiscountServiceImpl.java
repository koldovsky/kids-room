package ua.softserveinc.tc.service.impl;

import java.util.List;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.service.PersonalDiscountService;


public class PersonalDiscountServiceImpl extends BaseServiceImpl<PersonalDiscount> implements
    PersonalDiscountService {

  @Override
  public List<PersonalDiscountDTO> findAllPersonalDiscounts() {
    return null;
  }

  @Override
  public void addNewPersonalDiscount(PersonalDiscountDTO personalDiscountDTO) {

  }

  @Override
  public void updatePersonalDiscountById(PersonalDiscountDTO personalDiscountDTO) {

  }

  @Override
  public PersonalDiscountDTO findPersonalDiscountById(long id) {
    return null;
  }

  @Override
  public void changePersonalDiscountState(PersonalDiscountDTO personalDiscountDTO) {

  }

  @Override
  public PersonalDiscountDTO getPersonalDiscountByUserId(long id) {
    return null;
  }

  private PersonalDiscount convertToEntity(PersonalDiscountDTO dto){
    PersonalDiscount entity = new PersonalDiscount();
    return entity;
  }

  private PersonalDiscountDTO convertToDTO(PersonalDiscount entity){
    PersonalDiscountDTO dto = new PersonalDiscountDTO();
    dto.setId(entity.getId());
    dto.setReason(entity.getReason());
    dto.setValue(entity.getValue());
    dto.setStartTime(entity.getStartTime());
    dto.setEndTime(entity.getEndTime());
    dto.setActive(entity.getActive());
    return dto;
  }
}
