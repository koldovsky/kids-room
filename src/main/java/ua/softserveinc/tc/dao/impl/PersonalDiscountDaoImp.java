package ua.softserveinc.tc.dao.impl;

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
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.entity.PersonalDiscount;
import ua.softserveinc.tc.dao.PersonlaDiscountDao;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.Log;


public class PersonalDiscountDaoImp extends BaseDaoImpl<PersonalDiscount> implements
    PersonlaDiscountDao {

  @Log
  public static Logger log;

  @PersistenceContext
  protected EntityManager entityManager;

  private CriteriaBuilder builder;

  @Override
  public PersonalDiscount getPersonalDiscountById(long id) {
    CriteriaQuery<PersonalDiscount> query = builder.createQuery(PersonalDiscount.class);
    Root<PersonalDiscount> root = query.from(PersonalDiscount.class);
    query.select(root).where(
        builder.equal(root.get("id"), id));
    return entityManager.createQuery(query).getSingleResult();
  }

  @Override
  @Transactional
  public void updatePersonalDiscountById(PersonalDiscount newPersonalDiscount) {
    CriteriaUpdate<PersonalDiscount> update = builder.createCriteriaUpdate(PersonalDiscount.class);
    Root<PersonalDiscount> root = update.from(PersonalDiscount.class);
    update.set("reason", newPersonalDiscount.getReason());
    update.set("value", newPersonalDiscount.getValue());
    update.set("startTime", newPersonalDiscount.getStartTime());
    update.set("endTime", newPersonalDiscount.getEndTime());
    update.set("user_id", newPersonalDiscount.getUser().getId());
    update.where(builder.equal(root.get("id"), newPersonalDiscount.getId()));
    entityManager.createQuery(update).executeUpdate();
  }

  @Override
  @Transactional
  public void updatePersonalDiscountState(PersonalDiscount newPersonalDiscount) {
    CriteriaUpdate<PersonalDiscount> update = builder.createCriteriaUpdate(PersonalDiscount.class);
    Root<PersonalDiscount> root = update.from(PersonalDiscount.class);
    update.set("id_user", newPersonalDiscount.getUser().getId());
    update.where(builder.equal(root.get("id"), newPersonalDiscount.getId()));
    entityManager.createQuery(update).executeUpdate();
  }

  @Override
  public List<PersonalDiscount> getPersonalDiscountByUser(long id) {
    CriteriaQuery<PersonalDiscount> query = builder.createQuery(PersonalDiscount.class);
    Root<User> root = query.from(User.class);
    Join<User, PersonalDiscount> discounts = root.join("personalDiscounts");
    query.select(discounts).where(builder.equal(root.get("id"), id));
    return entityManager.createQuery(query).getResultList();
  }

  @PostConstruct
  private void createDefaultState() {
    builder = entityManager.getCriteriaBuilder();
  }
}
