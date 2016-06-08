/**
 * Created by dima- on 12.05.2016.
 */

$(function () {
    $('#basicExample').timepicker({

        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});

$(function () {
    $('#ender').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});

$(function () {
    $('#startTimeUpdate').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});


$(function () {
    $('#endTimeUpdate').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
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
        }
    });

    $('#updating').dialog({
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
        url: path,

        success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(', ');

                for (var i = 0; i < result.length; i++) {

                    var string = result[i];
                    var stringToArray = string.split('|');

                    objects[i] = {
                        id: parseInt(stringToArray[3]),
                        title: stringToArray[0],
                        start: stringToArray[1],
                        end: stringToArray[2]
                    }


                }
                rendering(objects, id);
            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: '1',
                    start: '1',
                    end: '1'
                }];
                rendering(objects, id);
            }
        }
    });
}

function rendering(objects, roomID) {

    var info = new Object();
    var creatingEvent = new Object();
    var ACTIVE_EVENT = '#428bca';
    var NOT_ACTIVE_EVENT = '#33cc33';

    $('#updatingButton').click(function () {
        var newStartDate = makeISOtime(info.calEvent.start.format(), 'startTimeUpdate');
        var newEndDate = makeISOtime(info.calEvent.end.format(), 'endTimeUpdate');

        if ((info.date.toTimeString() === (new Date(newStartDate)).toTimeString()) &&
            (info.endDate.toTimeString() === (new Date(newEndDate)).toTimeString()) &&
            (info.beforeUpdate === $('#titleUpdate').val())) {
            $('#updating').dialog('close');
            return;
        }
        $('#calendar').fullCalendar('removeEvents', info.calEvent.id);

        var eventForUpdate = {
            id: info.calEvent.id,
            title: $('#titleUpdate').val(),
            start: newStartDate,
            end: newEndDate
        };

        $('#calendar').fullCalendar('renderEvent', eventForUpdate);

        sendToServerForUpdate(eventForUpdate, info.roomID);

        $('#updating').dialog('close');
    });

    $('#deleting').click(function () {
        sendToServerForDelete(info.calEvent);
        $('#calendar').fullCalendar('removeEvents', info.calEvent.id);
        $('#updating').dialog('close');
    });

    $('#creating').click(function () {

        var ev = {
            id: -1,
            title: $('#startDate').val(),
            start: makeISOtime(creatingEvent.clickDate, 'basicExample'),
            end: makeISOtime(creatingEvent.clickDate, 'ender'),
            backgroundColor: NOT_ACTIVE_EVENT,
            borderColor: NOT_ACTIVE_EVENT,
            editable: false
        };

        $('#calendar').fullCalendar('renderEvent', ev, true);

        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'getnewevent',
            dataType: 'json',
            data: JSON.stringify({
                name: ev.title,
                startTime: ev.start,
                endTime: ev.end,
                roomId: creatingEvent.roomID
            }),
            success: function (result) {
                var newId = parseInt(result);

                $('#calendar').fullCalendar('removeEvents', ev.id);

                ev.id = newId;
                ev.backgroundColor = ACTIVE_EVENT;
                ev.borderColor = ACTIVE_EVENT;
                ev.editable = true;

                $('#calendar').fullCalendar('renderEvent', ev);
            }
        });

        $('#title').val('');
        $('#dialog').dialog('close');
    });

    $('#calendar').fullCalendar({
        slotDuration: '00:15:00',

        dayClick: function f(date, jsEvent, view) {

            var clickDate = date.format();
            $('#startDate').val('');
            $('#title').val(clickDate.substring(0, 10));
            $('#endDate').val(clickDate.substring(0, 10));

            $('#dialog').dialog('open');

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }
                                                            //тут вставляються автоматичні дати для події на весь день
            var ckbox = $('#checkbox');

            $('input').on('click', function () {
                if (ckbox.is(':checked')) {
                    $('#basicExample').prop('readonly', true);
                    $('#ender').prop('readonly', true);
                } else {
                    $('#basicExample').prop('readonly', false);
                    $('#ender').prop('readonly', false);
                }
            });

            creatingEvent.clickDate = clickDate;
            creatingEvent.roomID = roomID;
        },

        eventClick: function (calEvent, jsEvent, view) {

            var beforeUpdate = calEvent.title;

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

            info.beforeUpdate = beforeUpdate;
            info.endDate = endDate;
            info.calEvent = calEvent;
            info.roomID = roomID;
            info.date = date;
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        selectHelper: true,
        editable: true,
        eventLimit: true,
        events: objects
    });
}

function sendToServerForUpdate(event, roomID) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'geteventforupdate',
        dataType: 'json',
        data: JSON.stringify({
            id: event.id,
            name: event.title,
            startTime: event.start,
            endTime: event.end,
            roomId: roomID
        })
    });
}

function sendToServerForDelete(event) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'geteventfordelete',
        dataType: 'json',
        data: JSON.stringify({
            id: event.id,
            name: event.title,
            startTime: event.start,
            endTime: event.end
        })
    });
}

/*
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

*/
