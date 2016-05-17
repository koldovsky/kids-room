package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.VerificationToken;

import javax.persistence.Query;


/**
 * Created by Chak on 17.05.2016.
 */
@Repository
public class VerificationTokenDaoImpl extends BaseDaoImpl<VerificationToken> implements VerificationTokenDao {

    @Override
    public void delete(VerificationToken entity) {
        super.delete(getEntityManager().merge(entity));
    }

    @Override
    public VerificationToken findByUser(User user) {
        Query query = getEntityManager().createQuery("from VerificationToken where user.id = :user");
        query.setParameter("user", user.getId());
        return (VerificationToken) query.getSingleResult();
    }

    @Override
    public VerificationToken findByToken(String token) {
        Query query = getEntityManager().createQuery("from VerificationToken where token = :token");
        query.setParameter("token", token);
        return (VerificationToken) query.getSingleResult();
    }
}
