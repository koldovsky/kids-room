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
import ua.softserveinc.tc.json.UserJSON;
import ua.softserveinc.tc.service.BookingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportPageController
{
    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView report()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConst.REPORT_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = bookingService.getCurrentDate();
        String dateThen = bookingService.getDateMonthAgo();
        List<User> users = bookingService.getActiveUsersForRangeOfTime(dateThen, dateNow);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConst.ACTIVE_USERS, users);

        return model;
    }

    @RequestMapping(value = "/refreshParents/{startDate}/{endDate}", method = RequestMethod.GET)
    public @ResponseBody String refreshView(@PathVariable String startDate, @PathVariable String endDate)
    {
        List<User> users = bookingService.getActiveUsersForRangeOfTime(startDate, endDate);
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> userDTOs.add(new UserDTO(user)));
        UserJSON userJSON = new UserJSON(userDTOs, startDate, endDate);
        Gson gson = new Gson();
        return gson.toJson(userJSON);
    }
}
