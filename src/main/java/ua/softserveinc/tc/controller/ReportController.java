package ua.softserveinc.tc.controller;

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
import ua.softserveinc.tc.dto.UserDTO;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.DateUtil;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportController
{
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView report(Principal principal) {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConst.REPORT_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = dateUtil.getStringDate(dateUtil.currentDate());
        String dateThen = dateUtil.getStringDate(dateUtil.dateMonthAgo());
        Room room = roomService.getRoomByManager(userService.getUserByEmail(principal.getName()));

        List<User> users = userService.getActiveUsers(dateThen, dateNow, room);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConst.ACTIVE_USERS, users);

        return model;
    }

    @RequestMapping(value = "/refreshParents/{startDate}/{endDate}", method = RequestMethod.GET)
    public @ResponseBody String refreshView(@PathVariable String startDate,
                                            @PathVariable String endDate, Principal principal)
    {
        Room room = roomService.getRoomByManager(userService.getUserByEmail(principal.getName()));
        List<User> users = userService.getActiveUsers(startDate, endDate, room);
        Gson gson = new Gson();

        return gson.toJson(users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList()));
    }
}