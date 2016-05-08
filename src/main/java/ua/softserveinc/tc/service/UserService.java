package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface UserService extends BaseService<User>
{
    User getUserByEmail(String email);
    List<User> getAllParents();
}
