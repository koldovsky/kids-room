package ua.softserveinc.tc.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.dto.pagination.DataTableInput;
import ua.softserveinc.tc.dto.pagination.DataTableOutput;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.mapper.AbonnementMapper;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.util.AbonnementCharacteristics;
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
    public DataTableOutput<AbonnementDto> paginationAbonnements(DataTableInput input) {
        List<AbonnementDto> abonnementDtos = abonnementDao.getAbonnementsFromToLength(input.getStart(), input.getLength())
                .stream()
                .map(AbonnementDto::new)
                .collect(Collectors.toList());
        long abonnementsCount = abonnementDao.getRowsCount();
        AbonnementCharacteristics characteristics = new AbonnementCharacteristics();
        int page = characteristics.defineAbonnementsPage(input.getStart(), input.getLength(), abonnementsCount);
        DataTableOutput<AbonnementDto> tableOutput = new DataTableOutput<>(page, abonnementsCount,
                abonnementsCount, abonnementDtos);

        return tableOutput;
    }

    @Override
    public List<AbonnementDto> findAllAbonements() {
        return abonnementDao.findAll()
                .stream()
                .map(AbonnementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AbonnementDto findAbonnement(long id) {
        return modelMapper.map(abonnementDao.findById(id), AbonnementDto.class);
    }

    @Override
    public AbonnementDto updateAbonnement(AbonnementDto abonnementDto) {
        Abonnement abonnement = abonnementDao.findById(abonnementDto.getId());
        abonnement = abonnementMapper.setEntityFromDto(abonnement, abonnementDto);
        return new AbonnementDto(abonnementDao.update(abonnement));
    }

    @Override
    public AbonnementDto createAbonnement(AbonnementDto abonnementDto) {
        Abonnement abonnement = modelMapper.map(abonnementDto, Abonnement.class);
        abonnementDao.create(abonnement);
        return abonnementDto;
    }

    @Override
    public void updateActiveState(long id, boolean active) {
        abonnementDao.updateByActiveState(id, active);
    }
}
