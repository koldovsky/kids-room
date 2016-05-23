package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static ua.softserveinc.tc.constants.ColumnConstants.BookingConst.BOOKING_START_TIME;

/**
 * Created by TARAS on 01.05.2016.
 */
@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao {

    @Override
    public List<Booking> getBookingsByDay(Date startTime, Date endTime) throws ParseException {

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        try {
            query.select(root).where(builder.and(builder.between(root.get("bookingStartTime"),
                            startTime, endTime)), builder.equal(root.get("isCancelled"), false));
            query.orderBy(builder.asc(root.get("bookingStartTime")));
        }catch (Exception e){
            /*
            //Ask Andriy що тут має бути????
             */
        }
        return entityManager.createQuery(query).getResultList();
    }
}
