package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Nestor on 30.04.2016.
 */

@Repository(EventConstants.Entity.REPOSITORY)

public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    @PersistenceContext
    EntityManager entityManager;

    public Long getMaxRecurrentId() {

        if ((Long) entityManager
                .createQuery("select max(event.recurrentId) from Event event where event.recurrentId is not NULL")
                .getSingleResult() == null) return new Long(0);
        else {

            return (Long) entityManager
                    .createQuery("select max(event.recurrentId) from Event event where event.recurrentId is not NULL")
                    .getSingleResult();
        }
    }
}