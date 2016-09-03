package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.DayOff;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {

}
