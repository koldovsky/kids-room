package ua.softserveinc.tc.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.DayDiscountDao;
import ua.softserveinc.tc.entity.DayDiscount;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

@Repository
public class DayDiscountDaoImp extends BaseDaoImpl<DayDiscount> implements DayDiscountDao {

  @Log
  public static Logger log;

  @PersistenceContext
  protected EntityManager entityManager;

  private CriteriaBuilder builder;

  @Override
  public List<DayDiscount> getAllActiveDayDiscount(){
    CriteriaQuery<DayDiscount> query = builder.createQuery(DayDiscount.class);
    Root<DayDiscount> root = query.from(DayDiscount.class);
    query.select(root).where(builder.equal(root.get("active"), true));
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<DayDiscount> getDayDiscountForCurrentDays(LocalDate startDate, LocalDate endDate,
      LocalTime startTime, LocalTime endTime, Boolean state) {
    CriteriaQuery<DayDiscount> query = builder.createQuery(DayDiscount.class);
    Root<DayDiscount> root = query.from(DayDiscount.class);
    List<Predicate> restrictions = new ArrayList<>();

    restrictions.add(builder.not(builder.lessThan(root.get("endDate"), startDate)));
    restrictions.add(builder.not(builder.greaterThan(root.get("startDate"), endDate)));
    restrictions.add(builder.not(builder.lessThan(root.get("endTime"), startTime)));
    restrictions.add(builder.not(builder.greaterThan(root.get("startTime"), endTime)));

    if (state) {
      restrictions.add(builder.equal(root.get("active"), state));
    }
    query.select(root).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));

    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<DayDiscount> getDayDiscountForValidate(LocalDate startDate, LocalDate endDate,
      LocalTime startTime, LocalTime endTime, Long id) {
    CriteriaQuery<DayDiscount> query = builder.createQuery(DayDiscount.class);
    Root<DayDiscount> root = query.from(DayDiscount.class);
    List<Predicate> restrictions = new ArrayList<>();
    restrictions.add(builder.not(builder.lessThan(root.get("endDate"), startDate)));
    restrictions.add(builder.not(builder.greaterThan(root.get("startDate"), endDate)));
    restrictions.add(builder.not(builder.lessThan(root.get("endTime"), startTime)));
    restrictions.add(builder.not(builder.greaterThan(root.get("startTime"), endTime)));
    if (id != null) {
      restrictions.add(builder.not(builder.equal(root.get("id"), id)));
    }
    query.select(root).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));

    return entityManager.createQuery(query).getResultList();
  }

  @Override
  @Transactional
  public void changeDayDiscountState(Long id, Boolean state) {
    CriteriaUpdate<DayDiscount> query = builder.createCriteriaUpdate(DayDiscount.class);
    Root<DayDiscount> root = query.from(DayDiscount.class);
    query.set("active", state);
    query.where(builder.equal(root.get("id"), id));
    entityManager.createQuery(query).executeUpdate();
  }

  @PostConstruct
  private void createDefaultState() {
    builder = entityManager.getCriteriaBuilder();
  }
}
