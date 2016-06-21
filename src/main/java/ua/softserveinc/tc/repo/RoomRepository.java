package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(String name);

    List<Room> findByCity(String city);


}
