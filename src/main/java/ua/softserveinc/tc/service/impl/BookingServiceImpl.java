package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.constants.UtilConstants;
import ua.softserveinc.tc.util.TwoTuple;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.dto.BookingDto.getRecurrentBookingDto;
import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

/*
 * Rewritten by Sviatoslav Hryb on 05.10.2017
 */
@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService {

    @Log
    private Logger log;

    @Autowired
    private RateService rateService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RecurrentBookingValidator recurrentBookingValidator;

    @Autowired
    private BookingValidator bookingValidator;

    @Override
    public void calculateAndSetDuration(Booking booking) {
        long difference = booking.getBookingEndTime().getTime()
                - booking.getBookingStartTime().getTime();
        booking.setDuration(difference);
    }

    @Override
    public void calculateAndSetSum(Booking booking) {
        calculateAndSetDuration(booking);
        Long sum = rateService.calculateBookingCost(booking);
        booking.setSum(sum);
        booking.setBookingState(BookingState.COMPLETED);
        bookingDao.update(booking);
    }

    @Override
    public Long getSumTotal(List<Booking> bookings) {
        return bookings.stream()
                .mapToLong(Booking::getSum)
                .sum();
    }

    @Override
    public Map<User, Long> generateAReport(List<Booking> bookings) {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getUser,
                        Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Map<Room, Long> generateStatistics(List<Booking> bookings) {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getRoom,
                        Collectors.summingLong(Booking::getSum)));
    }

    @Override
    public Booking confirmBookingStartTime(BookingDto bookingDto) {
        Booking booking = findByIdTransactional(bookingDto.getId());
        Date date = replaceBookingTime(booking, bookingDto.getStartTime());
        booking.setBookingStartTime(date);
        resetSumAndDuration(booking);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDto bookingDto) {
        Booking booking = findByIdTransactional(bookingDto.getId());
        Date date = replaceBookingTime(booking, bookingDto.getEndTime());
        booking.setBookingEndTime(date);
        calculateAndSetSum(booking);
        return booking;
    }


    private void resetSumAndDuration(Booking booking) {
        booking.setDuration(0L);
        booking.setSum(0L);
        booking.setBookingState(BookingState.CALCULATE_SUM);
    }

    @Override
    public Date replaceBookingTime(Booking booking, String time) {
        DateFormat dfDate = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        String dateString = dfDate.format(booking.getBookingStartTime()) + " " + time;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDateAndTime(dateString));
        return calendar.getTime();
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getRecurrentBookingForEditingById(long bookingId) {
        BookingDto result = null;
        List<Booking> listOfRecurrentBooking =
                bookingDao.getRecurrentBookingsByRecurrentId(bookingId);

        if (!listOfRecurrentBooking.isEmpty()) {
            Set<Integer> weekDays = new HashSet<>();
            Calendar calendar = Calendar.getInstance();

            listOfRecurrentBooking.forEach(booking -> {
                calendar.setTime(booking.getBookingStartTime());
                weekDays.add(calendar.get(Calendar.DAY_OF_WEEK));
            });

            result = getRecurrentBookingDto(listOfRecurrentBooking, weekDays);
        }

        return result;

    }

    @Override
    public List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom) {
        User user = userDao.findById(idUser);
        Room room = roomDao.findById(idRoom);

        BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
                .setUsers(Collections.singletonList(user))
                .setRooms(Collections.singletonList(room))
                .setBookingsStates(Collections.singletonList(BookingState.BOOKED))
                .build();

        return getBookings(characteristic)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate,
                                                             Room room) {

        return getBookings(new Date[]{startDate, endDate}, room,
                BookingConstants.States.getActiveAndBooked());
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates) {
        BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
                .setDates(new Date[]{startDate, endDate})
                .setBookingsStates(Arrays.asList(bookingStates))
                .build();
        return getBookings(characteristic);
    }

    @Override
    public List<Booking> getBookings(Date[] dates, User user, BookingState... bookingStates) {
        BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
                .setDates(dates)
                .setUsers(Collections.singletonList(user))
                .setBookingsStates(Arrays.asList(bookingStates))
                .build();
        return getBookings(characteristic);
    }

    @Override
    public List<Booking> getBookings(Date[] dates, Room room, BookingState... bookingStates) {
        BookingsCharacteristics characteristic = new BookingsCharacteristics.Builder()
                .setDates(dates)
                .setRooms(Collections.singletonList(room))
                .setBookingsStates(Arrays.asList(bookingStates))
                .build();

        return getBookings(characteristic);
    }

    @Override
    public List<Booking> getBookings(BookingsCharacteristics characteristics) {

        return bookingDao.getBookings(characteristics);
    }

    @Override
    public boolean hasDuplicateBookings(List<BookingDto> listDto) {
        BookingDto singleDto = listDto.get(0);
        normalizeBookingDtoObjects(listDto);

        List<Child> children = new ArrayList<>();
        List<Long> idBookings = new ArrayList<>();
        List<Long> recurrentId = new ArrayList<>();

        listDto.forEach(dto -> {
            if (dto.getChild() != null) {
                children.add(dto.getChild());
            }
            if (dto.getId() != null) {
                idBookings.add(dto.getId());
            }
            if (dto.getRecurrentId() != null) {
                recurrentId.add(dto.getRecurrentId());
            }
        });
        List<Date[]> listDatesOfBooking = getDatesFromAnyBookingDto(singleDto);

        return listDatesOfBooking.stream().anyMatch(dates ->
                bookingDao.getDuplicateBookings(
                        new BookingsCharacteristics.Builder()
                                .setChildren(children)
                                .setDates(dates)
                                .setIdsOfBookings(idBookings)
                                .setRecurrentIdsOfBookings(recurrentId)
                                .build()
                ).size() > 0
        );
    }

    @Override
    public List<BookingDto> persistBookingsFromDto(List<BookingDto> listDTO) {
        List<BookingDto> resultDto = Collections.emptyList();

        if (hasAvailablePlacesInTheRoom(listDTO)) {
            List<Booking> bookingsForPersisting = BookingDto.getListOfBookingObjects(listDTO);
            List<Booking> persistedBookings =
                    bookingDao.persistRecurrentBookings(bookingsForPersisting);
            BookingDto.setIdToListOfBookingDto(listDTO, persistedBookings);
            resultDto = listDTO;
        }

        return resultDto;
    }

    @Override
    @Transactional
    public TwoTuple<List<BookingDto>, String> makeRecurrentBookings(List<BookingDto> bookingDtos) {
        TwoTuple<List<BookingDto>, String> result;

        if (!recurrentBookingValidator.isValidToInsert(bookingDtos)) {
            result = new TwoTuple<>(null, recurrentBookingValidator.getErrors().get(0));

        } else {
            List<BookingDto> bookings = saveRecurrentBookings(bookingDtos);

            if (bookings.isEmpty()) {
                result = new TwoTuple<>(null, ValidationConstants.NO_DAYS_FOR_BOOKING);
            } else {
                result = new TwoTuple<>(bookings, null);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public TwoTuple<List<BookingDto>, String> updateRecurrentBookings(BookingDto bookingDto) {
        TwoTuple<List<BookingDto>, String> result;
        List<Booking> cancelledBookings;
        List<BookingDto> listOfDtoForUpdate = Collections.singletonList(bookingDto);

        if (!recurrentBookingValidator.isValidToUpdate(listOfDtoForUpdate)) {
            result = new TwoTuple<>(null, recurrentBookingValidator.getErrors().get(0));

        } else {
            cancelledBookings = cancelRecurrentBookings(listOfDtoForUpdate);
            List<BookingDto> bookings = saveRecurrentBookings(listOfDtoForUpdate);

            if (bookings.isEmpty()) {
                denyCancellationWithinTransaction(cancelledBookings);
                result = new TwoTuple<>(null, ValidationConstants.NO_DAYS_FOR_BOOKING);
            } else {
                result = new TwoTuple<>(bookings, null);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public TwoTuple<List<BookingDto>, String> makeBookings(List<BookingDto> bookingDtos) {
        TwoTuple<List<BookingDto>, String> result;

        if (!bookingValidator.isValidToInsert(bookingDtos)) {
            result = new TwoTuple<>(null, bookingValidator.getErrors().get(0));

        } else {
            List<BookingDto> bookings = saveBookings(bookingDtos);

            if (bookings.isEmpty()) {
                result = new TwoTuple<>(null, ValidationConstants.NO_DAYS_FOR_BOOKING);
            } else {
                result = new TwoTuple<>(bookings, null);
            }
        }

        return result;
    }

    @Override
    public boolean normalizeBookingDtoObjects(List<BookingDto> dtoList) {
        boolean result = true;
        BookingDto singleDto = dtoList.get(0);

        try {
            Room room = (singleDto.getRoom() == null) ?
                    roomService.findEntityById(singleDto.getRoomId()) : singleDto.getRoom();
            User user = (singleDto.getUser() == null) ?
                    userService.findEntityById(singleDto.getUserId()) : singleDto.getUser();
            dtoList.forEach(dto -> {
                if (dto.getIdChild() == null) {
                    dto.setIdChild(dto.getKidId());
                }
                Date startTime = (dto.getDateStartTime() == null) ?
                        DateUtil.toDateISOFormat(dto.getStartTime()) : dto.getDateStartTime();
                Date endTime = (dto.getDateEndTime() == null) ?
                        DateUtil.toDateISOFormat(dto.getEndTime()) : dto.getDateEndTime();
                Child child = (dto.getChild() == null) ?
                        childService.findEntityById(dto.getIdChild()) : dto.getChild();
                dto.setUser(user);
                dto.setRoom(room);
                dto.setChild(child);
                dto.setDateStartTime(startTime);
                dto.setDateEndTime(endTime);
            });
        } catch (ResourceNotFoundException e) {
            result = e.getCause() != null && e.getCause().getClass() == ParseException.class;
        }

        return result;
    }

    /*
     * Checks if there is a duplicated booking for the given
     * object of BookingDto. The given object should not be null.
     *
     * @param dto the list of objects of BookingsDto
     * @return true if there is a duplicate bookings, otherwise
     * return false.
     */
    private boolean hasDuplicateBookings(BookingDto dto) {
        List<BookingDto> listOfBookingsDto = new ArrayList<>();
        listOfBookingsDto.add(dto);

        return hasDuplicateBookings(listOfBookingsDto);
    }

    /*
     * The method finds out is there available space in
     * the rooms for given listDTO. The listDto must not
     * be null.
     *
     * @param listDTO list of BookingDto
     * @return true if there is available places in the room
     */
    private boolean hasAvailablePlacesInTheRoom(List<BookingDto> listDTO) {
        int availablePlaces = 0;
        int needPlaces = 1;
        Date theSameDay = null;

        for (BookingDto bdto : listDTO) {
            if (bdto.getDateStartTime().equals(theSameDay)) {
                needPlaces++;
                continue;
            } else if (theSameDay != null && availablePlaces < needPlaces) {
                return false;
            }
            needPlaces = 1;
            theSameDay = bdto.getDateStartTime();
            availablePlaces = roomService.getAvailableSpaceForPeriod(
                    bdto.getDateStartTime(),
                    bdto.getDateEndTime(),
                    bdto.getRoom());
        }

        return availablePlaces >= needPlaces;
    }

    /*
     * Persist all recurrent Bookings that are represented by the objects of BookingDto.
     * Return the list of persistent objects of BookingDto
     *
     * @param bookingDtos the list of recurrent objects of BookingsDto
     * @return list persisted objects of BookingDto
     */
    private List<BookingDto> saveRecurrentBookings(List<BookingDto> bookingDtos) {
        setNewRecurrentIds(bookingDtos);
        List<BookingDto> listOfBookingDtos = prepareBookingDtoForPersisting(bookingDtos);

        return persistBookingsFromDto(listOfBookingDtos);
    }

    /*
     * Persist all Bookings that are represented by the objects of BookingDto.
     * Return the list of persistent objects of BookingDto
     *
     * @param bookingDtos the list of objects of BookingsDto
     * @return list persisted objects of BookingDto
     */
    private List<BookingDto> saveBookings(List<BookingDto> bookingDtos) {
        normalizeBookingDtoObjects(bookingDtos);
        bookingDtos.forEach(dto -> {
            dto.setBookingState(BookingState.BOOKED);
            dto.setSum(0L);
            dto.setDurationLong(dto.getDateEndTime().getTime() - dto.getDateStartTime().getTime());
            dto.setKidName(dto.getChild().getFullName());
        });

        return persistBookingsFromDto(bookingDtos);
    }

    /*
     * Cancels all the Bookings that are represented by the objects of BookingDto.
     * Return the list of cancelled objects of BookingDto
     *
     * @param bookingDtos the list of objects of BookingsDto
     * @return list of canceled objects of Booking
     */
    private List<Booking> cancelRecurrentBookings(List<BookingDto> bookingDtos) {
        Long recurrentId = bookingDtos.get(0).getRecurrentId();
        List<Booking> bookingForCancel =
                bookingDao.getRecurrentBookingsByRecurrentId(recurrentId);

        bookingForCancel.forEach(dto -> dto.setBookingState(BookingState.CANCELLED));

        return bookingForCancel;
    }

    /*
     * Constructs and returns the list of the arrays of the Date objects
     * from given BookingDto object. If the given object is null, then the
     * empty list will be returned. The array of Date object has length
     * of 2 and consists the start date of booking on index 0, and end date
     * of booking on index 1.
     *
     * @param dto the given BookingDto objects
     * @return the list of arrays of Date objects
     */
    private List<Date[]> getDatesFromAnyBookingDto(BookingDto dto) {
        List<Date[]> listOfBookings = new ArrayList<>();
        if (dto.getDaysOfWeek() == null) {
            Date[] startAndEndDates = new Date[2];
            startAndEndDates[0] = dto.getDateStartTime();
            startAndEndDates[1] = dto.getDateEndTime();
            listOfBookings.add(startAndEndDates);
        } else {
            listOfBookings = getDatesFromRecurrentBookingDto(dto);

        }

        return listOfBookings;
    }

    /**
     * get all active bookings from the room
     * @param room room with bookings
     * @return list of active bookings in the room
     */
    public List<BookingDto> getAllActiveBookingsInTheRoom(Room room) {
        return getBookings(null, room, BookingState.ACTIVE)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    /**
     * get all planned bookings from the room
     * @param room room with bookings
     * @return list of planned bookings in the room
     */
    public List<BookingDto> getAllPlannedBookingsInTheRoom(Room room) {
        return getBookings(null, room, BookingState.BOOKED)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    public void cancelAllActiveAndPlannedRoomBookings(Room room) {
        bookingDao.cancellActiveAndPlannedBookingsInRoom(room);
    }


    /*
     * Constructs and returns the list of the arrays of the Date objects
     * from given recurrent BookingDto object. If the given object is null,
     * then the empty list will be returned. The array of Date object has length
     * of 2 and consists the start date of booking on index 0, and end date
     * of booking on index 1.
     *
     * @param dto the given BookingDto objects
     * @return the list of arrays of Date objects
     */
    private List<Date[]> getDatesFromRecurrentBookingDto(BookingDto dto) {
        List<Date[]> resultDates = new ArrayList<>();

        if (dto != null) {
            Date dateRecurrentStart = DateUtil.toDateISOFormat(dto.getStartTime());
            Date dateRecurrentEnd = DateUtil.toDateISOFormat(dto.getEndTime());
            Calendar calendarAlternateTime = Calendar.getInstance();
            calendarAlternateTime.setTime(dateRecurrentStart);

            int[] bookingDuration = getBookingDuration(dto); // index 0 -> hours; index 1 -> minutes
            int[] intDaysOfWeek = DateUtil.getIntDaysOfWeek(
                    dto.getDaysOfWeek().trim().split(UtilConstants.WHITE_SPACE_REGEXP));

            while (calendarAlternateTime.getTimeInMillis() < dateRecurrentEnd.getTime()) {
                for (int dayOfWeek : intDaysOfWeek) {
                    calendarAlternateTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                    if (calendarAlternateTime.getTimeInMillis() > dateRecurrentEnd.getTime()) {
                        break;
                    }
                    if (calendarAlternateTime.getTimeInMillis() < dateRecurrentStart.getTime()) {
                        continue;
                    }
                    Date[] startAndEndDates = new Date[2];  //index 0 -> startDate; index 1 -> endDate
                    startAndEndDates[0] = calendarAlternateTime.getTime();
                    startAndEndDates[1] = getEndBookingDate(calendarAlternateTime, bookingDuration);
                    resultDates.add(startAndEndDates);
                }

                calendarAlternateTime.add(Calendar.WEEK_OF_YEAR, 1);
                calendarAlternateTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            }
        }

        return resultDates;
    }

    /*
     * Returns array that contains hours, minutes and seconds of duration of booking
     * from BookingDto.
     *
     * @param bDto BookingDto object. Must not be null.
     * @return array of duration.
     */
    private int[] getBookingDuration(BookingDto bDto) {
        String[] startTime = bDto.getStartTime()
                .split(DateConstants.T_DATE_SPLITTER)[1] //Times part
                .split(DateConstants.COLON_TIME_SPLITTER);
        String[] endTime = bDto.getEndTime()
                .split(DateConstants.T_DATE_SPLITTER)[1] //Times part
                .split(DateConstants.COLON_TIME_SPLITTER);
        int duration[] = new int[startTime.length];

        try {
            for (int i = 0; i < startTime.length; i++) {
                duration[i] = Integer.valueOf(endTime[i]) - Integer.valueOf(startTime[i]);
            }
        } catch (NumberFormatException e) {
            log.error("BookingDto has inappropriate state", e);
            duration = new int[0];
        }

        return duration;
    }

    /*
     * Return end date from giving start day and duration.
     *
     * @param startDate the start date. Must not be null
     * @param duration the duration of booking. Must not be null
     * @return the end date of booking
     */
    private Date getEndBookingDate(Calendar startDate, int[] duration) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(startDate.getTime());
        endDate.add(Calendar.HOUR, duration[0]);
        endDate.add(Calendar.MINUTE, duration[1]);

        return endDate.getTime();
    }

    /*
     * Creating series of BookingDto for persisting.
     *
     * @param given composite BookingDto
     * @return list of preparing BookingsDto
     */
    private List<BookingDto> prepareBookingDtoForPersisting(List<BookingDto> bookingDtos) {
        List<BookingDto> resultBookingsDto = new ArrayList<>();
        normalizeBookingDtoObjects(bookingDtos);

        getDatesFromRecurrentBookingDto(bookingDtos.get(0))
                .forEach(dates ->
                    bookingDtos.forEach(dto ->
                        resultBookingsDto.add(dto.getNewBookingDto(dates))));

        return resultBookingsDto;
    }

    /*
     * Set a new recurrent Id for each object of the list of the BookingDto
     * objects. The given list must not be null or empty. The recurrent Id
     * for each object must be the same and can be a null.
     *
     * @param bookingDtos the given list of BookingDto objects.
     */
    private void setNewRecurrentIds(List<BookingDto> bookingDtos) {
        Long maxRecurrentId = bookingDao.getMaxRecurrentId();

        for (BookingDto dto : bookingDtos) {
            dto.setRecurrentId(++maxRecurrentId);
        }
    }

    /*
     * Deny the cancellation of bookings within one transaction that are not yet
     * committed. The method set the state of bookings to BOOKED, so the method works
     * correct only if the previous state was BOOKED. If input parameter is null then
     * method returns without throwing any exceptions.
     *
     * @param bookingDtos the given list of BookingDto objects.
     */
    private void denyCancellationWithinTransaction(List<Booking> bookings) {
        if(bookings == null) {
            return;
        }
        bookings.forEach(booking -> booking.setBookingState(BookingState.BOOKED));
    }
}
