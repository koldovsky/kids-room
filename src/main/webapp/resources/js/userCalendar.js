/**
 * Created by dima- on 12.05.2016.
 */
var info;
var bookingsArray;
var bookingDate;
var roomIdForHandler;
var usersID;

$(function () {

    $('#bookingForm').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    });

    $('#bookingUpdatingDialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    });

    $('#bookingStartTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '15:00',
        maxTime: '22:00'
    });

    $('#bookingEndTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '15:00',
        maxTime: '22:00'
    });

    $('#bookingUpdatingStartTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '15:00',
        maxTime: '22:00'
    });

    $('#bookingUpdatingEndTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '15:00',
        maxTime: '22:00'
    });

    $('#updatingBooking').click(function () {
        updateBooking();
        $('#bookingUpdatingDialog').dialog('close');
    });

    $('#deletingBooking').click(function () {
        $('#bookingUpdatingDialog').dialog('close');
        cancelBooking(info.id);
    });

    $('#booking').click(function () {
        createBooking();
        $('#bookingForm').dialog('close');
    });
});

function selectRoomForUser(id, userId) {
    roomIdForHandler = id;
    usersID = userId;

    $('#user-calendar').fullCalendar('destroy');
    //TODO: ADD "CREATE BOOKING" BUTTON
    //TODO: Add variable for array length
    //TODO: Add variable for childID
    //TODO: FIX ALLLLLLLLLL 'COSTILS'
    $('input').on('click', function () {
        var numberOfKids = $('#kostil').val();
        var commentId;

        for (var i = 0; i < numberOfKids; i++) {
        commentId = $('#comment-' + i).val()
            if ($('#checkboxKid' + commentId).is(':checked')) {
                $('#child-comment-' + commentId).prop('hidden', false);
                $('#child-comment-' + commentId + '-1').prop('hidden', false);

            } else {
                $('#child-comment-' + commentId).prop('hidden', true);
                $('#child-comment-' + commentId + '-1').prop('hidden', true);
            }
        }
    });

    var path = 'getevents/' + id;

    $.ajax({
        url: path, success: function (result) {
            var objects;
            if (result.length !== 0) {
                objects = [];
                result = JSON.parse(result);

                for (var i = 0; i < result.length; i++) {
                    objects[i] = {
                        id: result[i].id,
                        title: result[i].name,
                        start: result[i].startTime,
                        end: result[i].endTime,
                        editable: false,
                        color: '#ffff00',
                        type: 'event'
                    }
                }
                renderingForUser(objects, id, userId);
            } else {
                $('#user-calendar').fullCalendar('destroy');

                objects = [{
                    title: '1',
                    start: '1',
                    end: '1',
                    editable: false
                }];
                renderingForUser(objects, id, userId);
            }
        }
    });
}

function renderingForUser(objects, id, userId) {

    bookingDate = {};
    info = {};

    bookingsArray = [];

    var pathForUploadingAllBookingsForUsers = 'getallbookings/' + userId + '/' + id;

    $.ajax({
        url: pathForUploadingAllBookingsForUsers, success: function (result) {
            result = JSON.parse(result);

            var objectsLen = objects.length;

            result.forEach(function (item, i) {
                objects[objectsLen + i] = {
                    id: item.id,
                    title: item.kidName,
                    start: item.date + 'T' + item.startTime + ':00',
                    end: item.date + 'T' + item.endTime + ':00',
                    color: '#99ff33',
                    borderColor: "#000000",
                    editable: false,
                    type: 'booking',
                    comment: item.comment
                };
            });
            renderCalendar(objects, id);
        }
    });
}

function makeISOTime(clickDate, idOfTimePicker) {
    var element = '#' + idOfTimePicker;
    var installedTime = $(element).timepicker('getTime');

    var timepickerMinutes = installedTime.getMinutes();
    var timepickerHours = installedTime.getHours();

    if (timepickerMinutes === 0) {
        timepickerMinutes = '00';
    } else {
        timepickerMinutes = timepickerMinutes.toString();
    }

    if (timepickerHours < 10) {
        timepickerHours = '0' + timepickerHours.toString();
    } else {
        timepickerHours = timepickerHours.toString();
    }
    return clickDate.substring(0, 11) + timepickerHours + ':' +
        timepickerMinutes + clickDate.substring(16);
}

function Booking(startTime, endTime, comment, kidId, roomId, usersID) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
    this.userId = usersID
}

function sendBookingToServerForUpdate(bookingForUpdate) {

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'change-booking',
        dataType: 'json',
        data: JSON.stringify({
            id: bookingForUpdate.id,
            startTime: bookingForUpdate.start.substring(0, 10) + ' ' + bookingForUpdate.start.substring(11, 16),
            endTime: bookingForUpdate.end.substring(0, 10) + ' ' + bookingForUpdate.end.substring(11, 16),
            comment: bookingForUpdate.comment
        }),
        success: function (result) {
            if (result) {

                bookingForUpdate.color = '#99ff33';
                bookingForUpdate.borderColor = '#000000';
                $('#user-calendar').fullCalendar('removeEvents', bookingForUpdate.id);
                $('#user-calendar').fullCalendar('renderEvent', bookingForUpdate);
            } else {
                alert('NOOOOOOO');
            }
        }
    });
}

