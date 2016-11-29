
/**
 * Created by Sviatoslav Hryb on 27-Nov-16.
 */

var dailyNotCompletedBookings;  //array containing bookings DTO

/**
 * Receives JSON containing bookings DTO with status ACTIVE and BOOKED
 * for one given day and given room. Information is evaluated to working
 * hours of the room. Information is stores in the global variable
 * dailyNotCompletedBookings
 */
function getNotCompletedBokings() {
    var time = $('#date-booking').val();
    var idRoom = localStorage["roomId"];
    var results;
    src = 'dailyNotCompletedBookings/' + time + '/' + idRoom;
    $.ajax({
        url: src,
        async: false,
        success: function (data) {
            results = data;
        },
        error: function() {
            $('#free-spaces').html(messages.notCorrect.server);
        }
    });
    dailyNotCompletedBookings = JSON.parse(results);
}

/**
 * The method finds the maximum people in the room for period of time
 * from dateLo to dateHi. All of the parameters must not be a null.
 *
 * @param dateLo start of period
 * @param dateHi end of period
 * @param bookings all reserved bookings in the time period
 * @return The maximum number of people that are simultaneously in the room
 */
function maxRangeReservedBookings(startTimeMillis, endTimeMillis, bookings) {
    var oneMinuteMillis = 60 * 1000;
    var maxReservedBookings = 0;
    var temporaryMax;
    var bok, ti, i;
    for (ti = startTimeMillis + 1; ti < endTimeMillis; ti += oneMinuteMillis) {
        temporaryMax = 0;
        for (i = 0; i < bookings.length; i++) {
            bok = bookings[i];
            if (bok.startTimeMillis < ti && bok.endTimeMillis > ti)
                temporaryMax++;
            if (temporaryMax > maxReservedBookings)
                maxReservedBookings = temporaryMax;
        }
    }
    return maxReservedBookings;
}

/**
 * The method finds the available space in the room (number of people)
 * for the given period of time from dateLo to dateHi.
 * All of the parameters must not be a null.
 *
 * @param dateLo start of period
 * @param dateHi end of period
 * @return number of places available in the room for the period
 */
function getAvailableSpaceForPeriod(startTimeMillis, endTimeMillis) {
    return roomCapacity - maxRangeReservedBookings(
            startTimeMillis, endTimeMillis, dailyNotCompletedBookings);
}

/**
 * Opens Dialog on edit-booking.jsp for making a booking.
 */
function openCreateBookingDialog() {
    var date = $('#date-booking').val();
    $('#bookingStartDate').val(date);
    $('#bookingDialog').dialog();
}

/**
 * Receives time for normalization to format HH:mm.
 * If received time is not in format /^(([01]\d|2[0-3])|(\d)):(([0-5]\d)|(\d))$/
 * the method throws alert with relative information and returns from method.
 * If time is x:yz, the method returns 0x:yz. If time is xy:z, the method returns
 * xy:0z. If time is x:y, the method returns 0x:0y.
 *
 * @param time for normaliation
 * @returns normalizated time
 */
function timeNormalize(time) {
    var regexpTime = /^(([01]\d|2[0-3])|(\d)):(([0-5]\d)|(\d))$/;
    var timeAr;
    if (!regexpTime.test(time)) {
        return null;
    }
    timeAr = time.split(":");
    if(timeAr[0].length < 2){
        timeAr[0] = 0 + timeAr[0];
    }
    if(timeAr[1].length < 2){
        timeAr[1] = 0 + timeAr[1];
    }
    return timeAr[0] + ":" + timeAr[1];
}

/**
 * Figures out available places in the room for set period of time
 * and show it in dialog window (id="bookingDialog") on edit-booking.jsp page.
 * The period of time gets from elements (id=bookingStartTimepicker and id=bookingEndTimepicker)
 * on aforementioned page.
 * If the time is not in corrected form the message with appropriate information
 * is shows in aforementioned dialog message.
 */
function getAndSetAvailablePlaces() {
    var startTimeMillis;
    var endTimeMillis;
    var availablePlaces;
    var date = $('#date-booking').val();
    var timeStart = timeNormalize($('#bookingStartTimepicker').val());
    var timeEnd = timeNormalize($('#bookingEndTimepicker').val());
    if (timeStart != null && timeEnd != null) {
        startTimeMillis = new Date(date + " " + timeStart + ":00").getTime();
        endTimeMillis = new Date(date + " " + timeEnd + ":00").getTime();
        availablePlaces = getAvailableSpaceForPeriod(startTimeMillis, endTimeMillis);
    } else {
        availablePlaces = messages.notCorrect.time;
    }
    $('#free-spaces').html(availablePlaces);
}

$().ready(function() {
    $("#btn-add-kid").click(function () {
        getNotCompletedBokings();
        getAndSetAvailablePlaces();
        openCreateBookingDialog();
    });

    $("#bookingStartTimepicker").change(function () {
        getAndSetAvailablePlaces();
    });

    $("#bookingEndTimepicker").change(function () {
        getAndSetAvailablePlaces();
    });
});
