
/**
 * Created by dima- on 12.05.2016.
 */


function changeFunc(value) {
    var id = value;

    var objects = [];
    $('#calendar').fullCalendar('destroy');
    var st = "getCompanies/"+id;
    $.ajax({url: st, success: function(result){


        result = result.split(',');

        var string = result[0];

        string = " " + string;

        var stringToArray = string.split(' ');

        objects[0] = {
            title : stringToArray[1],
            start : stringToArray[2],
            end : stringToArray[3]
        }

        for(var i = 1; i < result.length; i++) {
            var string = result[i];
            var stringToArray = string.split(' ');

            objects[i] = {
                title : stringToArray[1],
                start : stringToArray[2],
                end : stringToArray[3]
            }
        }

        $('#calendar').fullCalendar('render');
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultDate: '2016-05-12',

            selectable: true,
            selectHelper: true,
            select: function(start, end) {
                var title = prompt('Event Title:');
                var eventData;
                if (title) {
                    eventData = {
                        title: title,
                        start: start,
                        end: end
                    };
                    $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                }
                $('#calendar').fullCalendar('unselect');
            },
            editable: true,
            eventLimit: true, // allow "more" link when too many events
            events : objects
        });
    }
    });

}
