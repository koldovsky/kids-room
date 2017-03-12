package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.AbonnementDao;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.service.AbonnementsService;
import ua.softserveinc.tc.util.Log;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbonnementServiceImpl extends BaseServiceImpl<Abonnement> implements AbonnementsService {

    @Log
    private Logger log;

    @Autowired
    AbonnementDao abonnementDao;


    public List<AbonnementDto> findAllAbonements() {
        return abonnementDao.findAll()
                .stream()
                .map(AbonnementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AbonnementDto findAbonnement(long id) {
        return new AbonnementDto(abonnementDao.findById(id));
    }

    @Override
    public void updateAbonnement(AbonnementDto abonnementDto) {

        abonnementDao.update(abonnementDto.toEntity());
        System.out.println(abonnementDao);
    }
}
