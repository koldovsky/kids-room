package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.repo.UserRepository;
import ua.softserveinc.tc.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getActiveUsers(Date startDate, Date endDate, Room room) {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root.get(BookingConstants.Entity.USER)).distinct(true).where(
                builder.between(root.get(
                        BookingConstants.Entity.START_TIME), startDate, endDate),
                builder.equal(root.get(BookingConstants.Entity.STATE), BookingState.COMPLETED),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        return entityManager.createQuery(query).getResultList();
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
    public void confirmManagerRegistrationUpdate(User manager) {
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        userDao.update(manager);
    }


}
