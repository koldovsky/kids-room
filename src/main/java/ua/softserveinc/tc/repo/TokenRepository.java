package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

/**
 * Created by Nestor on 11.06.2016.
 */

public interface TokenRepository extends JpaRepository <Token, Long>{

    Token findByToken(String token);

    Token findByUser(User user);
}