function sendBookingToServerForCreate(bookingsArray) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'makenewbooking',
        dataType: 'json',
        data: JSON.stringify(bookingsArray),
        success: function (result) {

            var refresh = result;

            $('#user-calendar').fullCalendar('removeEvents', -1);

            refresh.forEach(function (item) {
                $('#user-calendar').fullCalendar('renderEvent', {
                    id: item.id,
                    title: item.kidName,
                    start: item.startTime,
                    end: item.endTime,
                    color: '#99ff33',
                    editable: false,
                    type: 'booking',
                    comment: item.comment
                });
            });

        }
    });
}

function cancelBooking(bookingId) {
    $.ajax({
        type: 'get',
        contentType: 'application/json',
        url: 'cancelBook/' + bookingId,
        dataType: 'json',
        data: JSON.stringify({}),
        success: function (result) {
            if (result) {
                $('#user-calendar').fullCalendar('removeEvents', bookingId);

            } else {
                alert('NOOOOOOO');
            }
        }
    });
}

function getComment(commentInputId) {
    return $('#' + commentInputId).val();
}

function updateBooking() {
    var newStartDate = makeISOTime(info.calEvent.start.format(), 'bookingUpdatingStartTimepicker');
    var newEndDate = makeISOTime(info.calEvent.end.format(), 'bookingUpdatingEndTimepicker');

    var bookingForUpdate = {
        id: info.calEvent.id,
        title: info.calEvent.title,
        start: newStartDate,
        end: newEndDate,
        comment: $('#child-comment-update').val()
    };

    sendBookingToServerForUpdate(bookingForUpdate);

}

function createBooking() {
    bookingsArray = [];
    var kidsCommentId;

    for (var i = 0; i < ($('#kostil').val()); i++) {
        kidsCommentId = $('#comment-' + i).val();
        if ($('#checkboxKid' + kidsCommentId).is(':checked')) {

            bookingsArray.push(
                new Booking(makeISOTime(bookingDate.clickDate, 'bookingStartTimepicker'),
                    makeISOTime(bookingDate.clickDate, 'bookingEndTimepicker'),
                    getComment("child-comment-" + kidsCommentId),
                    kidsCommentId,
                    roomIdForHandler,
                    usersID));

            $('#user-calendar').fullCalendar('renderEvent', {
                id: -1,
                title: '',
                start: makeISOTime(bookingDate.clickDate, 'bookingStartTimepicker'),
                end: makeISOTime(bookingDate.clickDate, 'bookingEndTimepicker'),
                editable: false
            });
        }
    }
    sendBookingToServerForCreate(bookingsArray);
}

function renderCalendar(objects, id) {
    $('#user-calendar').fullCalendar({
        minTime: '10:00:00',
        maxTime: '22:00:00',
        eventBackgroundColor: '#068000',
        eventColor: 'transparent',
        eventBorderColor: 'transparent',
        eventTextColor: '#000',
        slotDuration: '00:15:00',

        dayClick: function (date) {
            $('#bookingForm').dialog('open');

            var clickDate = date.format();

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }

            $('#bookingStartDate').val(clickDate.substring(0, 10));
            $('#bookingEndDate').val(clickDate.substring(0, 10));

            bookingDate.clickDate = clickDate;

            $('#dialog').dialog('open');
        },

        eventClick: function (calEvent) {

            if (calEvent.color === '#ffff00') {
                return;
            }

            if (calEvent.type === 'booking') {
                $('#child-comment-update').val(calEvent.comment);
            }

            var beforeUpdate = calEvent.title;

            $('#bookingUpdatingStartDate').val(calEvent.start.format().substring(0, 10));
            $('#bookingUpdatingEndDate').val(calEvent.end.format().substring(0, 10));

            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);


            /*
             newDate.setHours(date.getUTCHours());
             newDate.setMinutes(date.getUTCMinutes());
             newDate.setSeconds(date.getUTCSeconds());

             newDateForEnd.setHours(endDate.getUTCHours());
             newDateForEnd.setMinutes(endDate.getUTCMinutes());
             newDateForEnd.setSeconds(endDate.getUTCSeconds());
             */

            $('#bookingUpdatingStartTimepicker').timepicker('setTime', newDate);
            $('#bookingUpdatingEndTimepicker').timepicker('setTime', newDateForEnd);

            $('#bookingUpdatingDialog').dialog('open');

            info.id = calEvent.id;
            info.beforeUpdate = beforeUpdate;
            info.endDate = endDate;
            info.calEvent = calEvent;
            info.roomID = id;
            info.date = date;
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },

        defaultDate: $('#user-calendar').fullCalendar('getDate'),

        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#user-calendar').fullCalendar('renderEvent', eventData, false);
            }
            $('#user-calendar').fullCalendar('unselect');
        },

        eventRender: function (event, element) {
            if (event.rendering === 'background') {
                element.append(event.title);
                element.css('background-color', 'yellow');
                element.css('color', 'black');
            }
        },
        editable: false,
        eventLimit: true,
        events: objects
    });
}

function makeUTCTime(time, date) {
    time.setHours(date.getUTCHours());
    time.setMinutes(date.getUTCMinutes());
    time.setSeconds(date.getUTCSeconds());
    return time;
}
