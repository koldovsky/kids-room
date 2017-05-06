package ua.softserveinc.tc.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.dao.SubscriptionAssignmentDao;
import ua.softserveinc.tc.dto.*;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.mapper.AbonnementMapper;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.PaginationCharacteristics;
import ua.softserveinc.tc.util.Log;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AbonnementServiceImpl extends BaseServiceImpl<Abonnement> implements AbonnementsService {

    @Log
    private Logger log;

    @Autowired
    AbonnementDao abonnementDao;

    @Autowired
    SubscriptionAssignmentDao subscriptionAssignmentDao;

    @Autowired
    AbonnementMapper abonnementMapper;

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DataTableOutput<AbonnementDto> paginationAbonnements(SortingPagination sortPaginate) {
        List<AbonnementDto> abonnementList = abonnementDao.findAll(sortPaginate)
                .stream()
                .map(abonnement -> modelMapper.map(abonnement, AbonnementDto.class))
                .collect(Collectors.toList());
        long rowCount = abonnementDao.getRowsCount(),
                start = sortPaginate.getPagination().getStart(),
                itemsPerPage = sortPaginate.getPagination().getItemsPerPage();
        long filterCount = (PaginationCharacteristics.searchCount == 0) ? rowCount : PaginationCharacteristics.searchCount;
        return new DataTableOutput<>(rowCount, abonnementList, filterCount);
    }

    @Override
    public List<AbonnementDto> findAllAbonements() {
        return abonnementDao.findAll()
                .stream()
                .map(abonnement -> modelMapper.map(abonnement, AbonnementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AbonnementDto findAbonnement(long id) {
        return modelMapper.map(abonnementDao.findById(id), AbonnementDto.class);
    }

    @Override
    public AbonnementDto updateAbonnement(AbonnementDto abonnementDto) {
        Abonnement abonnement = abonnementDao.findById(abonnementDto.getId());
        abonnement = abonnementMapper.setEntityFields(abonnement, abonnementDto);
        return modelMapper.map(abonnementDao.update(abonnement), AbonnementDto.class);
    }

    @Override
    public AbonnementDto createAbonnement(AbonnementDto abonnementDto) {
        Abonnement abonnement = modelMapper.map(abonnementDto, Abonnement.class);
        abonnementDao.create(abonnement);
        return abonnementDto;
    }

    @Override
    public void updateActiveState(AbonnementDto abonnementDto) {
        Abonnement abonnement = modelMapper.map(abonnementDto, Abonnement.class);
        abonnementDao.updateByActiveState(abonnement.getId(), abonnement.getIsActive());
    }

    @Override
    public void assignUserToAbonnement(UserAbonnementDto userAbonnementDto) {
        Abonnement abonnement = abonnementMapper.toEntity(findAbonnement(userAbonnementDto.getAbonnementId()));
        for (Long id : userAbonnementDto.getUserId()) {
            SubscriptionAssignment entity = new SubscriptionAssignment();
            User user = userService.findUserId(id);
            entity.setUser(user);
            entity.setAbonnement(abonnement);
            entity.setValid(true);
            subscriptionAssignmentDao.create(entity);
        }
    }

    @Override
    public List<SubscriptionAssignmentDto> findSubscriptionAssignmentByUserId(long userId) {

        subscriptionAssignmentDao.getAssignmentByUserId(userId);

        return null;
    }

    @Override
    public List<SubscriptionsUsedHoursDto> getAssignmentWithUsedHoursByUserId(long userId) {

        return subscriptionAssignmentDao.getAssignmentByUserId(userId);
    }

    @Override
    public DataTableOutput<UserAssigmentDto> findAllPurchasedAbonnements(SortingPagination sortPaginate) {
        //long rowCount = subscriptionAssignmentDao.getRowsCount();
        List<SubscriptionAssignment> assignments = subscriptionAssignmentDao.findAll(sortPaginate);
        //long filterCount = PaginationCharacteristics.searchCount;
        List<UserAssigmentDto> assigmentDtos = assignments.stream().map(UserAssigmentDto::new)
                .collect(Collectors.toList());

        return new DataTableOutput<>(subscriptionAssignmentDao.getRowsCount(),
                assigmentDtos, PaginationCharacteristics.searchCount);
    }
}
