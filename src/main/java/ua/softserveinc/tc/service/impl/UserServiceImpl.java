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
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
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

    @Override
    public List<User> getActiveUsers(Date startDate, Date endDate, Room room) {
        return userDao.findActiveUsers(startDate, endDate, room);
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userDao.findAllUsersByRole(role);
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
    public ReturnModel adminUpdateManager(User manager, BindingResult bindingResult) {
        ReturnModel returnModel = new ReturnModel(manager.getEmail());
        userValidator.validateManager(manager, bindingResult);
        if (bindingResult.hasErrors()) {
            returnModel.setMessage("Error with user");
            return returnModel;
        }

            if ((!findUserId(manager.getId()).getEmail().equalsIgnoreCase(manager.getEmail())) && getUserByEmail(manager.getEmail()) != null) {
                returnModel.setMessage("This Email already exist");
                return returnModel;
            }

        User managerFromDB = checkFields(manager);
        update(managerFromDB);
        return returnModel;
    }

    @Override
    public ReturnModel adminAddManager(User manager, BindingResult bindingResult) {
        ReturnModel returnModel = new ReturnModel();
        returnModel.setEmail(manager.getEmail());
        userValidator.validateManager(manager, bindingResult);
        if (getUserByEmail(manager.getEmail()) != null) {
            bindingResult.addError(new ObjectError("Email", "Exist"));
            returnModel.setMessage("email Exist");
        }
        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError() != null) {
                returnModel.setMessage(returnModel.getMessage() + " / " + bindingResult.getFieldError().getField() + "Incorrect");
            }
            return returnModel;
        }
        manager.setRole(Role.MANAGER);
        manager.setActive(true);
        String token = UUID.randomUUID().toString();
        try {
            mailService.buildConfirmRegisterManager("Confirmation registration", manager, token);
        } catch (MessagingException | MailSendException ex) {
            log.error("Error! There is problems with sending email!", ex);
            bindingResult.rejectValue(ValidationConstants.EMAIL, ValidationConstants.FAILED_SEND_EMAIL_MSG);
            returnModel.setEmail(manager.getEmail());
            returnModel.setMessage("email didn't sent");
            return returnModel;
        }
        create(manager);
        this.tokenService.createToken(token, manager);
        returnModel.setEmail(manager.getEmail());
        return returnModel;
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
