package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.*;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView report(Principal principal) {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConstants.REPORT_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = getStringDate(dateNow());
        String dateThen = getStringDate(dateMonthAgo());
        Room room = roomService.getRoomByManager(userService.getUserByEmail(principal.getName()));
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> rooms = roomService.findByManger(currentManager);

        List<User> users = userService.getActiveUsers(toDate(dateMonthAgo()), toDate(dateNow()), room);

        modelMap.addAttribute(ReportConstants.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConstants.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConstants.ACTIVE_USERS, users);
        modelMap.addAttribute("rooms", rooms);

        return model;
    }

    @RequestMapping(value = "/refreshParents/{roomId}/{startDate}/{endDate}", method = RequestMethod.GET)
    @ResponseBody
    public String refreshView(@PathVariable Long roomId,
                              @PathVariable String startDate,
                              @PathVariable String endDate) {

        Room room = roomService.findById(roomId);

        List<User> users = userService.getActiveUsers(toDate(startDate), toDate(endDate), room);
        Gson gson = new Gson();

        return gson.toJson(users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList()));
    }
}