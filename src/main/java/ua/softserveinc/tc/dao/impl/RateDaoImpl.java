package ua.softserveinc.tc.dao.impl;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RateDaoImpl extends BaseDaoImpl<Rate> implements RateDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Rate rate) {
        entityManager.persist(rate);
    }
}
