package ua.softserveinc.tc.dao.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class TokenDaoImpl extends BaseDaoImpl<Token> implements TokenDao {

    @Log
    private static Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(Token entity) {
        super.delete(entityManager.merge(entity));
    }

    @Override
    public Token findByUser(User user) {
        Query query = entityManager.createQuery(
                "from Token where user.id = :user");
        query.setParameter(UserConstants.Entity.USER, user.getId());
        return (Token) query.getSingleResult();
    }

    @Override
    public Token findByToken(String token) {
        TypedQuery<Token> query = entityManager.createQuery(
                "from Token where token = :token", Token.class);

        return query.setParameter(TokenConstants.TOKEN, token)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
