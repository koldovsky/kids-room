/**
 * Created by dima- on 12.05.2016.
 */


function changeFunc(id) {
    $('#calendar').fullCalendar('destroy');
    $('#dialog').dialog({
        autoOpen: false,
        show: {
            effect : 'drop',
            duration : 500
        },
        hide: {
            effect : 'clip',
            duration : 500
        }
    })

    var path = "getCompanies/" + id;

    $.ajax({
        url: path, success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(',');

                result[0] = " " + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split(' ');

                    objects[i] = {
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3]
                    }
                }
                rendering(objects);
            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "",
                    start: "",
                    end: ""
                }]

                rendering(objects);

            }
        }
    });
}

function rendering(objects) {

    $('#calendar').fullCalendar({

        dayClick:function f(date, jsEvent, view) {

            var clickDate = date.format();

            $('#startDate').val("");
            $('#endDate').val(clickDate);
            $('#title').val(clickDate);

            $("#dialog").dialog('open');




            $('#creating').click(function(){

            var ev = {
                title:$('#startDate').val(),
                start: $('#title').val(),
                end: $('#endDate').val()
            }

            $('#calendar').fullCalendar('renderEvent', ev, true);
                forSendingToServer(ev);

                $('#title').val("");
                $('#dialog').dialog('close');
        })
        },



        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        //selectable: true,
        selectHelper: true,
        select: function (start, end) {
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
        events: objects
    });
    }


function forSendingToServer(event){

    $.ajax({
        type:'post',
        contentType : 'application/json',
        url:'getnewevent',
        dataType: "json",
    data: JSON.stringify({
        name : event.title,
        startTime : event.start,
        endTime: event.end
    })
})
}

