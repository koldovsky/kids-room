package ua.softserveinc.tc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;


public interface TokenRepository extends JpaRepository <Token, Long>{

    Token findByToken(String token);

    Token findByUser(User user);
}
