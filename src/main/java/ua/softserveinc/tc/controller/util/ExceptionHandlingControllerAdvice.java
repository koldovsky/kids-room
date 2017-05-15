package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.server.exception.BadUploadException;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.server.exception.TokenInvalidException;
import ua.softserveinc.tc.util.Log;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Class serves for global exception handling
 */

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @Log
    private Logger log;

    @Inject
    private MessageSource messageSource;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public ModelAndView handleError404(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_NOT_FOUND_CODE, HttpStatus.NOT_FOUND.toString(), locale);
    }


    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(TokenInvalidException.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_TOKEN_NOT_FOUND_CODE,
                ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    /**
     * Responds to user with AccessDenied view
     */
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleError403(AccessDeniedException e, HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_ACCESS_DENIED, ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleError500(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_INTERNAL_ERROR, ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    /**
     * Handles bad image upload if the uploaded file cannot be persisted
     * to the database for any reason
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadUploadException.class)
    public ModelAndView badUpload(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_BAD_UPLOAD, ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JpaSystemException.class)
    public ModelAndView jpaExceptionHandler(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_COMMON, ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class})
    public ModelAndView duplicateBooking(HttpServletRequest req, Exception ex, Locale locale) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return buildModelAndView(ErrorConstants.MESSAGE_BAD_REQUEST, ErrorConstants.DEFAULT_ERROR_FILE_NAME, locale);
    }

    private ModelAndView buildModelAndView(String message, String filename, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ErrorConstants.ERROR_VIEW);
        modelAndView.addObject(ErrorConstants.ERROR_MESSAGE,
                messageSource.getMessage(message, null, locale));
        modelAndView.addObject(ErrorConstants.ERROR_FILE, filename);

        return modelAndView;
    }
}
