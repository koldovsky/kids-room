/**
 * Created by dima- on 12.05.2016.
 */


function selectRoomForUser(id) {

    $('#calendar').fullCalendar('destroy');

    var path = "getCompanies/" + id;

    $.ajax({
        url: path,
        success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(',');

                result[0] = " " + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split(' ');

                    objects[i] = {
                        id: parseInt(stringToArray[4]),
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3],
                        stick: true
                    }
                }

                rendering(objects);

            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "1",
                    start: "1",
                    end: "1",
                    editable: false
                }]
                rendering(objects);

            }
        }
    });
}

function rendering(objects) {

    $('#calendar').fullCalendar({

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end,
                };
                $('#calendar').fullCalendar('renderEvent', eventData, false); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');
        },
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events: objects
    });
}

