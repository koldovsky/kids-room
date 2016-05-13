package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllManagers() {
        return userDao.findAll().stream().filter(user -> Role.MANAGER == user.getRole())
                .collect(Collectors.toList());
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
