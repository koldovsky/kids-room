package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.ChildService;

import java.util.List;

@Service
public class ChildServiceImpl extends BaseServiceImpl<Child> implements ChildService {

    @Autowired
    private ChildDao childDao;

    @Override
    public List<Child> getActiveChildrenInRoom(Room room) {
        return childDao.getActiveChildrenInRoom(room);
    }
}
