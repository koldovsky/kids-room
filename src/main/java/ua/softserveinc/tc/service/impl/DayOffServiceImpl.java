package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.repo.DayOffRepository;
import ua.softserveinc.tc.service.DayOffService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<DayOff> getClosestDays() {
        LocalDate today = LocalDate.now();

        return dayOffRepository.findAll().stream()
                .filter(day -> day.getStartDate().isAfter(today))
                .filter(day -> day.getStartDate().until(today).getDays() <= DayOfWeek.values().length)
                .collect(Collectors.toList());
    }

    @Override
    public List<DayOff> findByStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return dayOffRepository.findByStartDateBetween(startDate, endDate);
    }
}
