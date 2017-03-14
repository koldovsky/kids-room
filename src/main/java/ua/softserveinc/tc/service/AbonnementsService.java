package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.pagination.DataTableInput;
import ua.softserveinc.tc.dto.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.Abonnement;

import java.util.List;

public interface AbonnementsService extends BaseService<Abonnement> {

    List<AbonnementDto> findAllAbonements();

    DataTableOutput<AbonnementDto> paginationAbonnements(DataTableInput input);

    AbonnementDto findAbonnement(long id);

    AbonnementDto updateAbonnement(AbonnementDto abonnementDto);

    void updateActiveState(long id, boolean active);

    AbonnementDto createAbonnement(AbonnementDto abonnementDto);
}
