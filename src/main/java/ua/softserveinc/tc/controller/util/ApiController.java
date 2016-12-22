package ua.softserveinc.tc.controller.util;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.softserveinc.tc.constants.ApiConstants;
import ua.softserveinc.tc.constants.LocaleConstants;
import ua.softserveinc.tc.dto.ChildDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;



/**
 * Created by edward on 5/17/16.
 */
@RestController
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ApplicationConfigurator configurator;

    @Autowired
    private MessageSource messageSource;

    private static @Log Logger LOG;

    /**
     * Handles HTTP GET request to return all users registered in system
     *
     * @return users JSON
     */
    @GetMapping(ApiConstants.USER_REST_URL)
    public String getUser() {
        List<UserDto> result = new ArrayList<>();
        List<User> users = userService.findAll();

        for (User user : users) {
            result.add(new UserDto(user));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * Handles HTTP GET request to return user registered in system by id
     *
     * @param id
     * @return user JSON
     */
    @GetMapping(ApiConstants.USER_REST_BY_ID_URL)
    public String getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        Gson gson = new Gson();

        return gson.toJson(new UserDto(user));
    }

    /**
     * Handles HTTP GET request to return all children registered in system
     *
     * @return children JSON
     */
    @GetMapping(ApiConstants.CHILD_REST_URL)
    public String getChild() {
        List<ChildDto> result = new ArrayList<>();
        List<Child> children = childService.findAll();

        for (Child child : children) {
            result.add(new ChildDto(child));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * Handles HTTP POST request to store child in system
     *
     * @param child
     * @return child JSON
     */
    @PostMapping(ApiConstants.CHILD_REST_URL)
    public String addChild(@RequestBody Child child) {
        childService.create(child);
        Gson gson = new Gson();
        return gson.toJson(child);
    }

    /**
     * Handles HTTP GET request to return child registered in system by id
     *
     * @param id
     * @return child JSON
     */
    @GetMapping(ApiConstants.CHILD_BY_ID_REST_URL)
    public String getChildById(@PathVariable long id) {
        Child child = childService.findById(id);
        Gson gson = new Gson();

        return gson.toJson(new ChildDto(child));
    }

    /**
     * Handles HTTP GET request to display children who are currently in a given room
     *
     * @param roomId
     * @return children JSON
     */
    @GetMapping(ApiConstants.GET_ACTIVE_CHILDREN_IN_ROOM_URL)
    public String getChildrenInRoom(@PathVariable long roomId) {
        List<ChildDto> result = new ArrayList<>();
        List<Child> children = childService.getActiveChildrenInRoom(roomService.findById(roomId));

        for (Child child : children) {
            result.add(new ChildDto(child));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * Handles HTTP GET request to display parents of child with a given id
     *
     * @param id
     * @return user JSON
     */
    @GetMapping(ApiConstants.GET_CHILD_PARENT_REST_URL)
    public String getParentByChild(@PathVariable long id) {
        Child child = childService.findById(id);
        User user = child.getParentId();
        Gson gson = new Gson();

        return gson.toJson(new UserDto(user));
    }

    /**
     * Handles HTTP GET request to display application configuration
     *
     * @return configuration JSON
     */
    @GetMapping(ApiConstants.GET_APP_CONFIGURATION)
    public String getAppConfiguration() {
        Gson gson = new Gson();
        return gson.toJson(configurator);
    }

    /**
     * Handles HTTP GET request to display corresponding application localization, eg. 'EN', 'UA'
     *
     * @param locale
     * @return localization JSON
     */
    @GetMapping(ApiConstants.GET_APP_LOCALIZATION)
    public String getLocale(@PathVariable("locale") String locale) {
        Map<String, String> messages = new HashMap<>();
        Locale localeObj = new Locale(locale);

        for (String message : LocaleConstants.getMessages()) {
            messages.put(message, messageSource.getMessage(message, null, localeObj));
        }

        Gson gson = new Gson();
        return gson.toJson(messages);
    }

}
