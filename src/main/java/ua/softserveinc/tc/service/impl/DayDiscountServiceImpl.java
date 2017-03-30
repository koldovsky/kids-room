package ua.softserveinc.tc.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.DayDiscountDao;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.DayDiscount;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.service.DayDiscountService;
import ua.softserveinc.tc.util.PaginationCharacteristics;


@Service
public class DayDiscountServiceImpl extends BaseServiceImpl<DayDiscount> implements
    DayDiscountService {

  @Autowired
  private DayDiscountDao dayDiscountDao;

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
    long rowCount = dayDiscountDao.getRowsCount();
    long currentPage = PaginationCharacteristics.definePage(sortPaginate.getPagination().getStart(),
        sortPaginate.getPagination().getItemsPerPage(), rowCount);
    return  new DataTableOutput<>(currentPage, rowCount, rowCount, listDto);
  }

  /**
   * This method returns daily discount by current id
   *
   * @param id DayDiscounts id from the client
   * @return DayDiscount with actual id
   */
  @Override
  public DayDiscountDTO findDayDiscountById(long id) {
    return new DayDiscountDTO(dayDiscountDao.findById(id));
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
      LocalTime startTime, LocalTime endTime) {
    List<DayDiscount> qResult = dayDiscountDao
        .getDayDiscountForCurrentDays(startDate, endDate, startTime, endTime);
    return qResult.stream().map(DayDiscountDTO::new).collect(Collectors.toList());
  }


  /**
   * This method adds new discount and evicts cache with current discounts
   *
   * @param dto DayDiscount from the client DayDiscount id must be null
   */
  @Override
  @CacheEvict(value = "fullDayDiscountList", allEntries = true)
  public void addNewDayDiscount(DayDiscountDTO dto) {
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

}
