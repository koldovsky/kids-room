package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.EntityConstants.BookingConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by TARAS on 01.05.2016.
 */
@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @Override
    public List<Booking> getBookingsByDay(Date startTime, Date endTime, Room room) {

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);
        System.out.println(new Date());

        try {
            query.select(root).where(builder.and(builder.between(root.get("bookingStartTime"),
                            startTime, endTime)), builder.notEqual(root.get("bookingState"), BookingConst.BOOKING_CENCELLD),
                            builder.equal(root.get("idRoom"), room));
            query.orderBy(builder.asc(root.get("bookingStartTime")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityManager.createQuery(query).getResultList();
    }
}
