package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public ResponseEntity<List<User>> searchUser(@RequestParam("field") String field) {
        List<User> result = userSearch.search(field);

        return new ResponseEntity<List<User>>(result, HttpStatus.OK);
    }

}
