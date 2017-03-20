package ua.softserveinc.tc.controller.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.dto.RoomDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.dateNow;
import static ua.softserveinc.tc.util.DateUtil.dateMonthAgo;
import ua.softserveinc.tc.util.DateUtil;
import static ua.softserveinc.tc.util.DateUtil.getStringDate;

@Controller
public class StatisticsController {

    @Autowired
    private BookingService bookingService;


    @GetMapping("/adm-statistics")
    public ModelAndView statistics() {
        ModelAndView model = new ModelAndView(ReportConstants.STATISTICS_VIEW);
        ModelMap modelMap = model.getModelMap();

        String endDate = getStringDate(dateNow());
        String startDate = getStringDate(dateMonthAgo());

        modelMap.addAttribute(ReportConstants.END_DATE, endDate);
        modelMap.addAttribute(ReportConstants.START_DATE, startDate);

        return model;
    }

    @ResponseBody
    @GetMapping("/refreshRooms/{startDate}/{endDate}")
    public String refreshView(@PathVariable String startDate,
                              @PathVariable String endDate) {
        List<Booking> bookings = bookingService.getBookings(DateUtil.toBeginOfDayDate(startDate),
                DateUtil.toEndOfDayDate(endDate), BookingState.COMPLETED);
        Map<Room, Double> statistics = bookingService.generateStatistics(bookings);
        Gson gson = new Gson();

        return gson.toJson(statistics.keySet().stream()
                .map(room -> new RoomDto(room, statistics.get(room)))
                .collect(Collectors.toList()));
    }
}