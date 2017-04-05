package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.UserAbonnementDto;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.pagination.SortingPagination;

import java.util.List;

public interface AbonnementsService extends BaseService<Abonnement> {

    List<AbonnementDto> findAllAbonements();

    DataTableOutput<AbonnementDto> paginationAbonnements(SortingPagination pagination);

    AbonnementDto findAbonnement(long id);

    AbonnementDto updateAbonnement(AbonnementDto abonnementDto);

    void updateActiveState(AbonnementDto abonnementDto);

    AbonnementDto createAbonnement(AbonnementDto abonnementDto);

    void assignUserToAbonnement(UserAbonnementDto userAbonnementDto);
}
