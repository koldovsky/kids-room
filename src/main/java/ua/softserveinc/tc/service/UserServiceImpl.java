package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.DateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getActiveUsers(String startDate, String endDate)
    {
        EntityManager entityManager = bookingDao.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root.get("idUser")).distinct(true).where(
            builder.between(root.get("bookingStartTime"),
                dateUtil.toDate(startDate), dateUtil.toDate(endDate)),
            builder.equal(root.get("bookingState"), BookingState.COMPLETED));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userDao.findAllUsersByRole(role);
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public void confirmManagerRegistrationUpdate(User manager){
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        userDao.update(manager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllParents()
    {
        EntityManager entityManager = userDao.getEntityManager();
        List<User> list = (List<User>) entityManager
                .createQuery("from User" +
                        " where role = 0")
                .getResultList();
        return list;
    }

}
