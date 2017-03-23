package ua.softserveinc.tc.service.impl;

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
import ua.softserveinc.tc.service.DayDiscountService;

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
    return qResult.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public DayDiscountDTO findDayDiscountById(long id) {
    return convertToDto(dayDiscountDao.getDayDiscountById(id));
  }

  @Override
  public List<DayDiscountDTO> getDayDiscountsForPeriod(Date startDate, Date endDate) {
    List<DayDiscount> qResult = dayDiscountDao.findDayDiscountsForCurrentPeriod(startDate,endDate);
    return qResult.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  /**
   * This method adds new discount and evicts cache with current discounts
   *
   * @param dto DayDiscount from the client DayDiscount id must be null
   */
  @Override
  @CacheEvict(value = "fullDayDiscountList", allEntries = true)
  public void addNewDayDiscount(DayDiscountDTO dto) {
    dayDiscountDao.create(convertToEntity(dto));
  }

  /**
   * This method update discount by id and evicts cache with current discounts
   *
   * @param dto DayDiscount from the client DayDiscount id must not be null
   */
  @Override
  @CacheEvict(value = "fullDayDiscountList", allEntries = true)
  public void updateDayDiscountById(DayDiscountDTO dto) {
    dayDiscountDao.updateDayDiscountById(convertToEntity(dto));
  }

  /**
   * This method converts Entity to the DTO
   *
   * @param dayDiscount Entity DayDiscount
   * @return DayDiscountDTO
   */
  private DayDiscountDTO convertToDto(DayDiscount dayDiscount) {
    DayDiscountDTO dto = new DayDiscountDTO();
    dto.setId(dayDiscount.getId());
    dto.setReason(dayDiscount.getReason());
    dto.setValue(dayDiscount.getValue());
    dto.setStartDate(dayDiscount.getStartDate());
    dto.setEndDate(dayDiscount.getEndDate());
    dto.setActive(dayDiscount.getActive());
    return dto;
  }

  /**
   * This method converts DTO to the Entity
   *
   * @param discountDTO DTO DayDiscountDTO
   * @return DayDiscount
   */
  private DayDiscount convertToEntity(DayDiscountDTO discountDTO) {
    DayDiscount dayDiscount = new DayDiscount();
    if (discountDTO.getId() != null) {
      dayDiscount.setId(discountDTO.getId());
    }
    dayDiscount.setReason(discountDTO.getReason());
    dayDiscount.setValue(discountDTO.getValue());
    dayDiscount.setStartDate(discountDTO.getStartDate());
    dayDiscount.setEndDate(discountDTO.getEndDate());
    dayDiscount.setActive(discountDTO.getActive());
    return dayDiscount;
  }

}
