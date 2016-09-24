package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.repo.DayOffRepository;
import ua.softserveinc.tc.service.DayOffService;

import java.time.LocalDate;
import java.util.List;

@Service
public class DayOffServiceImpl implements DayOffService {

    @Autowired
    private DayOffRepository dayOffRepository;

    @Override
    public DayOff upsert(DayOff dayOff) {
        return dayOffRepository.saveAndFlush(dayOff);
    }

    @Override
    public DayOff findById(long id) {
        return dayOffRepository.findOne(id);
    }

    @Override
    public List<DayOff> findByNameOrStartDate(String name, LocalDate startDate) {
        return dayOffRepository.findByNameOrStartDate(name, startDate);
    }

    @Override
    public boolean dayOffExist(String name, LocalDate startDate) {
        if (findByNameOrStartDate(name, startDate).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void delete(long id) {
        dayOffRepository.delete(id);
    }

    @Override
    public List<DayOff> findAll() {
        return dayOffRepository.findAll();
    }

    @Override
    public List<DayOff> findByStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return dayOffRepository.findByStartDateBetween(startDate, endDate);
    }
}
