package ua.softserveinc.tc.controller.manager;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.*;
import ua.softserveinc.tc.constants.ManagerConstants.ManagerViewNames;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.dateMonthAgo;
import static ua.softserveinc.tc.util.DateUtil.dateNow;
import static ua.softserveinc.tc.util.DateUtil.getStringDate;

@Controller
@RequestMapping("/")
public class ManagerMappingController {

  @Autowired
  private UserService userService;

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
