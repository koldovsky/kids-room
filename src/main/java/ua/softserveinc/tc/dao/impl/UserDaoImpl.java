package ua.softserveinc.tc.dao.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.util.Log;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Log
    private static Logger log;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> findAllUsersByRole(Role role) {
        return entityManager
                .createQuery("from User where role = " + role.ordinal(), User.class)
                .getResultList();
    }

    @Override
    public void deleteUserById(Long id) {
        entityManager.createQuery("DELETE FROM User where id = " + id).executeUpdate();
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery(UserConstants.Entity.NQ_FIND_USER_BY_EMAIL, User.class);
            return
                    query.setParameter(UserConstants.Entity.EMAIL, email).getSingleResult();
        } catch (NoResultException e) {
            log.error("This email doesn't exists.", e);
            return null;
        }
    }

    @Override
    public User getUserByName(String firstName, String lastName) throws NonUniqueResultException {

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);

            Root<User> root = query.from(User.class);
            query.select(root).where(
                    builder.equal(root.get("firstName"), firstName),
                    builder.equal(root.get("lastName"), lastName));

            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            log.error("user" + firstName + " " + lastName + " doesnt exist");
            return null;
        } catch (NonUniqueResultException e) {
            log.error("multiple users with " + firstName + " and " + lastName + "  exist");
            throw e;
        }
    }

    @Override
    public List<User> findAll(List<Long> ids) {
        List<User> result = new ArrayList<>();

        for (Long elem : ids) {
            Query query = entityManager.createQuery("FROM User WHERE id = :id");
            query.setParameter("id", elem);
            result.add((User) query.getSingleResult());
        }
        return result;
    }

    @Override
    public List<User> findActiveUsers(Date startDate, Date endDate, Room room) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<Booking> root = query.from(Booking.class);

        query.select(root.get(BookingConstants.Entity.USER)).distinct(true).where(
                builder.between(root.get(
                        BookingConstants.Entity.START_TIME), startDate, endDate),
                builder.equal(root.get(BookingConstants.Entity.STATE), BookingState.COMPLETED),
                builder.equal(root.get(BookingConstants.Entity.ROOM), room));

        return entityManager.createQuery(query).getResultList();
    }
}
