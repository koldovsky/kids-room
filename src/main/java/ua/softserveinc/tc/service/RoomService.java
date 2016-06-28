package ua.softserveinc.tc.service;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.PeriodDto;
import ua.softserveinc.tc.entity.Room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public interface RoomService extends BaseService<Room> {

    void saveOrUpdate(Room room);

    List<PeriodDto> getBlockedPeriods(Room room, Calendar start, Calendar end);

    Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room);

    Boolean isPossibleUpdate(BookingDto bookingDto);

    Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room);
}
