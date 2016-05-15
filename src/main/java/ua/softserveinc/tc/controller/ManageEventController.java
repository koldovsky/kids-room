package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.security.Principal;
import java.util.List;

/**
 * Created by dima- on 13.05.2016.
 */

@Controller
public class ManageEventController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomDao roomDao;

    @RequestMapping(value = "/managerRoom", method = RequestMethod.GET)
    public @ResponseBody
    String getRoomsForManager(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());

        List<Room> managersRooms = roomDao.findByManager(user);
        System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEEEEE:" + managersRooms);

        return managersRooms.toString();
    }
}
