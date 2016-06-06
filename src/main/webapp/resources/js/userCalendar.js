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
                        rendering: 'background'
                    }
                }
                renderingForUser(objects, id);
            } else {
                $('#user-calendar').fullCalendar('destroy');

                var objects = [{
                    title: '1',
                    start: '1',
                    end: '1'
                }]
                renderingForUser(objects, id);
            }
        }
    });
}

function renderingForUser(objects) {

    var newBooking = new Object();
    var newBooking1 = new Object();
    var objectsArray = [];

    $('#booking').click(function () {

        var a1 = {
            date: "2016-06-06",
            startTime: "2016-06-06T15:00:00",
            endTime: "2016-06-06T15:00:00"
        };

        var a2 = {
            date: "2013-03-03",
            startTime: "2016-06-06T15:00:00",
            endTime: "2016-06-06T15:00:00"
        };

        var array = [a1, a2];
        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'getnewbooking',
            dataType: 'json',
            data: JSON.stringify(array),
            success: function (result) {
                alert("YEEEEEEEEEES");
            }
        });

        $('#title').val('');
        $('#bookingForm').dialog('close');
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

            $('#dialog').dialog('open');

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }

            newBooking.id = "1";
            newBooking1.id = "2";

            objectsArray[0] = newBooking;

            objectsArray[1] = newBooking1;
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

