package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

public interface ChildService extends BaseService<Child> {

    List<Child> getActiveChildrenInRoom(Room room);

}
