package ua.softserveinc.tc.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.RoomReportValuesDto;
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
    //@Qualifier("excelUser")
    private ExcelData<BookingDto> excelUser;

    @Autowired
    //@Qualifier("excelRoom")
    private ExcelData<RoomReportValuesDto> excelRoom;

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "excel/user")
    public ModelAndView getUserExcelReport(@RequestParam(value = ReportConstants.START_DATE) String startDate,
                              @RequestParam(value = ReportConstants.END_DATE) String endDate,
                              @RequestParam(value = ReportConstants.ROOM_ID, required = false) Long roomId,
                              @RequestParam(value = ReportConstants.EMAIL, required = false) String email,
                              Principal principal) {

        User currentUser;
        List<Booking> bookings;
        if (roomId == null) {
            currentUser = userService.getUserByEmail(principal.getName());
            bookings = bookingService.getBookings(
                    new Date[] {DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                    currentUser, BookingState.COMPLETED);

            excelUser.setTableData(CurrencyConverter.getInstance().convertBookingSum(bookings));
            excelUser.addAdditionalFields(ExcelUserBooking.ADDITIONAL_EXCEL_FIELDS[3] +
                    currentUser.getFullName());
            excelUser.addAdditionalFields(ExcelUserBooking.ADDITIONAL_EXCEL_FIELDS[0] +
                    CurrencyConverter.getInstance()
                            .convertSingle(bookings.stream().mapToLong(Booking::getSum).sum()));
        } else {
            Room room = roomService.findByIdTransactional(roomId);
            if (!room.getManagers().contains(userService.getUserByEmail(principal.getName()))) {
                throw new AccessDeniedException("You don't have access to this page");
            }
            currentUser = userService.getUserByEmail(email);
            bookings = bookingService.getBookings(
                    new BookingsCharacteristics.Builder()
                            .setDates(new Date[]{DateUtil.toBeginOfDayDate(startDate),
                                    DateUtil.toEndOfDayDate(endDate)})
                            .setUsers(Collections.singletonList(currentUser))
                            .setRooms(Collections.singletonList(room))
                            .setBookingsStates(Collections.singletonList(BookingState.COMPLETED))
                            .build());

            excelUser.setTableData(CurrencyConverter.getInstance().convertBookingSum(bookings));
            excelUser.addAdditionalFields(ExcelUserBooking.ADDITIONAL_EXCEL_FIELDS[3] +
                    currentUser.getFullName());
            excelUser.addAdditionalFields(ExcelUserBooking.ADDITIONAL_EXCEL_FIELDS[0] +
                    CurrencyConverter.getInstance()
                            .convertSingle(bookings.stream().mapToLong(Booking::getSum).sum()));
        }

        return getModelAndView(excelUser, startDate + "-" + endDate);
    }

    @GetMapping(value = "excel/room")
    public ModelAndView getRoomReport(@RequestParam(value = ReportConstants.START_DATE) String startDate,
                              @RequestParam(value = ReportConstants.END_DATE) String endDate,
                              @RequestParam(value = ReportConstants.ROOM_ID) Long roomId,
                              Principal principal) {

        Room room = roomService.findByIdTransactional(roomId);
        User manager = userService.getUserByEmail(principal.getName());
        if (!room.getManagers().contains(manager)) {
            throw new AccessDeniedException("You don't have access to this page");
        }
        List<Booking> bookings = bookingService.getBookings(
                new Date[]{DateUtil.toBeginOfDayDate(startDate), DateUtil.toEndOfDayDate(endDate)},
                room, BookingState.COMPLETED);

        excelRoom.setTableData(bookingService.generateRoomReport(bookings));
        excelRoom.addAdditionalFields(ExcelUserBooking.ADDITIONAL_EXCEL_FIELDS[2] +
                room.getName());

        return getModelAndView(excelRoom, startDate + "-" + endDate);
    }

    private ModelAndView getModelAndView(ExcelData excelData, String fileName) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setView(new ExcelDocument());
        modelAndView.addObject("data", excelData);
        modelAndView.addObject("fileName", fileName);

        return modelAndView;
    }
}
