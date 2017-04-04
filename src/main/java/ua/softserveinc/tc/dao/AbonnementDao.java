package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Abonnement;
import java.util.List;

public interface AbonnementDao extends BaseDao<Abonnement> {

    void updateByActiveState(long id, boolean active);
}
