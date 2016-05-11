package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;

import java.util.List;

/**
 * Created by Nestor on 07.05.2016.
 */

@Service
public class ChildServiceImpl extends BaseServiceImpl<Child> implements ChildService {
    @Autowired
    private ChildDao childDao;

    @SuppressWarnings("unchecked")
    public List<Child> getChildrenByUser(User user){
        return childDao
                .getEntityManager()
                .createQuery("from Child" +
                        " where parentId = " + user.getId())
                .getResultList();
    }

    //для чого це тут? в базовому севісі є метод findAll()
    @Override
    public List<Child> getAllChildren() {
        return childDao
                .getEntityManager()
                .createQuery("from Child")
                .getResultList();
    }

  /*  @Override
    public List<Child> getBookedChildren() {
        return childDao
                .getEntityManager()
                .createQuery("from Booking" + " where " + BOOKING_START_TIME + " = " + new Date())
                .getResultList();
    }*/
}
