package ua.softserveinc.tc.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.dao.*;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        return getBookings(startDate, endDate, null, room, bookingStates);
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
        User user = userDao.findById(listDto.get(0).getUserId());
        Room room = roomDao.findById(listDto.get(0).getRoomId());

        for (BookingDto x : listDto) {
            for (Booking y : bookingDao.getBookingsByUserAndRoom(user, room)) {

                if (y.getBookingEndTime().after(new Date()) && (y.getBookingState() != BookingState.CANCELLED)) {

                    if (!(!(DateUtil.toDateISOFormat(x.getEndTime()).after(y.getBookingStartTime()))
                            || !(DateUtil.toDateISOFormat(x.getStartTime()).before(y.getBookingEndTime())))
                            && x.getKidId().equals(y.getChild().getId())) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    @Override
    public List<BookingDto> persistBookingsFromDtoAndSetId(List<BookingDto> listDTO) {
        BookingDto bdto = listDTO.get(0);

        int available = roomService.getAvailableSpaceForPeriod(
                bdto.getDateStartTime(),
                bdto.getDateEndTime(),
                bdto.getRoom());

        if (available >= listDTO.size()) {
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


     /*   try{
            dateForRecurrentStart = sdf.parse(dateStart);
            dateForRecurrentEnd = sdf.parse(dateEnd);
        } catch (Exception e) {
            dateForRecurrentStart = null;
            dateForRecurrentEnd = null;
        }*/
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendarEndTime = Calendar.getInstance();
        calendarEndTime.setTime(dateForRecurrentEnd);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForRecurrentStart);

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

        while (dateForRecurrentEnd.getTime() > calendar.getTimeInMillis()) {
            for (int i = 0; i < days.length; i++) {

                List<BookingDto> dailyBookings = new LinkedList<>();

                calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get(days[i]));

                if (dateForRecurrentEnd.getTime() < calendar.getTimeInMillis()) break;
                if (dateForRecurrentStart.getTime() > calendar.getTimeInMillis()) continue;


                for (int j = 0; j < bookingDtos.size(); j++) {
                    Booking booking = new Booking();

                    booking.setBookingStartTime(calendar.getTime());


                    calendar1.setTime(calendar.getTime());
                    calendar1.set(Calendar.HOUR, calendarEndTime.get(Calendar.HOUR));
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

                    buf.setDateStartTime(DateUtil.toDateISOFormat(DateUtil.toIsoString(calendar.getTime())));
                    buf.setDateEndTime(DateUtil.toDateISOFormat(DateUtil.toIsoString(calendar1.getTime())));

                    buf.setUser(userDao.findById(bookingDtos.get(j).getUserId()));
                    buf.setBookingState(BookingState.BOOKED);
                    buf.setChild(childDao.findById(buf.getIdChild()));
                    dailyBookings.add(buf);
                }
                newRecurrentBooking.addAll(dailyBookings);
            }
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, daysOFWeek.get("Mon"));
        }
        return persistBookingsFromDtoAndSetId(newRecurrentBooking);
    }

    public BookingDto getRecurrentBookingForEditingById(long bookingId){

        BookingDto recurentBookToReturn = new BookingDto();
        List<Booking> listOfRecurrentBooking = new LinkedList<Booking>();
        listOfRecurrentBooking = bookingDao.getRecurrentBookingsByRecurrentId(bookingId);
        List<BookingDto> listOfRecurrentBookingDto = new LinkedList<BookingDto>();
        for(Booking booking: listOfRecurrentBooking){
            listOfRecurrentBookingDto.add(new BookingDto(booking));
        }
        listOfRecurrentBookingDto.sort(new Comparator<BookingDto>() {
            @Override
            public int compare(BookingDto o1, BookingDto o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        try {
            recurentBookToReturn.setDate(listOfRecurrentBookingDto.get(0).getDate());
            recurentBookToReturn.setEndDate(listOfRecurrentBookingDto.get(listOfRecurrentBookingDto.size()-1).getDate());;
            recurentBookToReturn.setStartTime(listOfRecurrentBookingDto.get(0).getStartTime());
            recurentBookToReturn.setEndTime(listOfRecurrentBookingDto.get(0).getEndTime());
            boolean bookedDaysOfWeek[] = {false,false,false,false,false,false};
            Calendar calendar = Calendar.getInstance();
            for (Booking booking : listOfRecurrentBooking) {
                calendar.setTime(booking.getBookingStartTime());
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                bookedDaysOfWeek[day-2]=true;
            }
            String nameOfDays[]=new String[] {"Mon","Tue","Wed","Thu","Fri","Sat"};
            String days="";
            for (int i=0; i < nameOfDays.length; i++){
                if (bookedDaysOfWeek[i]) days+=" "+nameOfDays[i];
            }
            recurentBookToReturn.setDaysOfWeek(days);
            recurentBookToReturn.setRecurrentId(bookingId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurentBookToReturn;
    };
}
