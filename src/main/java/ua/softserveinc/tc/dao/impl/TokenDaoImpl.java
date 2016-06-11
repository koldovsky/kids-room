package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

import javax.persistence.Query;


/**
 * Created by Chak on 17.05.2016.
 */
@Repository
public class TokenDaoImpl extends BaseDaoImpl<Token> implements TokenDao {

    @Override
    public void delete(Token entity) {
        super.delete(getEntityManager().merge(entity));
    }

    @Override
    public Token findByUser(User user) {
        Query query = getEntityManager().createQuery("from Token where user.id = :user");
        query.setParameter(UserConstants.Entity.USER, user.getId());
        return (Token) query.getSingleResult();
    }

    @Override
    public Token findByToken(String token) {
        Query query = getEntityManager().createQuery("from Token where token = :token");
        query.setParameter(TokenConstants.TOKEN, token);
        return (Token) query.getSingleResult();
    }
}
