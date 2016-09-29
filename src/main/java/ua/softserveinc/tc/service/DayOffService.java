package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.DayOff;

import java.time.LocalDate;
import java.util.List;

public interface DayOffService {

    DayOff upsert(DayOff dayOff);

    DayOff findById(long id);

    boolean dayOffExist(String name, LocalDate startDate);

    void delete(long id);

    List<DayOff> findAll();

    List<DayOff> getClosestDays();

    List<DayOff> findByNameOrStartDate(String name, LocalDate startDate);

    List<DayOff> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

}
