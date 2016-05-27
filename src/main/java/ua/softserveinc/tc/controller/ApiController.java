package ua.softserveinc.tc.controller;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import ua.softserveinc.tc.constants.ApiConstants;
import ua.softserveinc.tc.dto.ChildDTO;
import ua.softserveinc.tc.dto.UserDTO;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

/**
 * Created by edward on 5/17/16.
 */
@Controller
public class ApiController {

    @Autowired
    UserService userService;

    @Autowired
    ChildService childService;

    @RequestMapping(value = ApiConstants.usersRestUrl, method = RequestMethod.GET)
    public @ResponseBody String getUser() {
        List<UserDTO> result = new ArrayList<UserDTO>();
        List<User> users = userService.findAll();

        for (User user : users) {
            if (user.isEnabled()) {
                result.add(new UserDTO(user));
            }
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = ApiConstants.usersRestByIdUrl, method = RequestMethod.GET)
    public @ResponseBody String getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        Gson gson = new Gson();

        if (user.isEnabled()) {
            return gson.toJson(new UserDTO(user));
        }
        return null;
    }

    @RequestMapping(value = ApiConstants.childrenRestUrl, method = RequestMethod.GET)
    public @ResponseBody String getChild() {
        List<ChildDTO> result = new ArrayList<ChildDTO>();
        List<Child> children = childService.findAll();

        for (Child child : children) {
            if (child.isEnabled()) {
                result.add(new ChildDTO(child));
            }
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = ApiConstants.childrenRestUrl, method = RequestMethod.POST)
    public @ResponseBody String addChild(@RequestBody Child child) {
        childService.create(child);
        Gson gson = new Gson();
        return gson.toJson(child);
    }

    @RequestMapping(value = ApiConstants.childrenByIdRestUrl, method = RequestMethod.GET)
    public @ResponseBody String getChildById(@PathVariable long id) {
        Child child = childService.findById(id);
        Gson gson = new Gson();

        if (child.isEnabled()) {
            return gson.toJson(new ChildDTO(child));
        }
        return null;
    }

    @RequestMapping(value = ApiConstants.getChildrenParentRestUrl, method = RequestMethod.GET)
    public @ResponseBody String getParentByChild(@PathVariable long id) {
        Child child = childService.findById(id);
        User user = child.getParentId();
        Gson gson = new Gson();

        if (user.isEnabled()) {
            return gson.toJson(new UserDTO(user));
        }
        return null;
    }

}
