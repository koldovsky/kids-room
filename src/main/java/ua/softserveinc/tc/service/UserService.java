package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<User> {

    List<User> findAll(List<Long> ids);

    void deleteUserById(Long id);

    User getUserByEmail(String email);

    User getUserByName(String firstName, String lastName);

    void createWithEncoder(User user);

    List<User> findAllUsersByRole(Role role);

    List<User> getActiveUsers(Date startDate, Date endDate, Room room);

    List<Room> getActiveRooms(User user);

    List<Child> getEnabledChildren(User user);
}
