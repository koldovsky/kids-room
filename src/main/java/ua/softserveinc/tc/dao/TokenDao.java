package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

/**
 * Created by Chak on 17.05.2016.
 */
public interface TokenDao extends BaseDao<Token> {
    Token findByUser(User user);

    Token findByToken(String token);

    public void delete(Token entity);
}
