package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 09.06.2016.
 * Auto Jpa repository for handling User-related logic
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

    List<User> findByRole(Role role);

    @Query("SELECT DISTINCT b.user FROM Booking b " +
            "WHERE b.room = ?1 AND b.bookingEndTime > ?2 " +
            "AND b.bookingEndTime < ?3 AND b.bookingState = 2")
    List<User> getActiveUsers(Room room, Date lo, Date hi);

    @Query("UPDATE User u SET u.password = ?2 where u = ?1")
    @Modifying
    @Transactional
    void updateManagerPassword(User manager, String password);
}
