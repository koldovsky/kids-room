package ua.softserveinc.tc.dao.impl;

import java.time.LocalTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.dao.PersonalDiscountDao;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.Log;

@Repository
public class PersonalDiscountDaoImp extends BaseDaoImpl<PersonalDiscount> implements
    PersonalDiscountDao {

  @Log
  public static Logger log;

  @PersistenceContext
  protected EntityManager entityManager;

  private CriteriaBuilder builder;

  @Override
  public List<PersonalDiscount> getPersonalDiscountByUser(long id) {
    CriteriaQuery<PersonalDiscount> query = builder.createQuery(PersonalDiscount.class);
    Root<User> root = query.from(User.class);
    Join<User, PersonalDiscount> discounts = root.join("personalDiscounts");
    query.select(discounts).where(builder.equal(root.get("id"), id));
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<PersonalDiscount> getPersonalDiscountByPeriod(LocalTime startTime,
      LocalTime endTime, Long id) {
    CriteriaQuery<PersonalDiscount> query = builder.createQuery(PersonalDiscount.class);
    Root<PersonalDiscount> root = query.from(PersonalDiscount.class);
    query.select(root).where(
        builder.not(builder.lessThan(root.get("endTime"), startTime)),
        builder.not(builder.greaterThan(root.get("startTime"), endTime)),
        builder.equal(root.get("id"), id)
    );
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  @Transactional
  public void changePersonalDiscountState(Long id, Boolean state) {
    CriteriaUpdate<PersonalDiscount> query = builder.createCriteriaUpdate(PersonalDiscount.class);
    Root<PersonalDiscount> root = query.from(PersonalDiscount.class);
    query.set("active", state);
    query.where(builder.equal(root.get("id"), id));
    entityManager.createQuery(query).executeUpdate();
  }

  @PostConstruct
  private void createDefaultState() {
    builder = entityManager.getCriteriaBuilder();
  }
}
