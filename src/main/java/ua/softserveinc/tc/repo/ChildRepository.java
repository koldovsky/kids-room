package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByParentId(User parent);

}
