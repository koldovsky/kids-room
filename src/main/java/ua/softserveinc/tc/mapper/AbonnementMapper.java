package ua.softserveinc.tc.mapper;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.AbonnementDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class AbonnementMapper implements GenericMapper<Abonnement, AbonnementDto> {

    @Override
    public AbonnementDto toDto(Abonnement entity) {
        return null;
    }

    @Override
    public Abonnement toEntity(AbonnementDto dto) {
        return null;
    }

    public Abonnement setEntityFromDto(Abonnement entity, AbonnementDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setHour(dto.getHour());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());
        return entity;
    }
}
