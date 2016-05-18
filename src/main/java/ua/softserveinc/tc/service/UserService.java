package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface UserService extends BaseService<User> {

    void deleteUserById(Long id);

    List<User> findAllUsersByRole(Role role);

    User getUserByEmail(String email);

    List<User> getAllParents();

    void confirmManagerRegistrationUpdate(User manager);
}
