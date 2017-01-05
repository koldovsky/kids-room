package ua.softserveinc.tc.service.impl;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.LocaleConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.DuplicateBookingException;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.constants.UtilConstants;

import javax.servlet.http.HttpServletRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.dto.BookingDto.getBookingDto;
import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

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
    private ChildDao childDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RecurrentBookingValidator recurrentBookingValidator;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<Booking> getNotCompletedAndCancelledBookings(Date startDate, Date endDate,
                                                             Room room) {

        return bookingDao.getNotCompletedAndCancelledBookings(startDate, endDate, room);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates) {
        return getBookings(startDate, endDate, null, null, true, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user,
                                     BookingState... bookingStates) {

        return getBookings(startDate, endDate, user, null, true, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, Room room, boolean includeLastDay,
                                     BookingState... bookingStates) {

        return getBookings(startDate, endDate, null, room, includeLastDay, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, Room room,
                                     boolean includeDay, BookingState... bookingStates) {

        return bookingDao.getBookings(startDate, endDate, user, room, includeDay, bookingStates);
    }

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
        Booking booking = findById(bookingDto.getId());
        Date date = replaceBookingTime(booking, bookingDto.getStartTime());
        booking.setBookingStartTime(date);
        resetSumAndDuration(booking);
        return booking;
    }

    @Override
    public Booking confirmBookingEndTime(BookingDto bookingDto) {
        Booking booking = findById(bookingDto.getId());
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
    public BookingDto getRecurrentBookingForEditingById(final long bookingId) {
        final List<Booking> listOfRecurrentBooking =
                bookingDao.getRecurrentBookingsByRecurrentId(bookingId);
        Set<Integer> weekDays = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        for (Booking booking : listOfRecurrentBooking) {
            calendar.setTime(booking.getBookingStartTime());
            weekDays.add(calendar.get(Calendar.DAY_OF_WEEK));
        }
        return getBookingDto(listOfRecurrentBooking, weekDays);

    }

    @Override
    public List<BookingDto> updateRecurrentBookings(BookingDto recurrentBookingDto) {
        Long recurrentId = recurrentBookingDto.getRecurrentId();
        List<Booking> recurrentBookingForDelete = bookingDao.getRecurrentBookingsByRecurrentId(recurrentId);
        for (Booking bdto : recurrentBookingForDelete) {
            bdto.setBookingState(BookingState.CANCELLED);
        }

        List<BookingDto> listOfRecurrentBooking = recurrentDtoToList(recurrentBookingDto);
        if (listOfRecurrentBooking.isEmpty()) {
            return listOfRecurrentBooking;
        }

        List<Booking> recurrentBookingForCreate = new ArrayList<>();
        for (BookingDto bdto : listOfRecurrentBooking) {
            recurrentBookingForCreate.add(bdto.getBookingObject());
        }

        bookingDao.updateRecurrentBookingsDAO(recurrentBookingForDelete, recurrentBookingForCreate);
        final ArrayList<BookingDto> recurrentBookings = new ArrayList<>();
        recurrentBookingForCreate.forEach(b -> recurrentBookings.add(new BookingDto(b)));

        return recurrentBookings;
    }

    @Override
    public List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom) {
        User user = userDao.findById(idUser);
        Room room = roomDao.findById(idRoom);

        return getBookings(null, null, user, room, true, BookingState.BOOKED)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
                                .setChildrenListOfBookings(children)
                                .setDatesOfBookings(dates)
                                .setListOfIdOfBookings(idBookings)
                                .setListOfIdOfRecurrentBookings(recurrentId)
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
            BookingDto.setIdToListOfBookingDto(resultDto, persistedBookings);
            resultDto = listDTO;
        }

        return resultDto;
    }

    @Override
    @Transactional
    public ResponseEntity<String> makeRecurrentBookings(List<BookingDto> bookingDtos) {
        ResponseEntity<String> resultResponse;

        if (!recurrentBookingValidator.validate(bookingDtos)) {
            resultResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(recurrentBookingValidator.getErrors().get(0));

        } else {
            List<BookingDto> bookings = saveRecurrentBookings(bookingDtos);

            if (bookings.isEmpty()) {
                Locale locale = (Locale) request.getSession()
                        .getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);

                if (locale == null) {
                    locale = request.getLocale();
                }
                resultResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource
                        .getMessage(ValidationConstants.NO_DAYS_FOR_BOOKING, null, locale));
            } else {
                resultResponse = ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(bookings));
            }
        }

        return resultResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean normalizeBookingDtoObjects(List<BookingDto> dtoList) {
        boolean result = true;
        BookingDto singleDto = dtoList.get(0);

        try {
            Room room = (singleDto.getRoom() == null) ?
                    roomService.findById(singleDto.getRoomId()) : singleDto.getRoom();
            User user = (singleDto.getUser() == null) ?
                    userService.findById(singleDto.getUserId()) : singleDto.getUser();
            dtoList.forEach(dto -> {
                if (dto.getIdChild() == null) {
                    dto.setIdChild(dto.getKidId());
                }
                Date startTime = (dto.getDateStartTime() == null) ?
                        DateUtil.toDateISOFormat(dto.getStartTime()) : dto.getDateStartTime();
                Date endTime = (dto.getDateEndTime() == null) ?
                        DateUtil.toDateISOFormat(dto.getEndTime()) : dto.getDateEndTime();
                Child child = (dto.getChild() == null) ?
                        childService.findById(dto.getIdChild()) : dto.getChild();
                dto.setUser(user);
                dto.setRoom(room);
                dto.setChild(child);
                dto.setDateStartTime(startTime);
                dto.setDateEndTime(endTime);
            });
        } catch (ResourceNotFoundException e) {
            result = false;
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
    @Transactional(readOnly = true)
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
    @Transactional
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
     * Persist all Bookings that are represented by the objects of BookingDto.
     * Return the list of persistent objects of BookingDto
     *
     * @param bookingDtos the list of objects of BookingsDto
     * @return list persisted objects of BookingDto
     */
    @Transactional
    private List<BookingDto> saveRecurrentBookings(List<BookingDto> bookingDtos) {
        setRecurrentIds(bookingDtos);
        List<BookingDto> listOfBookingDtos = prepareBookingDtoForPersisting(bookingDtos);

        return persistBookingsFromDto(listOfBookingDtos);
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

        getDatesFromRecurrentBookingDto(bookingDtos.get(0)).forEach(dates ->
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
    @Transactional
    private void setRecurrentIds(List<BookingDto> bookingDtos) {
        Long maxRecurrentId = bookingDtos.get(0).getRecurrentId();

        if (maxRecurrentId == null) {
            maxRecurrentId = bookingDao.getMaxRecurrentId();
            for (BookingDto dto : bookingDtos) {
                dto.setRecurrentId(++maxRecurrentId);
            }
        }
    }


    private List<BookingDto> recurrentDtoToList(BookingDto recurrentBookingDto) {
        String dateStart = recurrentBookingDto.getStartTime();
        String dateEnd = recurrentBookingDto.getEndTime();
        Date dateForRecurrentStart = DateUtil.toDateISOFormat(dateStart);
        Date dateForRecurrentEnd = DateUtil.toDateISOFormat(dateEnd);

        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForRecurrentEnd);

        Calendar calendarStartTime = Calendar.getInstance();
        calendarStartTime.setTime(dateForRecurrentStart);

        Long newRecID = bookingDao.getMaxRecurrentId() + 1;
        Room room = roomDao.findById(recurrentBookingDto.getRoomId());
        recurrentBookingDto.setRoom(room);
        recurrentBookingDto.setChild(childDao.findById(recurrentBookingDto.getKidId()));
        recurrentBookingDto.setUser(userDao.findById(recurrentBookingDto.getUserId()));

        List<BookingDto> newRecurrentBookingDto = new ArrayList<>();

        Calendar iterationDayStartTime = calendarStartTime;
        Calendar iterationDayEndTime = (Calendar) iterationDayStartTime.clone();

        iterationDayEndTime.set(Calendar.HOUR_OF_DAY, calendarEndTime.get(Calendar.HOUR_OF_DAY));
        iterationDayEndTime.set(Calendar.MINUTE, calendarEndTime.get(Calendar.MINUTE));
        calendarEndTime.add(Calendar.DAY_OF_MONTH, 1);
        while (iterationDayEndTime.before(calendarEndTime)) {
            if (recurrentBookingDto.getWeekDays().contains(iterationDayEndTime.get(Calendar.DAY_OF_WEEK))) {
                BookingDto newBbooking = new BookingDto(recurrentBookingDto);
                newBbooking.setDateStartTime(iterationDayStartTime.getTime());
                newBbooking.setDateEndTime(iterationDayEndTime.getTime());
                newBbooking.setBookingState(BookingState.BOOKED);
                newBbooking.setKidName(newBbooking.getChild().getFullName());
                newBbooking.setRoomName(newBbooking.getRoom().getAddress());
                newBbooking.setIdChild(newBbooking.getChild().getId());
                newRecurrentBookingDto.add(newBbooking);
                if (hasDuplicateBookings(newBbooking)) {
                    throw new DuplicateBookingException();
                }
                newBbooking.setRecurrentId(newRecID);
            }
            iterationDayStartTime.add(Calendar.DAY_OF_MONTH, 1);
            iterationDayEndTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        return newRecurrentBookingDto;
    }
}
