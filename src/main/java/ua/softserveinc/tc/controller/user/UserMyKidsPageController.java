package ua.softserveinc.tc.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;


/**
 * Created by Nestor on 07.05.2016.
 * Controller handles request for "My Kids" view
 */

@Controller
public class UserMyKidsPageController {
    @Autowired
    private UserService userService;

    /**
     *
     * @param principal
     * @return "My Kids" view
     * @throws AccessDeniedException
     * if requesting user has no permissions
     * to access this page
     * @throws ResourceNotFoundException
     * if any of the requesting resources were not found
     */
    @RequestMapping(value = "/mykids", method = RequestMethod.GET)
    public ModelAndView myKids(Principal principal)
    throws AccessDeniedException{

        ModelAndView model = new ModelAndView();
        model.setViewName(MyKidsConst.MY_KIDS_VIEW);
        User current =  userService.getUserByEmail(principal.getName());
        if(current.getRole()!= Role.USER) {
            throw new AccessDeniedException("Only parents have access to this page");
        }

        List<Child> myKids = current.getEnabledChildren();
        ModelMap modelMap = model.getModelMap();

        if(!modelMap.containsAttribute(MyKidsConst.MY_KIDS_LIST_ATTRIBUTE)) {
            modelMap.addAttribute(MyKidsConst.MY_KIDS_LIST_ATTRIBUTE, myKids);
        }

        return model;
    }
}