package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.entity.Abonnement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AbonnementDaoImpl extends BaseDaoImpl<Abonnement> implements AbonnementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getRowsCount() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Abonnement.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void updateByActiveState(long id, boolean active) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Abonnement> update = builder.createCriteriaUpdate(Abonnement.class);
        Root root = update.from(Abonnement.class);
        update.set("isActive", active);
        update.where(builder.equal(root.get("id"), id));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public List<Abonnement> getAbonnementsFromToLength(int start, int length) {

        List<Abonnement> resultList;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Abonnement> criteria = builder.createQuery(Abonnement.class);
        Root<Abonnement> root = criteria.from(Abonnement.class);
        List<Predicate> restrictions = new ArrayList<>();
        restrictions.addAll(Arrays.asList(
                builder.greaterThan(root.get(AbonnementConstants.Hibernate.ABONNEMENT_ID),
                        start),
                builder.lessThanOrEqualTo(root.get(AbonnementConstants.Hibernate.ABONNEMENT_ID),
                        length + start)
                )
        );
        criteria.select(root).where(builder.and(
                restrictions.toArray(new Predicate[restrictions.size()])));
        resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
    }
}
