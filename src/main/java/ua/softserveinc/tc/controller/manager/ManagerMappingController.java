package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ManagerConstants;
import ua.softserveinc.tc.constants.ManagerConstants.ManagerViewNames;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.CurrencyConverter;
import ua.softserveinc.tc.util.DateUtil;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ua.softserveinc.tc.util.DateUtil.*;

@Controller
@RequestMapping("/")
public class ManagerMappingController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoomService roomService;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private Environment environment;

  @GetMapping(ManagerConstants.MANAGER_CALENDAR)
  public String getManagerCalendar(Model model, Principal principal) {
    String email = principal.getName();
    User user = userService.getUserByEmail(email);
    model.addAttribute(UserConstants.Entity.ROOMS, userService.getActiveRooms(user));
    return ManagerViewNames.MANAGER_CALENDAR_VIEW;
  }

  @GetMapping(ManagerConstants.MANAGER_REPORT)
  public String getManagerReport(Principal principal, Model model) {
    String endDate = getStringDate(dateNow());
    String startDate = getStringDate(dateMonthAgo());
    List<Room> rooms = userService
        .getActiveRooms(userService.getUserByEmail(principal.getName()));
    model.addAttribute(ReportConstants.END_DATE, endDate);
    model.addAttribute(ReportConstants.START_DATE, startDate);
    model.addAttribute(ReportConstants.ROOMS, rooms);
    return ManagerViewNames.MANAGER_REPORT_VIEW;
  }

  @GetMapping(ManagerConstants.MANAGER_REPORT_ALL)
  public ModelAndView allParentsBookings(
      @RequestParam(value = ReportConstants.START_DATE) String startDate,
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


    Map<User, Long> report = bookingService.generateAReport(bookings);

    ModelAndView modelAndView = new ModelAndView(ManagerViewNames.MANAGER_REPORT_ALL_VIEW);
    ModelMap modelMap = modelAndView.getModelMap();

    modelMap.addAttribute(ReportConstants.ROOM, room);
    modelMap.addAttribute(ReportConstants.REPORT, CurrencyConverter.getInstance().convertCurrency(report));
    modelMap.addAttribute(ReportConstants.SUM_TOTAL, CurrencyConverter.getInstance().convertSingle(bookingService.getSumTotal(bookings)));
    modelMap.addAttribute(ReportConstants.END_DATE, endDate);
    modelMap.addAttribute(ReportConstants.START_DATE, startDate);

    return modelAndView;
  }

  @GetMapping(ManagerConstants.MANAGER_REPORT_PARENT)
  public ModelAndView parentBookings(
      @RequestParam(value = ReportConstants.START_DATE) String startDate,
      @RequestParam(value = ReportConstants.END_DATE) String endDate,
      @RequestParam(value = ReportConstants.ROOM_ID) Long roomId,
      @RequestParam(value = ReportConstants.EMAIL) String email,
      Principal principal) {

    Room room = roomService.findByIdTransactional(roomId);
    User manager = userService.getUserByEmail(principal.getName());
    if (!room.getManagers().contains(manager)) {
      throw new AccessDeniedException("You don't have access to this page");
    }
    User parent = userService.getUserByEmail(email);

    BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
        .setDates(new Date[]{DateUtil.toBeginOfDayDate(startDate),
            DateUtil.toEndOfDayDate(endDate)})
        .setUsers(Collections.singletonList(parent))
        .setRooms(Collections.singletonList(room))
        .setBookingsStates(Collections.singletonList(BookingState.COMPLETED))
        .build();


      List<Booking> bookings = bookingService.getBookings(characteristic);

    ModelAndView modelAndView = new ModelAndView(ManagerViewNames.MANAGER_REPORT_PARENT_VIEW);
    ModelMap modelMap = modelAndView.getModelMap();

    modelMap.addAttribute(ReportConstants.ROOM, room);
    modelMap.addAttribute(ReportConstants.PARENT, parent);
    modelMap.addAttribute(ReportConstants.END_DATE, endDate);
    modelMap.addAttribute(ReportConstants.BOOKINGS, CurrencyConverter.getInstance().convertBookingSum(bookings));
    modelMap.addAttribute(ReportConstants.SUM_TOTAL, CurrencyConverter.getInstance().convertSingle(bookingService.getSumTotal(bookings)));
    modelMap.addAttribute(ReportConstants.START_DATE, startDate);
    modelMap
        .addAttribute("pageChecker", "reportParent"); //value for checking the page in header.jsp

    return modelAndView;
  }

  @GetMapping(ManagerConstants.MANAGER_EDIT_BOOKING)
  public String getManagerEditBooking(Principal principal, Model model) {
    List<User> users = userService
        .findAllUsersByRole(Role.USER);
    List<Room> rooms = userService
        .getActiveRooms(userService.getUserByEmail(principal.getName()));
    //TODO-VL create some constants to that strings below
    model.addAttribute("rooms", rooms);
    model.addAttribute("users", users);
    Collections
        .sort(users, (user1, user2) -> user1.getFirstName().compareTo(user2.getFirstName()));
    return ManagerViewNames.MANAGER_EDIT_BOOKING_VIEW;
  }

}
