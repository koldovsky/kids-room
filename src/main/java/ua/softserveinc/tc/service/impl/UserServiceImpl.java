package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.server.exception.NoSuchRowException;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User>
        implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MailService mailService;

    @Log
    private static Logger log;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private ResponseWithErrors responseWithErrors;

    @Override
    public List<User> getActiveUsers(Date startDate, Date endDate, Room room) {
        return userDao.findActiveUsers(startDate, endDate, room);
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userDao.findAllUsersByRole(role);
    }

    public List<UserDto> findUsersByRoleDto(Role role) {
        return userDao.findAllUsersByRole(role).stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(List<Long> ids) {
        return userDao.findAll(ids);
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
    public User getUserByName(String firstName, String lastName) {
        return userDao.getUserByName(firstName, lastName);
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void createWithEncoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public List<Room> getActiveRooms(User user) {
        return user.getRooms().stream()
                .filter(Room::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByIdDto(Long id) {
        User user = userDao.findUserById(id);
        if (Objects.isNull(user)) {
            log.error("While getting user with id " + id + " - No such row exception");
            throw new NoSuchRowException("No user this this id");
        }
        return new UserDto(user);
    }

    @Override
    public List<Child> getEnabledChildren(User user) {
        return user.getChildren().stream()
                .filter(Child::isEnabled)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByActiveTrueAndRoleNot(Role role) {
        return userDao.findByActiveTrueAndRoleNot(role);
    }

    @Override
    public User findUserId(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public ResponseWithErrors adminUpdateManager(User manager, BindingResult bindingResult) {
        return manager != null ? updateManager(manager, bindingResult) : getResponseOfEmptyUser();
    }

    @Override
    public ResponseWithErrors adminAddManager(User manager, BindingResult bindingResult) {
        responseWithErrors = new ResponseWithErrors();
        userValidator.validateManager(manager, bindingResult);
        checkEmailIfExist(manager, bindingResult, responseWithErrors);
        if (checkInputErrors(bindingResult, responseWithErrors)) return responseWithErrors;
        manager.setRole(Role.MANAGER);
        manager.setActive(true);
        String token = UUID.randomUUID().toString();
        if (sendEmail(manager, bindingResult, token)) return responseWithErrors;
        create(manager);
        tokenService.createToken(token, manager);
        responseWithErrors.setEmail(manager.getEmail());
        return responseWithErrors;
    }

    private boolean checkInputErrors(BindingResult bindingResult, ResponseWithErrors responseWithErrors) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError() != null) {
                responseWithErrors.setMessage(responseWithErrors.getMessage() + " / " + bindingResult.getFieldError().getField() + " incorrect");
            }
            return true;
        }
        return false;
    }

    private boolean sendEmail(User manager, BindingResult bindingResult, String token) {
        try {
            mailService.buildConfirmRegisterManager("Confirmation registration", manager, token);
        } catch (MessagingException | MailSendException ex) {
            log.error(ValidationConstants.PROBLEM_SEND_EMAIL_MSG, ex);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            responseWithErrors.setEmail(manager.getEmail());
            responseWithErrors.setMessage(ValidationConstants.NOT_SEND_EMAIL_MSG);
            return true;
        }
        return false;
    }

    private void checkEmailIfExist(User manager, BindingResult bindingResult, ResponseWithErrors responseWithErrors) {
        if (getUserByEmail(manager.getEmail()) != null) {
            bindingResult.addError(new ObjectError("Email", "Exist"));
            responseWithErrors.setMessage("User with such email already exist");
        }
    }

    private ResponseWithErrors getResponseOfEmptyUser() {
        responseWithErrors = new ResponseWithErrors("");
        responseWithErrors.setMessage("Empty manager");
        log.error("While getting manager with id  - No such user");
        return responseWithErrors;
    }

    private ResponseWithErrors updateManager(User manager, BindingResult bindingResult) {
        responseWithErrors = new ResponseWithErrors(manager.getEmail());
        userValidator.validateManager(manager, bindingResult);
        if (checkErrors(bindingResult, responseWithErrors)) return responseWithErrors;
        if (checkEmail(manager, responseWithErrors)) return responseWithErrors;
        update(checkFields(manager));
        return responseWithErrors;
    }

    private boolean checkErrors(BindingResult bindingResult, ResponseWithErrors responseWithErrors) {
        return bindingResult.hasErrors() ? responseWithErrors.setMessage(bindingResult.getFieldError().getField() + " incorrect") : false;
    }

    private boolean checkEmail(User manager, ResponseWithErrors responseWithErrors) {
        return (!findUserId(manager.getId()).getEmail().equalsIgnoreCase(manager.getEmail()) && getUserByEmail(manager.getEmail()) != null) ? responseWithErrors.setMessage(ValidationConstants.EMAIL_ALREADY_IN_USE_MSG) : false;
    }

    private User checkFields(User manager) {
        User managerFromDB = findUserId(manager.getId());
        if (!managerFromDB.getEmail().equalsIgnoreCase(manager.getEmail())) {
            managerFromDB.setEmail(manager.getEmail());
        }
        if (!managerFromDB.getFirstName().equalsIgnoreCase(manager.getFirstName())) {
            managerFromDB.setFirstName(manager.getFirstName());
        }
        if (!managerFromDB.getLastName().equalsIgnoreCase(manager.getLastName())) {
            managerFromDB.setLastName(manager.getLastName());
        }
        if (!managerFromDB.getPhoneNumber().equalsIgnoreCase(manager.getPhoneNumber())) {
            managerFromDB.setPhoneNumber(manager.getPhoneNumber());
        }
        return managerFromDB;
    }
}
