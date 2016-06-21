package ua.softserveinc.tc.dao.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.Log;

import javax.persistence.NoResultException;
import javax.persistence.Query;


/**
 * Created by Chak on 17.05.2016.
 */
@Repository
public class TokenDaoImpl extends BaseDaoImpl<Token> implements TokenDao {

    @Log
    private static Logger log;

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
        try {
            Query query = getEntityManager().createQuery("from Token where token = :token");
            return (Token) query.setParameter(TokenConstants.TOKEN, token).getSingleResult();
        } catch (NoResultException e) {
            log.error("no exist", e);
            return null;

        }
    }
}