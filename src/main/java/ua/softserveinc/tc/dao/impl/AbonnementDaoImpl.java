package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
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
    public void updateByActiveState(long id, boolean active) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Abonnement> update = builder.createCriteriaUpdate(Abonnement.class);
        Root root = update.from(Abonnement.class);
        update.set("isActive", active);
        update.where(builder.equal(root.get("id"), id));
        entityManager.createQuery(update).executeUpdate();
    }
}
