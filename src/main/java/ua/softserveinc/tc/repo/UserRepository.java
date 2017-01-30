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
 * Auto Jpa repository for handling User-related logic
 */
public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByActiveTrueAndRoleNot(Role role);
    
}
