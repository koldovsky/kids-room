package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<User> {

    List<User> findAll(List<Long> ids);

    void deleteUserById(Long id);

    User getUserByEmail(String email);

    void createWithEncoder(User user);

    List<User> findAllUsersByRole(Role role);

    void confirmManagerRegistrationUpdate(User manager);

    List<User> getActiveUsers(Date startDate, Date endDate, Room room);
}
