package ua.softserveinc.tc.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.MyKidsConst;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;


import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * Created by Nestor on 21.05.2016.
 * Controller handles kids profiles
 */
@Controller
public class KidsProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    /**
     * Handles HTTP GET request for kids profile
     *
     * @param id ID of a kid
     * @param principal User authentication info
     * @return view name
     * @throws AccessDeniedException
     *          in case a user who has sent the request is not MANAGER
     *          or a parent to this child
     * @throws ResourceNotFoundException
     *          in case no kid with such ID exists
     *          OR invalid request was detected
     */
    @RequestMapping(value = "/profile",
            method = RequestMethod.GET)
    public ModelAndView getProfile(@RequestParam("id") String id, Principal principal)
            throws AccessDeniedException, ResourceNotFoundException{
        //Checking if URL is valid. If it cannot be parsed to Long an exception
        //is thrown and passed to @ControllerAdvice
        Long idL;
        try {idL = Long.parseLong(id);}
        catch(Exception e) {throw new ResourceNotFoundException();}

        User current = userService.getUserByEmail(principal.getName());
        Child kid = childService.findById(idL);

        if(current.getRole() != Role.MANAGER && !current.equals(kid.getParentId())) {
            throw new AccessDeniedException("Have to be manager or parent");
        }

        ModelAndView model = new ModelAndView();
        model.setViewName(MyKidsConst.KID_PROFILE_VIEW);
        model.getModelMap().addAttribute(MyKidsConst.KID_ATTRIBUTE, kid);
        return model;
    }
}
