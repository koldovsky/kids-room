package ua.softserveinc.tc.controller.manager.restful;

import static ua.softserveinc.tc.util.DateUtil.toDate;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

@RestController
@RequestMapping("/restful/manager-report/")
public class ManagerReportController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoomService roomService;

  /**
   * Receives start and end date of request and room id.Figures all users that have used room
   * during period between start date and end date. Send to the client list of users
   *
   * @param startDate start date of search
   * @param endDate end date of search
   * @param roomId id or room
   * @return JSON with relevant information
   */
  @GetMapping("{startDate}/{endDate}/{roomId}")
  public List<UserDto> refreshView(@PathVariable String startDate,
      @PathVariable String endDate,
      @PathVariable Long roomId) {

    Room room = roomService.findByIdTransactional(roomId);

    List<User> users = userService.getActiveUsers(toDate(startDate), toDate(endDate), room);
    //TODO-VL change UserDto to DTO builder
    return users.stream()
        .map(UserDto::new)
        .collect(Collectors.toList());
  }

}
