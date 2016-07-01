package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.ChildService;

/**
 * Created by Nestor on 07.05.2016.
 */

@Service
public class ChildServiceImpl extends BaseServiceImpl<Child> implements ChildService {

    @Autowired
    private ChildDao childDao;

    @Override
    public List<Child> getActiveChildrenInRoom(Room room) {
        return childDao.getActiveChildrenInRoom(room);
    }

}
