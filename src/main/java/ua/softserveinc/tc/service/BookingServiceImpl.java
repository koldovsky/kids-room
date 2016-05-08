package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by TARAS on 01.05.2016.
 */

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService
{
    @Autowired
    private BookingDao bookingDao;

    @Override
    public List<Booking> getBookingsByUser(User user)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        List<Booking> bookings = (List<Booking>) entityManager.createQuery(
                "from Booking where idUser = " + user.getId())
                .getResultList();
        return bookings;
    }
}
