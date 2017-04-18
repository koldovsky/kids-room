package ua.softserveinc.tc.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ua.softserveinc.tc.dao.DayDiscountDao;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.DayDiscount;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.server.exception.NoSuchRowException;
import ua.softserveinc.tc.server.exception.UserInputException;
import ua.softserveinc.tc.service.DayDiscountService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.util.PaginationCharacteristics;
import ua.softserveinc.tc.validator.DayDiscountValidate;


@Service
public class DayDiscountServiceImpl extends BaseServiceImpl<DayDiscount> implements
    DayDiscountService {

  @Autowired
  private DayDiscountDao dayDiscountDao;

  @Autowired
  private DayDiscountValidate dayDiscountValidate;

  @Autowired
  private MessageSource messageSource;

  @Log
  private static Logger log;

  /**
   * This method returns all daily discounts
   *
   * @return List<DayDiscountDTO> with relative data
   */
  @Cacheable("fullDayDiscountList")
  public List<DayDiscountDTO> findAllDailyDiscounts() {
    List<DayDiscount> qResult = dayDiscountDao.findAll();
    return qResult.stream().map(DayDiscountDTO::new).collect(Collectors.toList());
  }

  @Override
  public DataTableOutput<DayDiscountDTO> paginateDayDiscount(SortingPagination sortPaginate) {
    List<DayDiscountDTO> listDto = dayDiscountDao.findAll(sortPaginate).stream()
        .map(DayDiscountDTO::new).collect(Collectors.toList());
    long rowCount = dayDiscountDao.getRowsCount(),
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

  /**
   * This method returns daily discount by current id
   *
   * @param id DayDiscounts id from the client
   * @return DayDiscount with actual id
   */
  @Override
  public DayDiscountDTO findDayDiscountById(long id) {
    DayDiscount dayDiscount = dayDiscountDao.findById(id);
    if (dayDiscount == null) {
      log.error("While getting day discount with id " + id + " - No such row exception");
      throw new NoSuchRowException("There is no day discounts with this id");
    }
    return new DayDiscountDTO(dayDiscount);
  }

  /**
   * This method returns list of daily discount for specific period
   *
   * @param startDate start date of search
   * @param endDate end date of search
   * @return List of DayDiscounts for specific period
   */
  @Override
  public List<DayDiscountDTO> getDayDiscountsForPeriod(LocalDate startDate, LocalDate endDate,
      LocalTime startTime, LocalTime endTime, Boolean state) {
    List<DayDiscount> qResult = dayDiscountDao
        .getDayDiscountForCurrentDays(startDate, endDate, startTime, endTime, state);
    return qResult.stream().map(DayDiscountDTO::new).collect(Collectors.toList());
  }


  /**
   * This method adds new discount and evicts cache with current discounts
   *
   * @param dto DayDiscount from the client DayDiscount id must be null
   */
  @Override
  @CacheEvict(value = "fullDayDiscountList", allEntries = true)
  public void addNewDayDiscount(DayDiscountDTO dto, BindingResult bindingResult) {
    dayDiscountValidate.validate(dto, bindingResult);
    List<String> listError = new ArrayList<String>();
    if (bindingResult.hasErrors()) {
      for (Object object : bindingResult.getAllErrors()) {
        FieldError er = (FieldError) object;
        listError
            .add(messageSource.getMessage(er.getCode(), null, LocaleContextHolder.getLocale()));
      }
      throw new UserInputException(listError);
    }
    //If user have entered correct data
    dayDiscountDao.create(new DayDiscount(dto));
  }

  /**
   * This method update discount by id and evicts cache with current discounts
   *
   * @param dto DayDiscount from the client DayDiscount id must not be null
   */
  @Override
  @CacheEvict(value = "fullDayDiscountList", allEntries = true)
  public void updateDayDiscountById(DayDiscountDTO dto) {
    dayDiscountDao.update(new DayDiscount(dto));
  }

  @Override
  public void changeDayDiscountState(DayDiscountDTO dto) {
    dayDiscountDao.changeDayDiscountState(dto.getId(), dto.getActive());
  }
}
