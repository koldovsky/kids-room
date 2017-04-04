package ua.softserveinc.tc.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.mapper.AbonnementMapper;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.util.PaginationCharacteristics;
import ua.softserveinc.tc.util.Log;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbonnementServiceImpl extends BaseServiceImpl<Abonnement> implements AbonnementsService {

    @Log
    private Logger log;

    @Autowired
    AbonnementDao abonnementDao;

    @Autowired
    AbonnementMapper abonnementMapper;

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
        long currentPage = PaginationCharacteristics.definePage(start, itemsPerPage, rowCount);
        long filterCount = (PaginationCharacteristics.searchCount == 0) ? rowCount : PaginationCharacteristics.searchCount;

        return new DataTableOutput<>(currentPage, rowCount, filterCount, abonnementList);
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
}
