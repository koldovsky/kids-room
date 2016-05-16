package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Nestor on 14.05.2016.
 */

@Controller
public class MyBookingsController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/mybookings", method = RequestMethod.GET)
    public ModelAndView getMyBookings(Principal principal){

        //TODO: тут треба доробити, бо показувати буде тільки рівно на проміжку -1 місяць від сьогодні
        //TODO: юзеру дати можливість вибрати дату
        ModelAndView model = new ModelAndView();
        model.setViewName(UsersConst.MY_BOOKINGS_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = bookingService.getCurrentDate();
        String dateThen = bookingService.getDateMonthAgo();

        User currentUser = userService
                .getUserByEmail(principal.getName());

        List<Booking> myBookings = bookingService
                .getBookingsByUserByRangeOfTime(currentUser, dateThen, dateNow);

        int sumTotal = bookingService.getSumTotal(myBookings);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConst.BOOKINGS, myBookings);
        modelMap.addAttribute(ReportConst.SUM_TOTAL, sumTotal);

        return model;
    }
}
