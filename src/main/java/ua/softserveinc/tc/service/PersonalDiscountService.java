package ua.softserveinc.tc.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;

public interface PersonalDiscountService extends BaseService<PersonalDiscount> {

  List<PersonalDiscountDTO> findAllPersonalDiscounts();

  void addNewPersonalDiscount(PersonalDiscountDTO personalDiscountDTO,BindingResult bindingResult);

  void updatePersonalDiscountById(PersonalDiscountDTO personalDiscountDTO, BindingResult bindingResult);

  void changePersonalDiscountState(PersonalDiscountDTO dto);

  PersonalDiscountDTO findPersonalDiscountById(Long id);

  List<PersonalDiscountDTO> findPersonalDiscountByUserId(Long id);

  DataTableOutput<PersonalDiscountDTO> paginatePersonalDiscount(SortingPagination sortPaginate);

  List<PersonalDiscountDTO> getPersonalDiscountForValidate(LocalTime startTime,
      LocalTime endTime, Long userId, Long discountId);

}
