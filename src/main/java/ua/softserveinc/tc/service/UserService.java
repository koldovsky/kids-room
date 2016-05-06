package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.User;

public interface UserService extends BaseService<User> {
User getUserByEmail(String email);
}
