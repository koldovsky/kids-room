package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ErrorPages;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;

/**
 * Created by Nestor on 18.05.2016.
 */

@ControllerAdvice
public class ExceptionHandlingController {

    @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView denyAccess(Principal principal){
        ModelAndView model = new ModelAndView();
        model.setViewName(ErrorPages.ACCESS_DENIED_VIEW);
        model.getModelMap().addAttribute(UsersConst.USER, userService.getUserByEmail(principal.getName()));
        return model;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView alertNotFound(){
        ModelAndView model = new ModelAndView();
        model.setViewName(ErrorPages.NOT_FOUND_VIEW);
        return model;
    }
}
