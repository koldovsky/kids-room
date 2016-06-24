/**
 * Created by dima- on 12.05.2016.
 */
var beforeUpdate;
var info;
var creatingEvent;

var ACTIVE_EVENT = '#428bca';
var NOT_ACTIVE_EVENT = '#33cc33';

$(function () {

    $(".modal-dialog-recurrently").dialog({
        modal: true,
        autoOpen: false
    });

    $('#dialog-recurrently').dialog({
        title: 'Recurrent events',
        width: 500
    });

    $('.timepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '15:00',
        maxTime: '22:00'
    });

    $('.dialog').dialog({
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

    $('#updatingButton').click(function () {
        var newStartDate = makeISOTime(info.calEvent.start.format(), 'startTimeUpdate');
        var newEndDate = makeISOTime(info.calEvent.end.format(), 'endTimeUpdate');

        $('#calendar').fullCalendar('removeEvents', info.calEvent.id);

        var eventForUpdate = {
            id: info.calEvent.id,
            title: $('#titleUpdate').val(),
            start: newStartDate,
            end: newEndDate,
            editable: false,
            description: $('#descriptionUpdate').val()
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
            start: makeISOTime(creatingEvent.clickDate, 'basicExample'),
            end: makeISOTime(creatingEvent.clickDate, 'ender'),
            backgroundColor: NOT_ACTIVE_EVENT,
            borderColor: NOT_ACTIVE_EVENT,
            editable: false,
            description: $('#description').val()
        };

        $('#calendar').fullCalendar('renderEvent', ev, true);
        $('#calendar').fullCalendar('unselect');

        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'getnewevent',
            dataType: 'json',
            data: JSON.stringify({
                name: ev.title,
                startTime: ev.start,
                endTime: ev.end,
                roomId: creatingEvent.roomID,
                description: ev.description
            }),
            success: function (result) {
                var newId = parseInt(result);

                $('#calendar').fullCalendar('removeEvents', ev.id);

                ev.id = newId;
                ev.backgroundColor = ACTIVE_EVENT;
                ev.borderColor = ACTIVE_EVENT;

                $('#calendar').fullCalendar('renderEvent', ev);
            }
        });

        $('#title').val('');
        $('#dialog').dialog('close');
    });

    $('#recurrent').click(function () {
        $('#dialog-recurrently').dialog('open');

        $('#recurrent-event-start-date').val($('#title').val());
        $('#recurrent-event-end-date').val($('#endDate').val());
    });

    $('#recurrent-event-create').click(function () {
        var startDate = $('#recurrent-event-start-date').val();
        var endDate = $('#recurrent-event-end-date').val();

        startDate += "T00:00:00";
        endDate += "T00:00:00";

        startDate = makeISOTime(startDate, 'recurrent-event-start-time');
        endDate = makeISOTime(endDate, 'recurrent-event-end-time');

        var recurrentEvents = {
            name: $('#recurrent-event-title').val(),
            startTime: startDate,
            endTime: endDate,
            roomId: localStorage["roomId"],
            description: $('#recurrent-event-description').val()
        };

        $('#dialog-recurrently').dialog('close');
        $('#dialog').dialog('close');

        sendRecurrentEventsForCreate(recurrentEvents);
    });
});

function selectRoomForManager(id) {

    $('#calendar').fullCalendar('destroy');

    var path = 'getevents/' + id;

    $.ajax({
        url: path,

        success: function (result) {
            var objects;
            if (result.length) {
                objects = [];
                result = JSON.parse(result);

                for (var i = 0; i < result.length; i++) {
                    objects[i] = {
                        id: result[i].id,
                        title: result[i].name,
                        start: result[i].startTime,
                        end: result[i].endTime,
                        editable: false,
                        type: 'event',
                        description: result[i].description
                    }
                }
                renderCalendarForManager(objects, id);
            } else {
                $('#calendar').fullCalendar('destroy');
                /**
                 * This object is required for creating empty calendar
                 * Without this object calendar will not be rendered
                 */
                objects = [{
                    title: '1',
                    start: '1',
                    end: '1'
                }];
                renderCalendarForManager(objects, id);
            }
        }
    });
}

function renderCalendarForManager(objects, roomID) {
    info = {};
    creatingEvent = {};

    $('#calendar').fullCalendar({
        slotDuration: '00:15:00',

        dayClick: function (date) {

            var clickDate = date.format();
            $('#startDate').val('');
            $('#title').val(clickDate.substring(0, 10));
            $('#endDate').val(clickDate.substring(0, 10));

            $('#dialog').dialog('open');

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }

            /*            var ckbox = $('#checkbox');

             $('input').on('click', function () {
             if (ckbox.is(':checked')) {
             $('#basicExample').prop('readonly', true);
             $('#ender').prop('readonly', true);
             } else {
             $('#basicExample').prop('readonly', false);
             $('#ender').prop('readonly', false);
             }
             });*/

            creatingEvent.clickDate = clickDate;
            creatingEvent.roomID = roomID;
        },

        eventClick: function (calEvent) {

            if (calEvent.color === NOT_ACTIVE_EVENT) {
                return;
            }

            beforeUpdate = calEvent.title;

            $('#titleUpdate').val(calEvent.title);
            $('#startDayUpdate').val(calEvent.start.format().substring(0, 10));
            $('#endDateUpdate').val(calEvent.end.format().substring(0, 10));
            $('#descriptionUpdate').val(calEvent.description);

            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);

            $('#startTimeUpdate').timepicker('setTime', newDate);
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);

            $('#updating').dialog('open');

            info.beforeUpdate = beforeUpdate;
            info.endDate = endDate;
            info.calEvent = calEvent;
            info.roomID = roomID;
            info.date = date;
            info.description = $('#descriptionUpdate').val();
        },

        selectable: true,
        selectHelper: true,
        select: function (start, end) {

            $('#dialog').dialog('open');

            /**
             * This if statement provide that click on day
             * will have start date and end date the same
             */
            if(start.format().length < 11) {
                end = end.add(-1, 'day');
            }

            creatingEvent.start = start;
            creatingEvent.end = end;
            creatingEvent.clickDate = start.format();
            creatingEvent.roomID = roomID;

            $('#title').val(start.format().substring(0, 10));
            $('#endDate').val(end.format().substring(0, 10));

            var timeStart = makeUTCTime(new Date(), new Date(start.format()));
            var timeEnd = makeUTCTime(new Date(), new Date(end.format()));

            $('#basicExample').timepicker('setTime', timeStart);
            $('#ender').timepicker('setTime', timeEnd);
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),
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
            roomId: roomID,
            description: event.description
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

function makeUTCTime(time, date) {
    time.setHours(date.getUTCHours());
    time.setMinutes(date.getUTCMinutes());
    time.setSeconds(date.getUTCSeconds());
    return time;
}

function sendRecurrentEventsForCreate(recurrentEvents) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getrecurrentevents',
        dataType: 'json',
        data: JSON.stringify(recurrentEvents),
        success: function (result) {

        }
    });
}