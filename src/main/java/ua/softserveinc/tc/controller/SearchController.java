package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.softserveinc.tc.constants.SearchConstants;
import ua.softserveinc.tc.dto.ChildDTO;
import ua.softserveinc.tc.dto.UserDTO;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.search.ChildSearch;
import ua.softserveinc.tc.search.UserSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward on 5/14/16.
 */
@Controller
public class SearchController {

    @Autowired
    private UserSearch userSearch;

    @Autowired
    private ChildSearch childSearch;

    @RequestMapping(value = SearchConstants.userSearchUrl, method = RequestMethod.GET)
    public @ResponseBody String searchUser(@RequestParam("field") String field) {
        List<UserDTO> result = new ArrayList<UserDTO>();

        if (isValidRequestField(field)) {
            List<User> users = userSearch.search(field);
            for (User user : users) {
                if (user.isConfirmed()) {
                    result.add(new UserDTO(user));
                }
            }
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = SearchConstants.childSearchUrl, method = RequestMethod.GET)
    public @ResponseBody String searchChild(@RequestParam("field") String field) {
        List<ChildDTO> result = new ArrayList<ChildDTO>();

        if (isValidRequestField(field)) {
            List<Child> children = childSearch.search(field);
            for (Child child : children) {
                if (child.isEnabled()) {
                    result.add(new ChildDTO(child));
                }
            }
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    private boolean isValidRequestField(String field) {
        return field.length() >= 3 && field.length() <= 1024;
    }

}
