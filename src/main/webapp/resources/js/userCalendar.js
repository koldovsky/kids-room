/**
 * Created by dima- on 12.05.2016.
 */
var info;
var bookingsArray;
var bookingDate;
var roomIdForHandler;

$(function () {
    $('#bookingStartTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});


$(function () {
    $('#bookingEndTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});

$(function () {
    $('#bookingUpdatingStartTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});

$(function () {
    $('#bookingUpdatingEndTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});


$( document ).ready(function() {
    $('#deletingBooking').click(function() {
        $('#bookingUpdatingDialog').dialog('close');
        cancelBooking(info.id);
    });
});

$( document ).ready(function() {
    $('#updatingBooking').click(function() {
        var newStartDate = makeISOtime(info.calEvent.start.format(), 'bookingUpdatingStartTimepicker');
        var newEndDate = makeISOtime(info.calEvent.end.format(), 'bookingUpdatingEndTimepicker');

        var bookingForUpdate = {
            id: info.calEvent.id,
            title: info.calEvent.title,
            start: newStartDate,
            end: newEndDate
        };

        sendBookingToServerForUpdate(bookingForUpdate, info.roomID);

        $('#bookingUpdatingDialog').dialog('close');
    });
});

$( document ).ready(function() {
    $('#booking').click(function () {
        for (var i = 0; i < ($('#kostil').val()) + 1; i++) {
            if ($('#checkboxKid' + i).is(':checked')) {

                bookingsArray.push(
                    new Booking(makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker'),
                        makeISOtime(bookingDate.clickDate, 'bookingEndTimepicker'), "NO", i, roomIdForHandler));

                $('#user-calendar').fullCalendar('renderEvent', {
                    id: -1,
                    title: "",
                    start: makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker'),
                    end:  makeISOtime(bookingDate.clickDate, 'bookingEndTimepicker'),
                    editable: false
                });
            }
        }
        sendBookingToServerForCreate(bookingsArray);
        /*  $.ajax({
         type: 'post',
         contentType: 'application/json',
         url: 'makenewbooking',
         dataType: 'json',
         data: JSON.stringify(bookingsArray),
         success: function (result) {

         var refresh = result;

         $('#user-calendar').fullCalendar('removeEvents', -1);

         refresh.forEach(function (item, i, refresh) {
         $('#user-calendar').fullCalendar('renderEvent', {
         id: item.id,
         title: item.kidName,
         start: item.startTime,
         end: item.endTime,
         color: "#99ff33",
         editable: false
         });
         });
         }
         });*/

        $('#bookingForm').dialog('close');
    });
});

function selectRoomForUser(id) {
    roomIdForHandler = id;
    $('#user-calendar').fullCalendar('destroy');

    $('input').on('click', function () {

        for (var i = 0; i < ($('#kostil').val()); i++) {
            if ($('#checkboxKid' + i).is(':checked')) {
                $('#child-comment-' + i).prop('readonly', false);
            } else {
                $('#child-comment-' + i).prop('readonly', true);
            }
        }
    });

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

    var path = 'getevents/' + id;

    $.ajax({
        url: path, success: function (result) {
            if (result.length != 0) {
                var objects = [];
                result = result.split(', ');

                result[0] = ' ' + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split('|');

                    objects[i] = {
                        id: parseInt(stringToArray[3]),
                        title: stringToArray[0],
                        start: stringToArray[1],
                        end: stringToArray[2],
                        editable: false,
                        color: "#ffff00"
                    }
                }
                renderingForUser(objects, id);
            } else {
                $('#user-calendar').fullCalendar('destroy');

                var objects = [{
                    title: '1',
                    start: '1',
                    end: '1',
                    editable: false
                }];
                renderingForUser(objects, id);
            }
        }
    });
}

function renderingForUser(objects, id) {

    bookingDate = new Object();
    info = new Object();
    bookingsArray = [];

  /*  $('#booking').click(function () {
        for (var i = 0; i < ($('#kostil').val()) + 1; i++) {
            if ($('#checkboxKid' + i).is(':checked')) {

                bookingsArray.push(
                    new Booking(makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker'),
                        makeISOtime(bookingDate.clickDate, 'bookingEndTimepicker'), "NO", i, id));

                $('#user-calendar').fullCalendar('renderEvent', {
                    id: -1,
                    title: "",
                    start: makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker'),
                    end:  makeISOtime(bookingDate.clickDate, 'bookingEndTimepicker'),
                    editable: false
                });
            }
        }
        sendBookingToServerForCreate(bookingsArray);
      /!*  $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'makenewbooking',
            dataType: 'json',
            data: JSON.stringify(bookingsArray),
            success: function (result) {

                var refresh = result;

                $('#user-calendar').fullCalendar('removeEvents', -1);

                refresh.forEach(function (item, i, refresh) {
                    $('#user-calendar').fullCalendar('renderEvent', {
                        id: item.id,
                        title: item.kidName,
                        start: item.startTime,
                        end: item.endTime,
                        color: "#99ff33",
                        editable: false
                    });
                });
            }
        });*!/

        $('#bookingForm').dialog('close');
    });*/

    /*$('#updatingBooking').click(function() {
        var newStartDate = makeISOtime(info.calEvent.start.format(), 'bookingUpdatingStartTimepicker');
        var newEndDate = makeISOtime(info.calEvent.end.format(), 'bookingUpdatingEndTimepicker');

        var bookingForUpdate = {
            id: info.calEvent.id,
            title: info.calEvent.title,
            start: newStartDate,
            end: newEndDate
        };

        console.log(info.calEvent.id);

        console.log(newStartDate);
        console.log(newEndDate);


        sendBookingToServerForUpdate(bookingForUpdate, info.roomID);

        $('#bookingUpdatingDialog').dialog('close');
    });*/

/*    $('#deletingBooking').click(function() {
        $('#bookingUpdatingDialog').dialog('close');
       cancelBooking(info.id);

    });*/
    var pathForUploadingAllBookingsForUsers = 'getallbookings/1/' + id;

    $.ajax({
        url: pathForUploadingAllBookingsForUsers, success: function (result) {
            result = JSON.parse(result);

            var objectsLen = objects.length;

            result.forEach(function (item, i, result) {
                console.log(item.date + item.startTime + ":00");
                objects[objectsLen + i] = {
                    id: item.id,
                    title: item.kidName,
                    start: item.date + "T" + item.startTime + ":00",
                    end: item.date + "T" + item.endTime + ":00",
                    color: "#99ff33",
                    editable: false
                }
            });

            $('#user-calendar').fullCalendar({
                minTime: '10:00:00',
                maxTime: '22:00:00',
                eventBackgroundColor: '#068000',
                eventColor: 'transparent',
                eventBorderColor: 'transparent',
                eventTextColor: '#000',
                slotDuration: '00:15:00',

                dayClick: function f(date, jsEvent, view) {
                    $('#bookingForm').dialog('open');

                    var clickDate = date.format();

                    $('#bookingStartDate').val(clickDate.substring(0, 10));
                    $('#bookingEndDate').val(clickDate.substring(0, 10));


                    if (clickDate.length < 12) {
                        clickDate = clickDate + 'T00:00:00';
                    }

                    bookingDate.clickDate = clickDate;                  //цей об'єкт переносить дату у ф-цію для створення букігу

                    $('#dialog').dialog('open');
                },

                eventClick: function (calEvent, jsEvent, view) {

                    var beforeUpdate = calEvent.title;

                    $('#bookingUpdatingStartDate').val(calEvent.start.format().substring(0, 10));
                    $('#bookingUpdatingEndDate').val(calEvent.end.format().substring(0, 10));

                    var date = new Date(calEvent.start.format());
                    var endDate = new Date(calEvent.end.format());

                    var newDate = new Date();
                    var newDateForEnd = new Date();

                    newDate.setHours(date.getUTCHours());
                    newDate.setMinutes(date.getUTCMinutes());
                    newDate.setSeconds(date.getUTCSeconds());

                    newDateForEnd.setHours(endDate.getUTCHours());
                    newDateForEnd.setMinutes(endDate.getUTCMinutes());
                    newDateForEnd.setSeconds(endDate.getUTCSeconds());

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
                    if (event.rendering == 'background') {
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
    });

    /* $('#user-calendar').fullCalendar({
     minTime: '10:00:00',
     maxTime: '22:00:00',
     eventBackgroundColor: '#068000',
     eventColor: 'transparent',
     eventBorderColor: 'transparent',
     eventTextColor: '#000',
     slotDuration: '00:15:00',

     dayClick: function f(date, jsEvent, view) {
     $('#bookingForm').dialog('open');

     var clickDate = date.format();

     $('#bookingStartDate').val(clickDate.substring(0, 10));
     $('#bookingEndDate').val(clickDate.substring(0, 10));


     if (clickDate.length < 12) {
     clickDate = clickDate + 'T00:00:00';
     }

     bookingDate.clickDate = clickDate;                  //цей об'єкт переносить дату у ф-цію для створення букігу

     $('#dialog').dialog('open');
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
     if (event.rendering == 'background') {
     element.append(event.title);
     element.css('background-color', 'yellow');
     element.css('color', 'black');
     }
     },
     editable: false,
     eventLimit: true,
     events: objects
     });*/
}

function makeISOtime(clickDate, idOfTimePicker) {
    var element = '#' + idOfTimePicker;
    var installedTime = $(element).timepicker('getTime');

    var timepickerMinutes = installedTime.getMinutes();
    var timepickerHours = installedTime.getHours();

    if (timepickerMinutes == 0) {
        timepickerMinutes = '00';
    } else {
        timepickerMinutes = timepickerMinutes.toString();
    }

    if (timepickerHours < 10) {
        timepickerHours = '0' + timepickerHours.toString();
    } else {
        timepickerHours = timepickerHours.toString();
    }

    return '' + clickDate.substring(0, 11) + timepickerHours + ':' +
        timepickerMinutes + clickDate.substring(16);
}

function Booking(startTime, endTime, comment, kidId, roomId) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
}

function sendBookingToServerForUpdate(bookingForUpdate, roomID) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'change-booking',
        dataType: 'json',
        data: JSON.stringify({
            id: bookingForUpdate.id,
        //    name: bookingForUpdate.title,
            startTime: bookingForUpdate.start.substring(0,10) + " " + bookingForUpdate.start.substring(11, 16),
            endTime: bookingForUpdate.end.substring(0,10) + " " + bookingForUpdate.end.substring(11, 16)
        //    roomId: roomID
        }),
        success: function (result) {
            if (result) {
                bookingForUpdate.color = "#99ff33";

                $('#user-calendar').fullCalendar('removeEvents', bookingForUpdate.id);
                $('#user-calendar').fullCalendar('renderEvent', bookingForUpdate);
            } else {
                alert("NOOOOOOO");
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

            refresh.forEach(function (item, i, refresh) {
                $('#user-calendar').fullCalendar('renderEvent', {
                    id: item.id,
                    title: item.kidName,
                    start: item.startTime,
                    end: item.endTime,
                    color: "#99ff33",
                    editable: false
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
        data: JSON.stringify({
        }),
        success: function (result) {
            if (result) {
                $('#user-calendar').fullCalendar('removeEvents', bookingId);

            } else {
                alert("NOOOOOOO");
            }
        }
    });
}