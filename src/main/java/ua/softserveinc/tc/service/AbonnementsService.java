package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.Abonnement;

import java.util.List;

public interface AbonnementsService extends BaseService<Abonnement> {

    List<AbonnementDto> findAllAbonements();

    AbonnementDto findAbonnement(long id);

    void updateAbonnement(AbonnementDto abonnementDto);
}
