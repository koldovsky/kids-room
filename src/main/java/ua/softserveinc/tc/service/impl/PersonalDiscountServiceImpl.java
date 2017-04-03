package ua.softserveinc.tc.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.PersonalDiscountDao;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.service.PersonalDiscountService;
import ua.softserveinc.tc.util.PaginationCharacteristics;

@Service
public class PersonalDiscountServiceImpl extends BaseServiceImpl<PersonalDiscount> implements
    PersonalDiscountService {

  @Autowired
  private PersonalDiscountDao personalDiscount;

  @Override
  public List<PersonalDiscountDTO> findAllPersonalDiscounts() {
    List<PersonalDiscount> qResult = personalDiscount.findAll();
    return qResult.stream().map(PersonalDiscountDTO::new).collect(Collectors.toList());
  }

  @Override
  public DataTableOutput<PersonalDiscountDTO> paginateDayDiscount(SortingPagination sortPaginate) {
    List<PersonalDiscountDTO> listDto = personalDiscount.findAll(sortPaginate).stream()
        .map(PersonalDiscountDTO::new).collect(Collectors.toList());
    long rowCount = personalDiscount.getRowsCount();
    long currentPage = PaginationCharacteristics.definePage(sortPaginate.getPagination().getStart(),
        sortPaginate.getPagination().getItemsPerPage(), rowCount);
    return  new DataTableOutput<>(currentPage, rowCount, rowCount, listDto);
  }

  @Override
  public PersonalDiscountDTO findPersonalDiscountById(Long id) {
    return new PersonalDiscountDTO(personalDiscount.findById(id));
  }

  @Override
  public List<PersonalDiscountDTO> findPersonalDiscountByUserId(Long id) {
    List<PersonalDiscount> qResult = personalDiscount.getPersonalDiscountByUser(id);
    return qResult.stream().map(PersonalDiscountDTO::new).collect(Collectors.toList());
  }

  @Override
  public void addNewPersonalDiscount(PersonalDiscountDTO personalDiscountDTO) {
    personalDiscount.create(new PersonalDiscount(personalDiscountDTO));
  }

  @Override
  public void updatePersonalDiscountById(PersonalDiscountDTO personalDiscountDTO) {
    personalDiscount.update(new PersonalDiscount(personalDiscountDTO));
  }

}
