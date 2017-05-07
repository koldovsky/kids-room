package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Abonnement;
import java.util.List;
import java.util.Optional;

public interface AbonnementDao extends BaseDao<Abonnement> {

    void updateByActiveState(long id, boolean active);

    Optional<Long> getMaxPrice();

    Optional<Long> getMinPrice();
}
