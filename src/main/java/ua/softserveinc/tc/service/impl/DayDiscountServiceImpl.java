package ua.softserveinc.tc.service.impl;


import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private ModelMapper modelMapper;

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

  /**
   * This method converts Entity to the DTO
   *
   * @param dayDiscount Entity DayDiscount
   * @return DayDiscountDTO
   */
  private DayDiscountDTO convertToDto(DayDiscount dayDiscount) {
    return modelMapper.map(dayDiscount, DayDiscountDTO.class);
  }

}
