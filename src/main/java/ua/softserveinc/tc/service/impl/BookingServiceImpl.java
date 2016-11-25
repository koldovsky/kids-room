package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.DateConstants;
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
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.BookingUtil;
import ua.softserveinc.tc.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.dto.BookingDto.getBookingDto;
import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

@Service
public class BookingServiceImpl extends BaseServiceImpl<Booking> implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private RateService rateService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ChildDao childDao;

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, BookingState... bookingStates) {
        return getBookings(startDate, endDate, null, null, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, BookingState... bookingStates) {
        return getBookings(startDate, endDate, user, null, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, Room room, BookingState... bookingStates) {
        return bookingDao.getBookings(startDate, endDate, room, bookingStates);
    }

    @Override
    public List<Booking> getBookings(Date startDate, Date endDate, User user, Room room, BookingState... bookingStates) {
        return bookingDao.getBookings(startDate, endDate, user, room, bookingStates);
    }

    @Override
    public void calculateAndSetDuration(Booking booking) {
        long difference = booking.getBookingEndTime().getTime() -
                booking.getBookingStartTime().getTime();

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

    public Boolean checkForDuplicateBooking(List<BookingDto> listDto) {

        for (BookingDto x : listDto) {
            if (checkForDuplicateBookingSingle(x)) return true;
        }
        return false;
    }

    public Boolean checkForDuplicateBookingSingle(BookingDto bookingDto) {
        User user = userDao.findById(bookingDto.getUserId());
        Room room = roomDao.findById(bookingDto.getRoomId());

        Boolean isDuplicate = bookingDao.getBookingsByUserAndRoom(user, room).stream()
                .filter(booking -> (booking.getRecurrentId() == null) || (!booking.getRecurrentId().equals(bookingDto.getRecurrentId())))
                .filter(booking ->
                        booking.getBookingEndTime().after(new Date()) &&
                                booking.getBookingState() != BookingState.CANCELLED)
                .map(booking -> BookingUtil.checkBookingTimeOverlap(bookingDto, booking))
                .filter(overlap -> overlap.equals(Boolean.TRUE))
                .findAny().orElse(false);
        return isDuplicate;
    }

    /**
     * The method finds out is there available space in
     * the rooms for given listDTO. The listDto must not
     * be null.
     *
     * @param listDTO list of BookingDto
     * @return true if there is available places in the room
     */
    private boolean isAvailabilePlacesInTheRoom(List<BookingDto> listDTO) {
        Objects.requireNonNull(listDTO, "listDTO must not be null");
        int availablePlaces = 0;
        int needPlaces = 1;
        Date theSameDay = null;
        for (BookingDto bdto : listDTO) {
            if (bdto.getDateStartTime().equals(theSameDay)) {
                needPlaces++;
                continue;
            } else if (theSameDay != null && availablePlaces < needPlaces)
                return false;
            needPlaces = 1;
            theSameDay = bdto.getDateStartTime();
            availablePlaces = roomService.getAvailableSpaceForPeriod(
                    bdto.getDateStartTime(),
                    bdto.getDateEndTime(),
                    bdto.getRoom());
        }
        return availablePlaces >= needPlaces;
    }

    @Override
    public List<BookingDto> persistBookingsFromDtoAndSetId(List<BookingDto> listDTO) {
        Objects.requireNonNull(listDTO, "listDTO must not be null");
        if (isAvailabilePlacesInTheRoom(listDTO)) {
            listDTO.forEach(bookingDTO -> {
                Booking booking = bookingDTO.getBookingObject();
                booking.setSum(0L);
                booking.setDuration(0L);
                bookingDao.create(booking);
                bookingDTO.setId(booking.getIdBook());
            });
            return listDTO;
        } else {
            return Collections.emptyList();
        }
    }

    public List<BookingDto> getAllBookingsByUserAndRoom(Long idUser, Long idRoom) {
        User user = userDao.findById(idUser);
        Room room = roomDao.findById(idRoom);
        return getBookings(null, null, user, room, BookingState.BOOKED)
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    public Long getMaxRecurrentId() {
        return bookingDao.getMaxRecurrentId();
    }


    public List<BookingDto> makeRecurrentBookings(List<BookingDto> bookingDtos) {
        /**
         * All recurrent bookings have the same date,
         * this method use date only from first element in list
         */

        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        String dateStart = bookingDtos.get(0).getStartTime();
        String dateEnd = bookingDtos.get(0).getEndTime();

        Date dateForRecurrentStart = DateUtil.toDateISOFormat(dateStart);
        Date dateForRecurrentEnd = DateUtil.toDateISOFormat(dateEnd);

        Map<String, Integer> daysOFWeek = new HashMap<>();
        daysOFWeek.put("Sun", Calendar.SUNDAY);
        daysOFWeek.put("Mon", Calendar.MONDAY);
        daysOFWeek.put("Tue", Calendar.TUESDAY);
        daysOFWeek.put("Wed", Calendar.WEDNESDAY);
        daysOFWeek.put("Thu", Calendar.THURSDAY);
        daysOFWeek.put("Fri", Calendar.FRIDAY);
        daysOFWeek.put("Sat", Calendar.SATURDAY);

        Calendar calendar1 = Calendar.getInstance();

        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForRecurrentEnd);

        Calendar calendarStartTime = Calendar.getInstance();
        calendarStartTime.setTime(dateForRecurrentStart);

        String[] days = bookingDtos.get(0).getDaysOfWeek().split(" ");
        Long newRecID = bookingDao.getMaxRecurrentId() + 1;

        /**
         * This code make Map for bookings (using children) and their new recurrent ID
         */
        Map<Long, Long> recurrentMap = new HashMap<>(bookingDtos.size());

        for (BookingDto bookingDto : bookingDtos) {
            recurrentMap.put(bookingDto.getKidId(), newRecID);
            newRecID++;
        }

        Room room = roomDao.findById(bookingDtos.get(0).getRoomId());

        List<BookingDto> newRecurrentBooking = new LinkedList<>();

        while (dateForRecurrentEnd.getTime() > calendarStartTime.getTimeInMillis()) {
            for (int i = 0; i < days.length; i++) {

                List<BookingDto> dailyBookings = new LinkedList<>();

                calendarStartTime.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(days[i]));

                if (dateForRecurrentEnd.getTime() < calendarStartTime.getTimeInMillis()) break;
                if (dateForRecurrentStart.getTime() > calendarStartTime.getTimeInMillis()) continue;


                for (int j = 0; j < bookingDtos.size(); j++) {
                    Booking booking = new Booking();

                    booking.setBookingStartTime(calendarStartTime.getTime());


                    calendar1.setTime(calendarStartTime.getTime());
                    calendar1.set(Calendar.HOUR_OF_DAY, calendarEndTime.get(Calendar.HOUR_OF_DAY));
                    calendar1.set(Calendar.MINUTE, calendarEndTime.get(Calendar.MINUTE));

                    booking.setBookingEndTime(calendar1.getTime());

                    booking.setRecurrentId(recurrentMap.get(bookingDtos.get(j).getKidId()));
                    booking.setRoom(room);
                    booking.setChild(childDao.findById(bookingDtos.get(j).getKidId()));
                    booking.setComment(bookingDtos.get(j).getComment());
                    booking.setDuration(new Long(0));

                    booking.setUser(userDao.findById(bookingDtos.get(j).getUserId()));

                    //FIXME: fix this 'buf'
                    BookingDto buf = booking.getDto();
                    buf.setRoom(room);
                    buf.setRoomId(room.getId());

                    buf.setDateStartTime(DateUtil.toDateISOFormat(DateUtil.toIsoString(calendarStartTime.getTime())));
                    buf.setDateEndTime(DateUtil.toDateISOFormat(DateUtil.toIsoString(calendar1.getTime())));

                    buf.setUser(userDao.findById(bookingDtos.get(j).getUserId()));
                    buf.setBookingState(BookingState.BOOKED);
                    buf.setChild(childDao.findById(buf.getIdChild()));
                    dailyBookings.add(buf);
                }
                newRecurrentBooking.addAll(dailyBookings);
            }
            calendarStartTime.add(Calendar.WEEK_OF_YEAR, 1);
            calendarStartTime.set(Calendar.DAY_OF_WEEK, daysOFWeek.get("Mon"));
        }
        if (newRecurrentBooking.isEmpty()) return newRecurrentBooking;
        return persistBookingsFromDtoAndSetId(newRecurrentBooking);
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
                if (checkForDuplicateBookingSingle(newBbooking)) {
                    throw new DuplicateBookingException();
                }
                newBbooking.setRecurrentId(newRecID);
            }
            iterationDayStartTime.add(Calendar.DAY_OF_MONTH, 1);
            iterationDayEndTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        return newRecurrentBookingDto;
    }

    @Override
    public BookingDto getRecurrentBookingForEditingById(final long bookingId) {
        final List<Booking> listOfRecurrentBooking = bookingDao.getRecurrentBookingsByRecurrentId(bookingId);
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
}
