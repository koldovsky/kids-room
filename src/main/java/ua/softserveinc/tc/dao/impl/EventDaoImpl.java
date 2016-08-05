package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

@Repository(EventConstants.Entity.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    @PersistenceContext
    EntityManager entityManager;

    public Long getMaxRecurrentId() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Event> r = q.from(Event.class);
        Expression maxExpression = cb.max(r.get("recurrentId"));

        CriteriaQuery<Long> select = q.select(maxExpression);

        TypedQuery<Long> typedQuery = entityManager.createQuery(select);

        Long result = typedQuery.getSingleResult();
        if (result == null) {
            return 0L;
        }
        return result;
    }
}
