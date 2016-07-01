package ua.softserveinc.tc.service;

import java.util.List;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;

/**
 * Created by Nestor on 07.05.2016.
 */

public interface ChildService extends BaseService<Child> {

    List<Child> getActiveChildrenInRoom(Room room);

}
