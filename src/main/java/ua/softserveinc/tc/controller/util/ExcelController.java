package ua.softserveinc.tc.controller.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.*;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExcelController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ExcelData<BookingDto> excel;

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "excel")
    public ModelAndView excel(@RequestParam(value = ReportConstants.START_DATE) String startDate,
                              @RequestParam(value = ReportConstants.END_DATE) String endDate,
                              @RequestParam(value = ReportConstants.ROOM_ID, required = false) Long roomId,
                              @RequestParam(value = ReportConstants.EMAIL, required = false) String email,
                              Principal principal) {

        User currentUser;
        ModelAndView modelAndView = new ModelAndView();
        List<Booking> bookings;
        if (roomId == null) {
            currentUser = userService.getUserByEmail(principal.getName());
            bookings = bookingService.getBookings(
                    new Date[] {DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                    currentUser, BookingState.COMPLETED);
            excel.setTableData(bookings.stream().map(BookingDto::new).collect(Collectors.toList()));
        } else if (email != null) {
            currentUser = userService.getUserByEmail(email);
            Room room = roomService.findByIdTransactional(roomId);
            excel.setTableData(bookingService.getBookings(
                    new BookingsCharacteristics.Builder()
                            .setDates(new Date[]{DateUtil.toBeginOfDayDate(startDate),
                                    DateUtil.toEndOfDayDate(endDate)})
                            .setUsers(Collections.singletonList(currentUser))
                            .setRooms(Collections.singletonList(room))
                            .setBookingsStates(Collections.singletonList(BookingState.COMPLETED))
                            .build()).stream().map(BookingDto::new).collect(Collectors.toList()));
            excel.addAdditionalFields(ExcelUserRoomBooking.ADDITIONAL_EXCEL_FIELDS[2]
                    + currentUser.getFullName());
        } else {
            Room room = roomService.findByIdTransactional(roomId);
            bookings = bookingService.getBookings(
                    new Date[]{DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                    room, BookingState.COMPLETED);
            excel.setTableData(bookingService.generateAReport(bookings));
            excel.addAdditionalFields(ExcelUserRoomBooking.ADDITIONAL_EXCEL_FIELDS[2]
                    + room.getName());
        }

        modelAndView.setView(new ExcelDocument());
        modelAndView.addObject("data", excel);
        modelAndView.addObject("fileName", startDate + "-" + endDate);

        return modelAndView;
    }
}