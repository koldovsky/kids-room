package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Long countByRoomAndBookingState(Room room, BookingState bookingState);

    List<Booking> findByBookingState(BookingState bookingState);

    List<Booking> findByBookingStateAndBookingStartTimeLessThan(BookingState bookingState, Date start);

}
