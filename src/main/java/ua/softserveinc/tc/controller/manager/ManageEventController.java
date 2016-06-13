package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;

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
    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/efefe", method = RequestMethod.GET)
    public ModelAndView getRoomsForManager(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());

        List<Room> managersRooms = user.getRooms();
        System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEEEEE:" + managersRooms);

        ModelAndView model = new ModelAndView();
        model.setViewName(EventConstants.View.MAIN_PAGE);

        model.getModelMap()
                .addAttribute("managersRoom", managersRooms);

        return model;

    }
}
