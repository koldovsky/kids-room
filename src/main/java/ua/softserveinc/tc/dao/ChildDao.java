package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

public interface ChildDao extends BaseDao<Child> {
    List<Child> getActiveChildrenInRoom(Room room);
}
