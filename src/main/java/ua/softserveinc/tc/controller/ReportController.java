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
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static ua.softserveinc.tc.util.TimeUtil.*;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/manager-report", method = RequestMethod.GET)
    public ModelAndView report()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConst.REPORT_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = convertToString(currentDate());
        String dateThen = convertToString(dateMonthAgo());
        List<User> users = userService.getActiveUsersForRangeOfTime(dateThen, dateNow);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConst.ACTIVE_USERS, users);

        return model;
    }

    @RequestMapping(value = "/refreshParents/{startDate}/{endDate}", method = RequestMethod.GET)
    public @ResponseBody String refreshView(@PathVariable String startDate, @PathVariable String endDate)
    {
        List<User> users = userService.getActiveUsersForRangeOfTime(startDate, endDate);
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> userDTOs.add(new UserDTO(user)));
        Gson gson = new Gson();
        return gson.toJson(userDTOs);
    }
}