package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.*;
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

    void assignUserToAbonnement(UsersAbonnementAssignmentDto usersAbonnementAssignmentDto);

    List<SubscriptionAssignmentDto> findSubscriptionAssignmentByUserId(long userId);

    List<SubscriptionsUsedHoursDto> getAssignmentWithUsedHoursByUserId(long userId);

    DataTableOutput<UserAssigmentDto> findAllPurchasedAbonnements(SortingPagination sortPaginate);

    long getMaxAbonnementsPrice();

    long getMinAbonnementsPrice();

    List<UserAbonnementInfoDto> getAbonnementInfoByUserId(long userId);

    void sendNotificationToAssignAbonnement(UserAbonnementsDto dto);
}
