package ua.softserveinc.tc.controller.util;

import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.server.exception.BadUploadException;
import ua.softserveinc.tc.server.exception.DuplicateBookingException;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.server.exception.TokenInvalidException;
import ua.softserveinc.tc.util.Log;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nestor on 18.05.2016.
 * Class serves for global exception handling
 */

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @Log
    private Logger log;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)

    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public String handleError404(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return ErrorConstants.NOT_FOUND_VIEW; }


    @ResponseStatus
    @ExceptionHandler(TokenInvalidException.class)
    public String handleError(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return ErrorConstants.TOKEN_NOT_FOUND_VIEW;
    }


    /**
     * Responds to user with AccessDenied view
     *
     * @return AccessDenied view
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleError403(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return ErrorConstants.ACCESS_DENIED_VIEW;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleError500(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return ErrorConstants.INTERNAL_SERVER_ERROR_VIEW;
    }

    /**
     * Handles bad image upload if the uploaded file cannot be persisted
     * to the database for any reason
     *
     * @return "Bad Upload" view
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadUploadException.class)
    public String badUpload(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return "error-bad-upload";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JpaSystemException.class)
    public String jpaExceptionHandler(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return "criticalRuntimeException";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateBookingException.class)
    public String duplicateBooking(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);

        return "error-duplicate-booking";
    }

}
