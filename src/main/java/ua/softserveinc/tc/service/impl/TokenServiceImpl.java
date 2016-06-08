package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.TokenInvalidException;
import ua.softserveinc.tc.service.TokenService;

/**
 * Created by Chak on 17.05.2016.
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<Token> implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public void createToken(String sToken, User user) {
        Token token = new Token(sToken, user);
        tokenDao.create(token);
    }

    @Override
    public Token findByUser(User user) {
        return tokenDao.findByUser(user);
    }

    @Override
    public Token findByToken(String token) {
        Token result = tokenDao.findByToken(token);
        if (result == null) {
            throw new TokenInvalidException();
        }
        return result;
    }
}
