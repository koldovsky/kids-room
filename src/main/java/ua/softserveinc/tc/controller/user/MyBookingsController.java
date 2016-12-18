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
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.TimeValidator;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import static ua.softserveinc.tc.util.DateUtil.toDate;

/**
 * Created by Nestor on 14.05.2016.
 * Controller handles reports on User's bookings
 */

@Controller
public class MyBookingsController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TimeValidator timeValidator;
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
    @GetMapping("mybookings/getbookings")
    @ResponseBody
    public ResponseEntity<String> getBookings(
                       @RequestParam(value = "startDate") String startDate,
                       @RequestParam(value = "endDate") String endDate,
                       Principal principal)
            throws ResourceNotFoundException, ParseException {

        if(!timeValidator.validateDate(startDate, endDate)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.DATE_IS_NOT_VALID);
        }
        User currentUser = userService.getUserByEmail(principal.getName());
        if(currentUser.getRole() != Role.USER){
            throw new AccessDeniedException("Have to be a User");
        }
        List<Booking> myBookings = bookingService.getBookings(toDate(startDate),
                toDate(endDate), currentUser, BookingState.COMPLETED);
        List<BookingDto> dtos = myBookings
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dtos));
    }
}
