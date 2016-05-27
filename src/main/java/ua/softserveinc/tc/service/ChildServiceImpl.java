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
    //TODO: Адміну дати можливість конфіжити ці поля
    private static int MIN_AGE = 3;
    private static int MAX_AGE = 8;

    public static int getMinAge() {
        return MIN_AGE;
    }

    public static void setMinAge(int minAge) {
        MIN_AGE = minAge;
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }

    public static void setMaxAge(int maxAge) {
        MAX_AGE = maxAge;
    }

    @Autowired
    private ChildDao childDao;

    @SuppressWarnings("unchecked")
    public List<Child> getChildrenByUser(User user){
        List<Child> resultList = childDao.getEntityManager().createQuery("from Child" +
                        " where parentId = " + user.getId()).getResultList();
        return resultList;
    }

    //для чого це тут? в базовому севісі є метод findAll()
    @Override
    @SuppressWarnings("unchecked")
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
