package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ExcelService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ExcelDocument;
import ua.softserveinc.tc.validator.TimeValidatorImpl;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import ua.softserveinc.tc.util.DateUtil;

/**
 * Controller handles reports on User's bookings
 */

@Controller
public class MyBookingsController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TimeValidatorImpl timeValidator;

    @Autowired
    private ExcelService excelService;
    /**
     * Renders a view
     * @param principal
     * @return required view
     * @throws AccessDeniedException
     * if requesting user has no permissions
     * to access this page
     * @throws ResourceNotFoundException
     * if any of the requesting resources were not found
     */
    @GetMapping("/mybookings")
    public ModelAndView getMyBookings(Principal principal)
    throws AccessDeniedException{
        User current = userService.getUserByEmail(principal.getName());
        if(current.getRole() != Role.USER){
            throw new AccessDeniedException("Have to be a User");
        }

        ModelAndView model = new ModelAndView();
        model.setViewName(UserConstants.Model.MY_BOOKINGS_VIEW);
        model.getModelMap().addAttribute("pageChecker","notHome");//value for checking the page in header.jsp
        return model;
    }


    /**
     * Handles HTTP GET request for bookings in custom range of time
     *
     * @param startDate time range lower limit
     * @param endDate time range upp er limit
     * @param principal User principal
     *
     * @return A list of DTOs containing all valuable info in JSON
     * @throws AccessDeniedException
     * if requesting user has no permissions
     * to access this page
     * @throws ResourceNotFoundException
     * if any of the requesting resources were not found
     */
    @GetMapping(value = "mybookings/getbookings", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getBookings(
                       @RequestParam(value = "startDate") String startDate,
                       @RequestParam(value = "endDate") String endDate,
                       Principal principal)
            throws ResourceNotFoundException, ParseException {

        if(!timeValidator.isStartDateBefore(startDate, endDate)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.DATE_IS_NOT_VALID);
        }
        User currentUser = userService.getUserByEmail(principal.getName());
        if(currentUser.getRole() != Role.USER){
            throw new AccessDeniedException("Have to be a User");
        }

        List<Booking> myBookings = bookingService.getBookings(
                new Date[] {DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                currentUser, BookingState.COMPLETED);
        List<BookingDto> dtos = myBookings
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dtos));
    }

    @GetMapping(value = "excel")
    public ModelAndView excel(@RequestParam(value = "startDate") String startDate,
                              @RequestParam(value = "endDate") String endDate,
                              Principal principal) {

        User currentUser = userService.getUserByEmail(principal.getName());
        List<Booking> myBookings = bookingService.getBookings(
                new Date[] {DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                currentUser, BookingState.COMPLETED);
        List<BookingDto> dtos = myBookings
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());

        String[] additionalFields = {
                BookingConstants.ADDITIONAL_EXCEL_FIELDS[0],
                String.valueOf(bookingService.getSumTotal(myBookings)/100.0),
                BookingConstants.ADDITIONAL_EXCEL_FIELDS[1]
        };
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new ExcelDocument());
        modelAndView.addObject("additionalFields", Arrays.asList(additionalFields));
        modelAndView.addObject("data", excelService.getDataFromDto(dtos));
        modelAndView.addObject("fileName", startDate + "-" + endDate);

        return modelAndView;
    }
}
