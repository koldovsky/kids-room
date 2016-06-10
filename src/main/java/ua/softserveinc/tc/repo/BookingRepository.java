package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 09.06.2016.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookingEndTimeBetween(Date start, Date end);

    List<Booking> findByIdRoomAndBookingEndTimeBetween(Room room, Date start, Date end);

    List<Booking> findByIdUserAndBookingEndTimeBetween(User user, Date start, Date end);

    List<Booking> findByIdRoomAndIdUserAndBookingEndTimeBetween(Room room, User user, Date start, Date end);

}
