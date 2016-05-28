/**
 * Created by dima- on 12.05.2016.
 */



$(function() {
    $('#basicExample').timepicker({
        dateFormat: "hh-mm-ss",

    });
});

$(function() {
    $('#ender').timepicker({
        dateFormat: "hh-mm-ss"
    });
});

$(function() {
    $('#startTimeUpdate').timepicker({
        dateFormat: "hh-mm-ss"
    });
});


$(function() {
    $('#endTimeUpdate').timepicker({
        dateFormat: "hh-mm-ss"
    });
});


function changeFunc(id) {

    $('#calendar').fullCalendar('destroy');

    $('#dialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        },

    })

    $('#updating').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        },

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
                        id: parseInt(stringToArray[4]),
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3]
                    }
                }
                rendering(objects, id);
            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "1",
                    start: "1",
                    end: "1"
                }]
                rendering(objects, id);
            }
        }
    });
}

function rendering(objects ,roomID) {

    $('#calendar').fullCalendar({

        dayClick: function f(date, jsEvent, view) {

            var clickDate = date.format();


            $('#startDate').val('');
            $('#title').val(clickDate.substring(0,10));
            $('#endDate').val(clickDate.substring(0,10));

            $('#dialog').dialog('open');

            if(clickDate.length < 12) {
                clickDate = clickDate + "T00:00:00";
            }


            $('#creating').click(function () {
                if( $('#startDate').val() == "" || clickDate == "") {
                    return;
                }

                var ev = {
                    id: -1,
                    title: $('#startDate').val(),
                    start: makeISOtime(clickDate, "basicExample"),
                    end:   makeISOtime(clickDate, "ender"),
                    backgroundColor: '#33cc33',
                    borderColor: '#33cc33'
                }

                $('#calendar').fullCalendar('renderEvent', ev, true);

                $.ajax({
                    type: 'post',
                    contentType: 'application/json',
                    url: 'getnewevent',
                    dataType: "json",
                    data: JSON.stringify({
                        name: ev.title,
                        startTime: ev.start,
                        endTime: ev.end,
                        roomId: roomID
                    }),
                    success: function (result) {
                        var newId = parseInt(result);

                        $('#calendar').fullCalendar( 'removeEvents', ev.id);

                        ev.id = newId;
                        ev.backgroundColor = '#428bca';
                        ev.borderColor = '#428bca';


                        $('#calendar').fullCalendar( 'renderEvent', ev );


                    }
                });

                $('#title').val("");

                $('#dialog').dialog('close');
                clickDate = "";
            })
        },

        eventClick: function (calEvent, jsEvent, view) {

            $('#titleUpdate').val(calEvent.title);
            $('#startDayUpdate').val(calEvent.start.format().substring(0, 10));
            $('#endDateUpdate').val(calEvent.end.format().substring(0, 10));


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

            $('#startTimeUpdate').timepicker('setTime', newDate);
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);

            $('#updating').dialog('open');


            $('#updatingButton').click(function () {


                var newStartDate = makeISOtime(calEvent.start.format(), "startTimeUpdate");
                var newEndDate = makeISOtime(calEvent.start.format(), "endTimeUpdate");

                var eventForUpdate = {
                    id: calEvent.id,
                    title: $('#titleUpdate').val(),
                    start: newStartDate,
                    end: newEndDate

                }
                alert(calEvent.id)
                $('#updating').dialog('close');
            });

        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        selectHelper: true,
/*        select: function (start, end) {
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
        },*/
        editable: true,
        eventLimit: true,
        events: objects
    });
}


function forSendingToServer(event, roomID) {
var res = -1;
   $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            name: event.title,
            startTime: event.start,
            endTime: event.end,
            roomId: roomID
        }),
       success : function (result) {
         res = result;
        }
    });

    return res;

}


function sendToServerForUpdate(event, roomID) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            id : event.id,
            name: event.title,
            startTime: event.start,
            endTime: event.end,
            roomId: roomID
        })
    });
}

function sendToServerForDelete(event, roomID) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            id : event.id
        })
    });
}

function makeISOtime(clickDate, idOfTimePicker) {
    var element = "#" + idOfTimePicker;
     var installedTime = $(element).timepicker('getTime');


     var timepickerMinutes = installedTime.getMinutes();
     var timepickerHours = installedTime.getHours();

     if(timepickerMinutes == 0) {
     timepickerMinutes = "00";
     } else {
     timepickerMinutes = "30";
     }

     if(timepickerHours < 10) {
     timepickerHours = "0" + timepickerHours.toString();
     } else {
     timepickerHours = timepickerHours.toString();
     }

     var superBuffer = "" + clickDate.substring(0,11) + timepickerHours + ":" +
     timepickerMinutes + clickDate.substring(16);

     return superBuffer;
}