package ua.softserveinc.tc.controller;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.search.UserSearch;

/**
 * Created by edward on 5/14/16.
 */
@Controller
public class SearchController {

    @Autowired
    private UserSearch userSearch;

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public @ResponseBody String searchUser(@RequestParam("field") String field) {
        List<User> result = userSearch.search(field);

        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
