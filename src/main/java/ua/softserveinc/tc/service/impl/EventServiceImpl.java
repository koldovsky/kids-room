package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.EventService;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 01.05.2016.
 */

@Transactional
@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService {
    @Autowired
    private EventDao eventDao;

    public List<Event> getAllEventsByDay(Date searchDate){
        EntityManager entityManager = eventDao.getEntityManager();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(searchDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        ParameterExpression<Date> dateParam = builder.parameter(Date.class);
        builder.between(dateParam, root.<Date>get(EventConstants.Entity.START_TIME), root.<Date>get(EventConstants.Entity.END_TIME));
        return entityManager
                .createQuery(query)
                .setParameter(dateParam, searchDate, TemporalType.DATE)
                .getResultList();
    }
    }
