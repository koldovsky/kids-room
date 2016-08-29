package ua.softserveinc.tc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.validator.ChildValidator;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nestor on 07.05.2016.
 * Controller handles kid registration
 */
@Controller
public class RegisterNewKidController {
    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChildValidator childValidator;

    @Autowired
    private ApplicationConfigurator applicationConfigurator;

    /**
     * Handles HTTP GET request to display registration form
     *
     * @param model
     * @return view name
     */
    @RequestMapping(value = "/registerkid", method = RequestMethod.GET)
    public String registerKid(Model model, HttpServletRequest request){
        if(!model.containsAttribute(ChildConstants.View.KID_ATTRIBUTE)) {
            model.addAttribute(ChildConstants.View.KID_ATTRIBUTE, new Child());
            model.addAttribute("pageChecker","needBack");//value for checking the page in header.jsp
        }

        request.getSession().setAttribute(UserConstants.Model.ATRIBUTE_CONFIG, applicationConfigurator.getObjectDto());
        return ChildConstants.View.KID_REGISTRATION;
    }


    /**
     * Handles HTTP POST request from user after form submitting
     * Validates the invariants of the Child object and prepares it
     * for persistance into the database
     *
     * @param child A Child object to be persisted into the database
     * @param principal A Spring Security interface implementation
     *                  that represents currently logged in account
     * @param bindingResult A result holder for object binding
     * @return redirects to profile view if successful
     *         or current view if failed
     */
    @RequestMapping(value = "/registerkid", method = RequestMethod.POST)
    public String submit(
            @ModelAttribute(value = ChildConstants.View.KID_ATTRIBUTE) Child child,
            Principal principal,
            BindingResult bindingResult) {

        child.setParentId(userService.getUserByEmail(
                        principal.getName()));

        childValidator.validate(child, bindingResult);

        if(bindingResult.hasErrors()) {
            return ChildConstants.View.KID_REGISTRATION;
        }

        childService.create(child);
        return "redirect:/" + ChildConstants.View.KID_PROFILE + "?id=" + child.getId();
    }

    /**
     * Registers custom date format
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}