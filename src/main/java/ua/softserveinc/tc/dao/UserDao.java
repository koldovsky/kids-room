package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    void deleteUserById(Long id);

    List<User> findAllUsersByRole(Role role) ;

    User getUserByEmail(String email);

    List<User> findAll(List<Long> ids);
}