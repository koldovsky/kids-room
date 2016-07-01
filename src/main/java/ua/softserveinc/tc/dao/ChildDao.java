package ua.softserveinc.tc.dao;

import java.util.List;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;

/**
 * Created by Demian on 30.04.2016.
 */
public interface ChildDao extends BaseDao<Child> {

    List<Child> getActiveChildrenInRoom(Room room);

}
