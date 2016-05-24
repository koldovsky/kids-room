package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import java.util.List;

public interface UserService extends BaseService<User>
{
    List<User> getAllParents();
    void deleteUserById(Long id);
    User getUserByEmail(String email);
    List<User> findAllUsersByRole(Role role);
    void confirmManagerRegistrationUpdate(User manager);
    List<User> getActiveUsersForRangeOfTime(String startDate, String endDate);
}
