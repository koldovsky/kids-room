package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.entity.Abonnement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AbonnementDaoImpl extends BaseDaoImpl<Abonnement> implements AbonnementDao {

    @PersistenceContext
    private EntityManager entityManager;
}
