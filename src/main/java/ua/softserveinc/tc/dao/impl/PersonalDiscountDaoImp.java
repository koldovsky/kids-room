package ua.softserveinc.tc.dao.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
  public List<PersonalDiscount> getPersonalDiscountForValidate(LocalTime startTime,
      LocalTime endTime, Long userId, Long discountId) {
    CriteriaQuery<PersonalDiscount> query = builder.createQuery(PersonalDiscount.class);
    Root<User> rootUser = query.from(User.class);
    Join<User, PersonalDiscount> discounts = rootUser.join("personalDiscounts");

    List<Predicate> restrictions = new ArrayList<>();
    restrictions.add(builder.equal(discounts.getParent().get("id"), userId));
    restrictions.add(builder.not(builder.lessThan(discounts.get("endTime"), startTime)));
    restrictions.add(builder.not(builder.greaterThan(discounts.get("startTime"), endTime)));
    if (discountId != null) {
      restrictions.add(builder.not(builder.equal(discounts.get("id"), discountId)));
    }
    query.select(discounts).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));

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
