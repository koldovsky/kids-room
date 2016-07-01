package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;

/**
 * Created by Demian on 30.04.2016.
 */

@Repository
public class ChildDaoImpl extends BaseDaoImpl<Child> implements ChildDao {

    @Override
    public List<Child> getActiveChildrenInRoom(Room room) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Child> criteria = builder.createQuery( Child.class );

        Root<Booking> booking = criteria.from( Booking.class );
        Join<Booking, Child> children = booking.join("child");

        criteria.select(children).where(builder.and(
                builder.equal(booking.get("room"), room),
                builder.equal(booking.get("bookingState"), BookingState.ACTIVE)
        ));

        return entityManager.createQuery(criteria).getResultList();
    }

}
