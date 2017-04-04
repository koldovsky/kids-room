package ua.softserveinc.tc.service;

import org.springframework.validation.BindingResult;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.*;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<User> {

    List<User> findAll(List<Long> ids);

    void deleteUserById(Long id);

    User getUserByEmail(String email);

    User getUserByName(String firstName, String lastName);

    void createWithEncoder(User user);

    List<User> findAllUsersByRole(Role role);

    List<UserDto> findUsersByRoleDto(Role role);

    List<User> getActiveUsers(Date startDate, Date endDate, Room room);

    List<Room> getActiveRooms(User user);

    List<Child> getEnabledChildren(User user);

    List<User> findByActiveTrueAndRoleNot(Role role);

    User findUserId(Long id);

    UserDto findUserByIdDto(Long id);

    ReturnModel adminUpdateManager(User manager, BindingResult bindingResult);

    ReturnModel adminAddManager(User manager, BindingResult bindingResult);
}
