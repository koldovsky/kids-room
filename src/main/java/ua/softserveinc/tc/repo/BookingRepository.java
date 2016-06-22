package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 09.06.2016.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Long countByRoomAndBookingState(Room room, BookingState bookingState);

    List<Booking> findByBookingState(BookingState bookingState);

    List<Booking> findByBookingEndTimeBetween(Date start, Date end);

    List<Booking> findByRoomAndBookingEndTimeBetween(Room room, Date start, Date end);

    List<Booking> findByUserAndBookingEndTimeBetween(User user, Date start, Date end);

    List<Booking> findByBookingStateAndBookingStartTimeLessThan(BookingState bookingState, Date start);

    List<Booking> findByRoomAndUserAndBookingEndTimeBetween(Room room, User user, Date start, Date end);

    List<Booking> findByRoomAndUserAndBookingState(Room room, User user, BookingState bookingState);
}
