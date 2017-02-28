package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
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

import static ua.softserveinc.tc.util.DateUtil.dateNow;
import static ua.softserveinc.tc.util.DateUtil.dateMonthAgo;
import static ua.softserveinc.tc.util.DateUtil.toDate;
import static ua.softserveinc.tc.util.DateUtil.getStringDate;


@Controller
public class ReportController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/refreshParents/{startDate}/{endDate}/{roomId}")
    @ResponseBody
    public String refreshView(@PathVariable String startDate,
                              @PathVariable String endDate,
                              @PathVariable Long roomId) {

        Room room = roomService.findByIdTransactional(roomId);

        List<User> users = userService.getActiveUsers(toDate(startDate), toDate(endDate), room);
        Gson gson = new Gson();

        return gson.toJson(users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList()));
    }
}