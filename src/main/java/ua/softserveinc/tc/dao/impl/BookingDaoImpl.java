package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by TARAS on 01.05.2016.
 */
@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    public List<Booking> getBookingsByUserAndRoom(User user, Room room) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);

        Root<Booking> root = query.from(Booking.class);
        query.select(root).where(builder.equal(root.get(BookingConstants.Entity.USER), user),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        return entityManager.createQuery(query).getResultList();
    }

    public Long getMaxRecurrentId() {

        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Booking> root = criteriaQuery.from(Booking.class);
        CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.max(root.get(BookingConstants.DB.ID_RECURRENT)));

        TypedQuery<Long> typedQuery = entityManager.createQuery(select);

        Long result = typedQuery.getSingleResult();
        if (result == null) {
            return new Long(0);
        } else {
            return result;
        }
    }
}