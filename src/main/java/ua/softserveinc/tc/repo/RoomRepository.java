package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.List;

/**
 * Created by Nestor on 11.06.2016.
 */

public interface RoomRepository extends JpaRepository<Room, Long>{

    Room findByName(String name);

    List<Room> findByCity(String city);
    

}
