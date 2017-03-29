package ua.softserveinc.tc.validator;

import org.springframework.validation.Validator;
import ua.softserveinc.tc.entity.Room;

import java.util.List;
import java.util.Locale;


public interface RoomValidator extends Validator {
    List<String> checkRoomBookings(Room room, Locale locale);

    boolean haveDuplicateRates(Room room);
}
