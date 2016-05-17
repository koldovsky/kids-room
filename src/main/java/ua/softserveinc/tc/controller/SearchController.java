package ua.softserveinc.tc.controller;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import ua.softserveinc.tc.dto.ChildDTO;
import ua.softserveinc.tc.dto.UserDTO;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.search.ChildSearch;
import ua.softserveinc.tc.search.UserSearch;

/**
 * Created by edward on 5/14/16.
 */
@Controller
public class SearchController {

    @Autowired
    private UserSearch userSearch;

    @Autowired
    private ChildSearch childSearch;

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public @ResponseBody String searchUser(@RequestParam("field") String field) {
        List<User> users = userSearch.search(field);

        List<UserDTO> result = new ArrayList<UserDTO>();
        for (User user : users) {
            result.add(new UserDTO(user));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = "/child/search", method = RequestMethod.GET)
    public @ResponseBody String searchChild(@RequestParam("field") String field) {
        List<Child> children = childSearch.search(field);

        List<ChildDTO> result = new ArrayList<ChildDTO>();
        for (Child child : children) {
            result.add(new ChildDTO(child));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
