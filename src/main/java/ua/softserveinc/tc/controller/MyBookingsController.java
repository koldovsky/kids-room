package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @RequestMapping(value = "/mybookings", method = RequestMethod.GET)
    public ModelAndView getMyBookings(Principal principal){

        //TODO: тут треба доробити, бо показувати буде тільки рівно на проміжку -1 місяць від сьогодні
        //TODO: напевно, менеджеру треба якусь кнопку, де він буде скидати букінги юзерів в своїй кімнаті
        //TODO: або треба щоб менеджер встановлював, коли це має скидатись

        ModelAndView model = new ModelAndView();
        model.setViewName(UsersConst.MY_BOOKINGS_VIEW);

        ModelMap modelMap = model.getModelMap();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar builder = Calendar.getInstance();
        Date now = builder.getTime();
        builder.add(Calendar.MONTH, -1);
        Date startDate = builder.getTime();

        String nowStr = formatter.format(now);
        String startDateStr = formatter.format(startDate);

        User currentUser = userService
                .getUserByEmail(principal.getName());

        List<Booking> myBookings = bookingService
                .getBookingsByUserByRangeOfTime(currentUser, startDateStr, nowStr);
        int sumTotal = Booking.getSumTotal(myBookings);

        modelMap.addAttribute(ReportConst.PARENT, currentUser);
        modelMap.addAttribute(ReportConst.DATE_NOW, nowStr);
        modelMap.addAttribute(ReportConst.DATE_THEN, startDateStr);
        modelMap.addAttribute(ReportConst.BOOKINGS, myBookings);
        modelMap.addAttribute(ReportConst.SUM_TOTAL, sumTotal);

        return model;
    }
}
