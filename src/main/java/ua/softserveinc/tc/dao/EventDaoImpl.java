package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.entity.Event;

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
 * Created by Nestor on 30.04.2016.
 */

@Repository("eventDao")
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao{

    public List<Event> getAllEventsByDay(Date searchDate){
        EntityManager entityManager = getEntityManager();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(searchDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startTime = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endTime = calendar.getTime();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        ParameterExpression<Date> dateParam = builder.parameter(Date.class);
        builder.between(dateParam, root.<Date>get("startTime"), root.<Date>get("endTime"));
        return entityManager
                .createQuery(query)
                .setParameter(dateParam, searchDate, TemporalType.DATE)
                .getResultList();
    }

    public void deleteOutdated(){
        getEntityManager().createQuery("DELETE FROM events WHERE endTime < (NOW() - INTERVAL 1 DAY)").executeUpdate();
    }
}