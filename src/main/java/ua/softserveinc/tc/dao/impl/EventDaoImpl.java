package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository(EventConstants.Entity.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    @PersistenceContext
    EntityManager entityManager;

    public Long getMaxRecurrentId() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Event> r = q.from(Event.class);
        Expression<Long> maxExpression = cb.max(r.get(EventConstants.EntityClass.ID_RECURRENT));

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
    @Transactional
    public Event createOrUpdateEvent(Event event){
        return entityManager.merge(event);
    }

    @Override
    public List<Event> findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        query.select(root).where(
                builder.equal( root.get(EventConstants.EntityClass.NAME), name)
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<String> getEmailForNotifyChangeEvent(Long event_id) {
        Query query = entityManager.createNativeQuery("select users.email from Users" +
                " inner join bookings on bookings.id_user = users.id_user" +
                " inner join events on events.room = bookings.id_room" +
                " where events.id_event = :eventId and (CAST(events.start_time AS DATE) = CAST(bookings.booking_start_time AS DATE))" +
                " and ((CAST(events.start_time AS TIME) between CAST(bookings.booking_start_time AS TIME) and CAST(bookings.booking_end_time AS TIME)) " +
                " or " +
                " (CAST(events.end_time AS TIME) between CAST(bookings.booking_start_time AS TIME) and CAST(bookings.booking_end_time AS TIME)))" +
                " group by users.id_user");

        query.setParameter("eventId", event_id);
        return query.getResultList();
    }

    @Override
    public Map<String, String> compareAndGetField(Event event, Event eventNew) {
        Map<String, String> resultMap = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DATE_AND_TIME_FORMAT);

        if(!eventNew.getName().equals(event.getName())){
            resultMap.put("name", eventNew.getName());
        }

        if(eventNew.getStartTime().compareTo(event.getStartTime()) != 0){
            resultMap.put("startTime", dateFormat.format(eventNew.getStartTime()));
        }

        if(eventNew.getEndTime().compareTo(event.getEndTime()) != 0){
            resultMap.put("endTime", dateFormat.format(eventNew.getEndTime()));
        }

        if(eventNew.getAgeLow() != event.getAgeLow()){
            resultMap.put("ageLow", String.valueOf(eventNew.getAgeLow()));
        }

        if(eventNew.getAgeHigh() != event.getAgeHigh()){
            resultMap.put("ageHigh", String.valueOf(eventNew.getAgeHigh()));
        }

        if(!eventNew.getRoom().getId().equals(event.getRoom().getId())){
            resultMap.put("room", String.valueOf(eventNew.getRoom().getName()));
        }

        if(!eventNew.getDescription().equals(event.getDescription())){
            resultMap.put("description", eventNew.getDescription());
        }

        return resultMap;
    }

    @Override
    @Transactional
    public void deleteByRecurrentId(Long idRecurrent) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Event> delete = cb.createCriteriaDelete(Event.class);
        Root r = delete.from(Event.class);
        delete.where(cb.equal(r.get(EventConstants.EntityClass.ID_RECURRENT), idRecurrent));
        entityManager.createQuery(delete).executeUpdate();
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
