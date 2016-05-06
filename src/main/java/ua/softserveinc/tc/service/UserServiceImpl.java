package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.User;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {



    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
//        User u = new User();
//        u.setEmail(email);
//        u.setPassword("1234");
//        u.setRole(Role.USER);
//        return u;

        return userDao.getUserByEmail(email);
    }
}
