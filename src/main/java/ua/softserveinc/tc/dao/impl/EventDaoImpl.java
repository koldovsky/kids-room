package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository(EventConstants.Entity.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    @PersistenceContext
    EntityManager entityManager;

    public Long getMaxRecurrentId() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Event> r = q.from(Event.class);
        Expression<Long> maxExpression = cb.max(r.get("recurrentId"));

        CriteriaQuery<Long> select = q.select(maxExpression);

        TypedQuery<Long> typedQuery = entityManager.createQuery(select);

        Long result = typedQuery.getSingleResult();
        if (result == null) {
            return 0L;
        }
        return result;
    }

    @Transactional
    public void saveSetOfEvents(List<Event> listToSave) {
        for (Event event: listToSave) {
            entityManager.persist(event);
        }
    }

    @Override
    public void deleteByRecurrentId(Long idRecurrent) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Event> delete = cb.createCriteriaDelete(Event.class);
        Root<Event> r = delete.from(Event.class);
        ParameterExpression<Long> p = cb.parameter(Long.class);
        delete.where(cb.equal(r.get("recurrentId"),idRecurrent));
    }

    public List<Event> getRecurrentEventByRecurrentId(Long recurrentId) {
        CriteriaQuery<Event> query;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.select(root).where(builder.equal(root.get(
                EventConstants.EntityClass.ID_RECURRENT), recurrentId)).
                orderBy(builder.asc(root.get(
                        EventConstants.EntityClass.START_TIME)));
        return entityManager.createQuery(query).getResultList();
    }
}
