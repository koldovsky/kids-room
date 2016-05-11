package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;
import ua.softserveinc.tc.entity.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao
{

    @Override
    public User getUserByEmail(String email) {
        try {
            TypedQuery<User> query = getEntityManager().createNamedQuery(UserConst.NQ_FIND_USER_BY_EMAIL, User.class);
            return query.setParameter(UserConst.EMAIL, email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
