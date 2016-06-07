package ua.softserveinc.tc.controller.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.dto.RoomDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Demian on 28.05.2016.
 */
@Controller
public class StatisticsController
{
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statistics()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConst.STATISTICS_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = dateUtil.getStringDate(dateUtil.currentDate());
        String dateThen = dateUtil.getStringDate(dateUtil.dateMonthAgo());

        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(dateThen, dateNow);
        Map<Room, Long> statistics = bookingService.generateStatistics(bookings);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConst.STATISTICS, statistics);

        return model;
    }

    @RequestMapping(value = "/refreshRooms/{startDate}/{endDate}", method = RequestMethod.GET)
    public @ResponseBody String refreshView(@PathVariable String startDate,
                                            @PathVariable String endDate)
    {
        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(startDate, endDate);
        Map<Room, Long> statistics = bookingService.generateStatistics(bookings);
        Gson gson = new Gson();

        return gson.toJson(statistics.keySet().stream()
                .map(room -> new RoomDTO(room, statistics.get(room)))
                .collect(Collectors.toList()));
    }
}