package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

}
