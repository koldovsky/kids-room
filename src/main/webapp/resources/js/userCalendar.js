/**
 * Created by dima- on 12.05.2016.
 */

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

function selectRoomForUser(id) {
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
                       // rendering: 'background'
                        editable:false,
                        color:"#ffff00"
                    }
                }
                renderingForUser(objects, id);
            } else {
                $('#user-calendar').fullCalendar('destroy');

                var objects = [{
                    title: '1',
                    start: '1',
                    end: '1'
                }];
                renderingForUser(objects, id);
            }
        }
    });
}

function renderingForUser(objects, id) {

    var bookingDate = new Object();

    var bookingsArray = [];

    $('#booking').click(function () {
        for (var i = 0; i < ($('#kostil').val()); i++) {
            if ($('#checkboxKid' + i).is(':checked')) {
                bookingsArray.push(
                    new Booking(makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker'),
                        makeISOtime(bookingDate.clickDate, 'bookingEndTimepicker'), "NO", 1, id, 1));

                $('#user-calendar').fullCalendar('renderEvent', {
                    title: "name",
                    start: makeISOtime(bookingDate.clickDate, 'bookingStartTimepicker')
                });
            }
        }

        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'makenewbooking',
            dataType: 'json',
            data: JSON.stringify(bookingsArray),
            success: function (result) {
                alert("YEEEEEEEEEES");
            }
        });

        bookingsArray = [];

        $('#title').val('');

        $('#bookingForm').dialog('close');
    });

    var pathForUploadingAllBookingsForUsers = 'getallbookings/1/' + id;

    $.ajax({
        url: pathForUploadingAllBookingsForUsers, success: function (result) {
            result = JSON.parse(result);

            var objectsLen = objects.length;

            result.forEach(function (item, i, result) {
                console.log(item.date + item.startTime + ":00");
                objects[objectsLen + i] = {
                    title: item.kidName,
                    start: item.date + "T" + item.startTime + ":00",
                    end: item.date + "T" + item.endTime + ":00",
                    color: "#99ff33",
                    editable: true
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
                  alert("DON'T TOUCH THIS!!!!!!");
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
        timepickerMinutes = '30';
    }

    if (timepickerHours < 10) {
        timepickerHours = '0' + timepickerHours.toString();
    } else {
        timepickerHours = timepickerHours.toString();
    }

    return '' + clickDate.substring(0, 11) + timepickerHours + ':' +
        timepickerMinutes + clickDate.substring(16);
}

function Booking(startTime, endTime, comment, kidId, roomId, userId) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
    this.userId = userId;
}
