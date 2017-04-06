package ua.softserveinc.tc.service.impl;

import org.slf4j.Logger;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.*;
import ua.softserveinc.tc.util.*;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;
import ua.softserveinc.tc.constants.UtilConstants;

import java.time.*;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.dto.BookingDto.getRecurrentBookingDto;
import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService {

    @Log
    private Logger log;

    @Inject
    private RateService rateService;

    @Inject
    private RoomService roomService;

    @Inject
    private ChildService childService;

    @Inject
    private UserService userService;

    @Inject
    private BookingDao bookingDao;

    @Inject
    private UserDao userDao;

    @Inject
    private RoomDao roomDao;

    @Inject
    private RecurrentBookingValidator recurrentBookingValidator;

    @Inject
    private BookingValidator bookingValidator;

    @Inject
    private DayDiscountService dayDiscountService;

    @Override
    public void calculateAndSetDuration(Booking booking) {
        long difference = booking.getBookingEndTime().getTime()
                - booking.getBookingStartTime().getTime();
        booking.setDuration(difference);
    }

    @Override
    public void calculateAndSetSum(Booking booking) {
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
        calculateAndSetDuration(booking);
        calculateSumIncludingDiscounts(booking);

        return booking;
    }

    private void calculateSumIncludingDiscounts(Booking booking) {
        List<DayDiscountDTO> dayDiscountDTOS = dayDiscountService
                .getDayDiscountsForPeriod(dateToLocalDateTime(booking.getBookingStartTime()).toLocalDate(),
                        dateToLocalDateTime(booking.getBookingEndTime()).toLocalDate(),
                        dateToLocalDateTime(booking.getBookingStartTime()).toLocalTime(),
                        dateToLocalDateTime(booking.getBookingEndTime()).toLocalTime());

        if (dayDiscountDTOS.size() == 0) {
            calculateAndSetSum(booking);
        } else {
            List<Discount> outputDiscounts = new ArrayList<>();
            List<Discount> discounts = dayDiscountDTOS.stream().map(Discount::new).collect(Collectors.toList());

            LocalTime startPeriodTime = dateToLocalDateTime(booking.getBookingStartTime()).toLocalTime();
            LocalTime endPeriodTime = dateToLocalDateTime(booking.getBookingEndTime()).toLocalTime();
            Rate roomRate = booking.getRoom().getRates().get(0);
            booking.setSum(0L);
            while (startPeriodTime.isBefore(endPeriodTime)) {
                startPeriodTime = calculateSumForNextPeriod(booking, startPeriodTime, endPeriodTime,
                        discounts, roomRate, outputDiscounts);
            }

            booking.setDiscounts(outputDiscounts.stream().map(Discount::toString).collect(Collectors.joining("\n")));
        }
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private LocalTime calculateSumForNextPeriod(Booking booking, LocalTime startPeriodTime, LocalTime endBookingTime,
                                                List<Discount> list, Rate rate, List<Discount> outputDiscounts) {

        LocalTime minStartDiscount = getMinTime(list, Discount::getStartTime, startPeriodTime, endBookingTime);
        LocalTime minEndDiscount = getMinTime(list, Discount::getEndTime, startPeriodTime, endBookingTime);
        LocalTime endPeriodTime = minStartDiscount.isBefore(minEndDiscount)
                ? minStartDiscount : minEndDiscount;

        Discount discountWithMaxValue = list.stream()
                .filter(p -> p.containPeriod(startPeriodTime, endPeriodTime))
                .max(Comparator.comparingInt(Discount::getValue))
                .orElse(new Discount(0));

        if (outputDiscounts.size() > 0 &&
                outputDiscounts.get(outputDiscounts.size() - 1).equals(discountWithMaxValue)) {
            outputDiscounts.get(outputDiscounts.size() - 1).setEndTime(discountWithMaxValue.getEndTime());
        } else {
            outputDiscounts.add(new Discount(discountWithMaxValue.getReason(), discountWithMaxValue.getValue(),
                    startPeriodTime, endPeriodTime));
        }

        booking.setSum(booking.getSum() + calculateAndGetSum(startPeriodTime, endPeriodTime,
                discountWithMaxValue.getValue(), rate));

        return endPeriodTime;
    }

    private LocalTime getMinTime(List<Discount> list, Function<Discount, LocalTime> function,
                            LocalTime startPeriodTime, LocalTime endPeriodTime) {
        return list.stream().map(function)
                .filter(p -> p.isAfter(startPeriodTime) && !p.isAfter(endPeriodTime))
                .sorted(LocalTime::compareTo)
                .findFirst().orElse(endPeriodTime);
    }

    private long calculateAndGetSum(LocalTime startTime, LocalTime endTime, int discountValue, Rate rate) {
        long minutes = MINUTES.between(startTime, endTime);
        double minuteCostInCoins = rate.getPriceRate() * (100 - discountValue)
                / (rate.getHourRate() * 60 * 100.0);

        return (long) (minutes * minuteCostInCoins);
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
    public Long countByRoomAndBookingState(Room room, BookingState bookingState) {
        return bookingDao.countByRoomAndBookingState(room, bookingState);
    }

    @Override
    public List<Booking> findByBookingState(BookingState bookingState) {
        return bookingDao.findByBookingState(bookingState);
    }

    @Override
    public List<Booking> findByBookingStateAndBookingStartTimeLessThan(BookingState bookingState, Date start) {
        return bookingDao.findByBookingStateAndBookingStartTimeLessThan(bookingState, start);
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
    public List<Date[]> getDatesOfReservedBookings(Date startDate, Date endDate, Room room) {

        return bookingDao.getDatesOfReservedBookings(startDate, endDate, room);
    }

    @Override
    public List<Date[]> getDatesOfReservedBookings(BookingsCharacteristics characteristics) {

        return bookingDao.getDatesOfReservedBookings(characteristics);
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
    public boolean hasAvailablePlacesInTheRoom(BookingsCharacteristics characteristic,
                                               int numOfKids) {

        return getNotAvailablePlacesTimePeriods(characteristic, numOfKids, true).isEmpty();
    }

    @Override
    public List<Date[]> getAllNotAvailablePlacesTimePeriods(Room room) {
        Date today = Date.from(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        BookingsCharacteristics characteristic =
                new BookingsCharacteristics.Builder()
                        .setDates(new Date[] {today, DateConstants.MAX_DATE_FOR_CHECK})
                        .setRooms(Collections.singletonList(room))
                        .build();

        return getNotAvailablePlacesTimePeriods(characteristic, 1, false);
    }

    @Override
    public List<Date[]> getNotAvailablePlacesTimePeriods(BookingsCharacteristics characteristics,
                                                         int numOfKids,
                                                         boolean onlyStartOfFirstPeriod) {

        boolean onlyCheckForFreeSpaces = false;
        List<Date[]> resultList = new ArrayList<>();
        Room room = characteristics.getRooms().get(0);
        List<Date[]> datesOfReservedBookings =
                getDatesOfReservedBookings(characteristics);
        DailyBookingsMapTransformer dailyBookings =
                new DailyBookingsMapTransformer(datesOfReservedBookings);

        for (List<DateTwoTuple> tuples : dailyBookings) {
            int maxKidsInRoom = 0;
            boolean isFull = false;
            Date previousStartDateOfFullRoom = null;
            Date startDateOfFullRoom = null;
            Date endDateOfFullRoom = null;

            for (DateTwoTuple dateTuple : tuples) {
                if (dateTuple.isStart()) {
                    maxKidsInRoom++;
                } else {
                    maxKidsInRoom--;
                }

                if (maxKidsInRoom + numOfKids > room.getCapacity()) {
                    if (onlyStartOfFirstPeriod) {
                        startDateOfFullRoom = dateTuple.getDate();
                        resultList.add(new Date[] {startDateOfFullRoom, null});
                        onlyCheckForFreeSpaces = true;
                        break;
                    }
                    if (!isFull) {
                        startDateOfFullRoom = dateTuple.getDate();
                        isFull = true;
                    }
                } else if (isFull) {
                    if (!startDateOfFullRoom.equals(endDateOfFullRoom)) {
                        previousStartDateOfFullRoom = startDateOfFullRoom;
                        endDateOfFullRoom = dateTuple.getDate();
                        resultList.add(new Date[]{startDateOfFullRoom, endDateOfFullRoom});
                    } else {
                        endDateOfFullRoom = dateTuple.getDate();
                        resultList.remove(resultList.size() - 1);
                        resultList.add(new Date[]{previousStartDateOfFullRoom, endDateOfFullRoom});
                    }
                    isFull = false;
                }
            }
            if (onlyCheckForFreeSpaces) {
                break;
            }
        }

        if (datesOfReservedBookings.isEmpty() && numOfKids > room.getCapacity()) {
            resultList.add(characteristics.getDates());
        }

        return resultList;
    }

    @Override
    public List<BookingDto> persistBookingsFromDto(List<BookingDto> listDTO) {
        List<Booking> bookingsForPersisting = BookingDto.getListOfBookingObjects(listDTO);
        List<Booking> persistedBookings =
                bookingDao.persistRecurrentBookings(bookingsForPersisting);
        BookingDto.setIdToListOfBookingDto(listDTO, persistedBookings);

        return listDTO;
    }

    @Override
    @Transactional
    public BookingsHolder makeRecurrentBookings(List<BookingDto> bookingDtos) {
        BookingsHolder result;

        if (!recurrentBookingValidator.isValidToInsert(bookingDtos)) {
            result = new BookingsHolder(null, recurrentBookingValidator.getErrors().get(0));

        } else {
            List<BookingDto> bookings = saveRecurrentBookings(bookingDtos);

            if (bookings.isEmpty()) {
                result = new BookingsHolder(null, ValidationConstants.NO_DAYS_FOR_BOOKING);
            } else {
                result = new BookingsHolder(bookings, null);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public BookingsHolder updateBooking(BookingDto bookingDto) {
        BookingsHolder result;
        List<BookingDto> listOfDtoForUpdate = Collections.singletonList(bookingDto);
        Booking booking = null;

        if (bookingDto != null && bookingDto.getId() != null) {
            booking = bookingDao.findById(bookingDto.getId());
        }

        if (booking != null) {
            bookingDto.setFieldFromBookingIfNotExists(booking);
            bookingDto.setAllAbsentIdFromBooking(booking);

            if (!bookingValidator.isValidToUpdate(listOfDtoForUpdate)) {

                result = new BookingsHolder(null, bookingValidator.getErrors().get(0));

            } else {

                Date startTime = DateUtil.toDateISOFormat(bookingDto.getStartTime());
                Date endTime = DateUtil.toDateISOFormat(bookingDto.getEndTime());

                bookingDto.setDateStartTime(startTime);
                bookingDto.setDateEndTime(endTime);

                booking.setBookingStartTime(startTime);
                booking.setBookingEndTime(endTime);
                booking.setComment(bookingDto.getComment());
                booking.setRecurrentId(null);

                result = new BookingsHolder(Collections.singletonList(bookingDto), null);
            }
        } else {
            result = new BookingsHolder(null, ValidationConstants.COMMON_ERROR_MESSAGE);
        }

        return result;
    }

    @Override
    @Transactional
    public BookingsHolder updateRecurrentBookings(BookingDto bookingDto) {
        BookingsHolder result;
        List<Booking> cancelledBookings;
        List<BookingDto> listOfDtoForUpdate = Collections.singletonList(bookingDto);

        if (!recurrentBookingValidator.isValidToUpdate(listOfDtoForUpdate)) {
            result = new BookingsHolder(null, recurrentBookingValidator.getErrors().get(0));

        } else {
            cancelledBookings = cancelRecurrentBookings(listOfDtoForUpdate);
            List<BookingDto> bookings = saveRecurrentBookings(listOfDtoForUpdate);

            if (bookings.isEmpty()) {
                denyCancellationWithinTransaction(cancelledBookings);
                result = new BookingsHolder(null, ValidationConstants.COMMON_ERROR_MESSAGE);
            } else {
                result = new BookingsHolder(bookings, null);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public BookingsHolder makeBookings(List<BookingDto> bookingDtos) {
        BookingsHolder result;

        if (!bookingValidator.isValidToInsert(bookingDtos)) {
            result = new BookingsHolder(null, bookingValidator.getErrors().get(0));

        } else {
            List<BookingDto> bookings = saveBookings(bookingDtos);

            if (bookings.isEmpty()) {
                result = new BookingsHolder(null, ValidationConstants.COMMON_ERROR_MESSAGE);
            } else {
                result = new BookingsHolder(bookings, null);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public int cancelBookingsByRecurrentId(long recurrentId) {

        return bookingDao.cancelBookingsByRecurrentId(recurrentId);
    }

    @Override
    @Transactional
    public int cancelBookingById(long bookingId) {

        return bookingDao.cancelBookingById(bookingId);
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

        if (bookingForCancel != null) {
            bookingForCancel.forEach(dto -> dto.setBookingState(BookingState.CANCELLED));
        }

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

            int[] bookingDuration = getBookingDuration(dto); //index 0 = hours; index 1 = minutes
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
                    Date[] startAndEndDates = new Date[2]; //index 0 = startDate; index 1 = endDate
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
                .forEach(dates -> bookingDtos
                        .forEach(dto -> resultBookingsDto
                                .add(dto.getNewBookingDto(dates))));

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

    /*
     * The main reason of existing this class is resolving problem of finding periods
     * of times where there are no available places in the room. To create instance of this class
     * use constructor with parameter of list of arrays of dates. If given list is null then its
     * iterator behaves as follows: hasNext() will always returns false, and next() will always
     * returns empty list.
     *
     * After creating instance of this class we can get daily dates using iterator.
     * Each invoking next() will returns list of instances of DateTwoTuple that contains start and
     * end dates for next day and sorted in ascending order. The first object
     * in DateTuple is date and second indicates when date is start (is true) or end (is false).
     * So iterating through instance of this class we receives all dates from input parameter
     * grouped by one day.
     *
     * Class implements Iterable interface, so we can use it in forEach for loops.
     */
    private class DailyBookingsMapTransformer implements Iterable<List<DateTwoTuple>> {

        private final List<Date[]> listOfDates;

        /*
         * Iterator for iterating through dates. Each invoking next will return all dates
         * for next one day encapsulated in DateTwoTuple object. First encapsulated value
         * is date and second indicated if this date is start (true) or
         * end(false). For correct work start and end date must belong to the same day.
         */
        private class BookingsDatesIterator implements Iterator<List<DateTwoTuple>> {

            private int particularDay;
            private int particularYear;
            private ListIterator<Date[]> listOfDatesIterator;

            BookingsDatesIterator() {
                if (listOfDates != null && !listOfDates.isEmpty()) {
                    Object[] dates = listOfDates.get(0);
                    Date firstDateOfListOfDates = (Date)dates[0];
                    particularDay = DateUtil.getDayFromDate(firstDateOfListOfDates);
                    particularYear = DateUtil.getYearFromDate(firstDateOfListOfDates);
                    listOfDatesIterator = listOfDates.listIterator();
                } else {
                    listOfDatesIterator = Collections.emptyListIterator();
                }
            }

            /*
             * Returns list of DateTwoTuple objects that consists Date as
             * first parameter and Boolean value that indicate if this date is
             * start (true) or end (false) grouped by one day. For correct work
             * start and end date must belong to the same day.
             *
             * @return appropriate list of DateTwoTuple objects.
             */
            @Override
            public List<DateTwoTuple> next() {
                List<DateTwoTuple> resultList = new ArrayList<>();
                int nextDay;
                int nextYear;

                while (listOfDatesIterator.hasNext()) {
                    Object[] dates = listOfDatesIterator.next();

                    nextDay = DateUtil.getDayFromDate((Date)dates[0]);
                    nextYear = DateUtil.getYearFromDate((Date)dates[0]);

                    if (nextDay != particularDay || nextYear != particularYear) {
                        listOfDatesIterator.previous();
                        particularDay = nextDay;
                        particularYear = nextYear;
                        break;
                    }

                    resultList.add(new DateTwoTuple((Date)dates[0], true));
                    resultList.add(new DateTwoTuple((Date)dates[1], false));
                }
                Collections.sort(resultList);

                return resultList;
            }

            @Override
            public boolean hasNext() {

                return listOfDatesIterator != null && listOfDatesIterator.hasNext();
            }

        }

        /*
         * Constructor for create instance of this class.
         *
         * @param dates the given list of arrays of dates that we want grouped by days
         * and merged in one list.
         */
        DailyBookingsMapTransformer(List<Date[]> dates) {

            listOfDates = dates;
        }

        @Override
        public Iterator<List<DateTwoTuple>> iterator() {

            return new BookingsDatesIterator();
        }
    }
}
