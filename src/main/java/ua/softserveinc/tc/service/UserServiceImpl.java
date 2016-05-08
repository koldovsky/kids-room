package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
