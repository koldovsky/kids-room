package ua.softserveinc.tc.service.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.softserveinc.tc.dao.PersonalDiscountDao;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.server.exception.NoSuchRowException;
import ua.softserveinc.tc.service.PersonalDiscountService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.util.PaginationCharacteristics;
import ua.softserveinc.tc.validator.PersonalDiscountValidate;

import static ua.softserveinc.tc.validator.util.ShowUserInputErrors.validateDTO;

@Service
public class PersonalDiscountServiceImpl extends BaseServiceImpl<PersonalDiscount> implements
    PersonalDiscountService {

  @Autowired
  private PersonalDiscountDao personalDiscount;

  @Autowired
  private PersonalDiscountValidate personalDiscountValidate;

  @Autowired
  private MessageSource messageSource;

  @Log
  private static Logger log;

  @Override
  public List<PersonalDiscountDTO> findAllPersonalDiscounts() {
    List<PersonalDiscount> qResult = personalDiscount.findAll();
    return qResult.stream().map(PersonalDiscountDTO::new).collect(Collectors.toList());
  }

  @Override
  public DataTableOutput<PersonalDiscountDTO> paginatePersonalDiscount(
      SortingPagination sortPaginate) {
    List<PersonalDiscountDTO> listDto = personalDiscount.findAll(sortPaginate).stream()
        .map(PersonalDiscountDTO::new).collect(Collectors.toList());
    long rowCount = personalDiscount.getRowsCount(),
        start = sortPaginate.getPagination().getStart(),
        itemsPerPage = sortPaginate.getPagination().getItemsPerPage();
    long currentPage = PaginationCharacteristics.definePage(start, itemsPerPage, rowCount);

    if (PaginationCharacteristics.searchCount == 0) {
      return new DataTableOutput<>(currentPage, rowCount, rowCount, listDto);
    } else {
      return new DataTableOutput<>(currentPage, rowCount, PaginationCharacteristics.searchCount,
          listDto);
    }
  }

  @Override
  public PersonalDiscountDTO findPersonalDiscountById(Long id) {
    PersonalDiscount dto = personalDiscount.findById(id);
    if (Objects.isNull(dto)) {
      log.error("While getting personal discount with id " + id + " - No such row exception");
      throw new NoSuchRowException("There is no personal discounts with this id");
    }
    return new PersonalDiscountDTO(dto);
  }

  @Override
  public List<PersonalDiscountDTO> findPersonalDiscountByUserId(Long id) {
    List<PersonalDiscount> qResult = personalDiscount.getPersonalDiscountByUser(id);
    return qResult.stream().map(PersonalDiscountDTO::new).collect(Collectors.toList());
  }

  @Override
  public void addNewPersonalDiscount(PersonalDiscountDTO personalDiscountDTO,
      BindingResult bindingResult) {
    validateDTO(personalDiscountDTO, bindingResult, messageSource, personalDiscountValidate);
    personalDiscount.create(new PersonalDiscount(personalDiscountDTO));
  }

  @Override
  public void updatePersonalDiscountById(PersonalDiscountDTO personalDiscountDTO,
      BindingResult bindingResult) {
    validateDTO(personalDiscountDTO, bindingResult, messageSource, personalDiscountValidate);
    personalDiscount.update(new PersonalDiscount(personalDiscountDTO));
  }

  @Override
  public void changePersonalDiscountState(PersonalDiscountDTO dto) {
    if (dto.getId() == null || dto.getActive() == null) {
      log.error("Something wrong with DTO -  id or state is null");
      throw new NoSuchRowException("Wrong data format");
    }
    personalDiscount.changePersonalDiscountState(dto.getId(), dto.getActive());
  }

  @Override
  public List<PersonalDiscountDTO> getPersonalDiscountForValidate(LocalTime startTime,
      LocalTime endTime, Long userId, Long discountId) {
    List<PersonalDiscount> qResult = personalDiscount
        .getPersonalDiscountForValidate(startTime, endTime, userId, discountId);
    return qResult.stream().map(PersonalDiscountDTO::new).collect(Collectors.toList());
  }

}
