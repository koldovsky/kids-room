package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;

import static ua.softserveinc.tc.util.TimeUtil.*;

/**
 * Created by Demian on 28.05.2016.
 */
@Controller
public class StatisticsController
{
    @Autowired
    RoomService roomService;

    @RequestMapping(value = "/adm-statistics", method = RequestMethod.GET)
    public ModelAndView report()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("adm-statistics");
        ModelMap modelMap = model.getModelMap();

        String dateNow = convertToString(currentDate());
        String dateThen = convertToString(dateMonthAgo());
        List<Room> rooms = roomService.findAll();

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute("rooms", rooms);

        return model;
    }
}