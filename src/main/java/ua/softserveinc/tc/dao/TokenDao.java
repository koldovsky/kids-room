package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

public interface TokenDao extends BaseDao<Token> {
    Token findByUser(User user);

    Token findByToken(String token);

    void delete(Token entity);
}
