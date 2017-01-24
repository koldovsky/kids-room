package ua.softserveinc.tc.validator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

@Component("bookingValidator")
public class BookingValidatorImpl implements BookingValidator {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private InputDateValidatorImpl inputDateValidator;

    @Log
    private Logger log;

    private final List<String> errors = new ArrayList<>();

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public boolean isValidToInsert(List<BookingDto> dto) {
        boolean result = true;
        errors.clear();

        if (dto == null || dto.isEmpty() || hasNull(dto)) {
            errors.add(ValidationConstants.COMMON_ERROR_MESSAGE);

            result = false;
        } else {
            if (!hasCorrectData(dto)) {
                errors.add(ValidationConstants.COMMON_ERROR_MESSAGE);

                result = false;
            } else if (!inputDateValidator.validate(dto)) {
                errors.add(inputDateValidator.getErrors().get(0));

                result = false;
            } else if (hasDuplicateBooking(dto)) {
                errors.add(ValidationConstants.DUPLICATE_BOOKING_MESSAGE);

                result = false;
            } else if (!hasAvailablePlacesInTheRoom(dto)) {
                errors.add(ValidationConstants.NO_DAYS_FOR_BOOKING);

                result = false;
            }
        }

        return result;
    }

    @Override
    public boolean isValidToUpdate(List<BookingDto> listDto) {
        boolean result;
        errors.clear();

        if(!isValidToInsert(listDto)) {

            result = false;
        } else {
            BookingDto singleDto = listDto.get(0);
            Booking booking = null;
            try {
                booking = bookingService.findEntityById(singleDto.getId());
            } catch (ResourceNotFoundException e) {
                errors.add(ValidationConstants.COMMON_ERROR_MESSAGE);
                log.error("Not existed booking entity");
            }
            result = booking != null;
        }

        return result;
    }

    /*
     * Checks if the any of the BookingDto from the given list has null.
     *
     * @param dtoList the given list
     * @return true if any of the BookingDto has null, otherwise - false
     */
    private boolean hasNull(List<BookingDto> dtoList) {

        return dtoList.stream().anyMatch(dto -> {
            boolean result = false;
            if (dto.getUserId() == null || dto.getRoomId() == null
                    || dto.getKidId() == null || dto.getComment() == null
                    || dto.getStartTime() == null || dto.getEndTime() == null) {

                result = true;
            }

            return result;
        });
    }

    /*
     * Checks if all of the BookingDto from the given list has correct data.
     * The correctness of the data means that object contains correct id of room,
     * id of user and id of child.
     *
     * @param dtoList the given list
     * @return true if all of the BookingDto has correct data, otherwise - false
     */
    private boolean hasCorrectData(List<BookingDto> dtoList) {

        return bookingService.normalizeBookingDtoObjects(dtoList);
    }

    /*
     * Checks if any of the BookingDto objects forms a duplicate bookings.
     *
     * @param dtoList the given list
     * @return true if any of the BookingDto objects forms duplicate bookings,
     * otherwise - false
     */
    private boolean hasDuplicateBooking(List<BookingDto> dtoList) {

        return bookingService.hasDuplicateBookings(dtoList);
    }

    /*
     * Checks if there are available places in the room for given BookingDto.
     * Each object from given list must represent one particular child.
     *
     * @param dtoList the given list
     * @return true if there are available places in the room, otherwise - false
     */
    private boolean hasAvailablePlacesInTheRoom(List<BookingDto> dtoList) {
        BookingDto singleDto = dtoList.get(0);
        bookingService.normalizeBookingDtoObjects(dtoList);
        Date[] dates = new Date[] {singleDto.getDateStartTime(), singleDto.getDateEndTime()};
        Room room = singleDto.getRoom();
        Long idRecurrent = singleDto.getRecurrentId();
        Long idOfBooking = singleDto.getId();
        int numOfKids = dtoList.size();

        BookingsCharacteristics.Builder bookingBuilder =
                new BookingsCharacteristics.Builder()
                        .setDates(dates)
                        .setRooms(Collections.singletonList(room));

        if (idRecurrent != null) {
            bookingBuilder.setRecurrentIdsOfBookings(Collections.singletonList(idRecurrent));
        }

        if (idOfBooking != null) {
            bookingBuilder.setIdsOfBookings(Collections.singletonList(idOfBooking));
        }

        BookingsCharacteristics characteristic = bookingBuilder.build();

        return bookingService.hasAvailablePlacesInTheRoom(characteristic, numOfKids);
    }
}
