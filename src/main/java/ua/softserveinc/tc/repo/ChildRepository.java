package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;

import java.util.List;

/**
 * Created by Nestor on 11.06.2016.
 */
public interface ChildRepository extends JpaRepository<Child, Long>{

    List<Child> findByParentId(User parent);

}
