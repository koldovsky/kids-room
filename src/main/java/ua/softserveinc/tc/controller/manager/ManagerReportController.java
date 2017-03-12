package ua.softserveinc.tc.controller.manager;

import static ua.softserveinc.tc.util.DateUtil.toDate;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

@RestController
public class ManagerReportController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoomService roomService;

  @GetMapping("/refreshParents/{startDate}/{endDate}/{roomId}")
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
