package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
