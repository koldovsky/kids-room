package ua.softserveinc.tc.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.FileUploadFormObject;
import ua.softserveinc.tc.validator.LogicalRequestsValidator;

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
        if(!LogicalRequestsValidator.isRequestValid(id)){
            throw new ResourceNotFoundException();
        }

        Long idL = Long.parseLong(id);
        User current = userService.getUserByEmail(principal.getName());
        Child kid = childService.findById(idL);

        if(current.getRole() != Role.MANAGER && !current.equals(kid.getParentId())) {
            throw new AccessDeniedException("Have to be manager or parent");
        }

        if(!kid.isEnabled()){
            throw new ResourceNotFoundException();
        }

        ModelAndView model = new ModelAndView();
        model.setViewName(ChildConstants.View.KID_PROFILE);
        model.getModelMap().addAttribute(ChildConstants.View.KID_ATTRIBUTE, kid);
        model.getModelMap().addAttribute("fileForm", new FileUploadFormObject());
        model.getModelMap().addAttribute("pageChecker","needBack");//value for checking the page in header.jsp
        return model;
    }
}
