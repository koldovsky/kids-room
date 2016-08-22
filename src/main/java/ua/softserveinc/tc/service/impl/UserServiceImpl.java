package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getActiveUsers(Date startDate, Date endDate, Room room) {
        return userDao.findActiveUsers(startDate, endDate, room);
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userDao.findAllUsersByRole(role);
    }

    @Override
    public List<User> findAll(List<Long> ids) {
        return userDao.findAll(ids);
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public User getUserByEmail(String email)  {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void createWithEncoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public List<Room> getActiveRooms(User user) {
        return  user.getRooms().stream()
                .filter(Room::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public List<Child> getEnabledChildren(User user) {
        return user.getChildren().stream()
                .filter(Child::isEnabled)
                .collect(Collectors.toList());
    }
}
