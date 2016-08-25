/**
 * Created by dima- on 12.05.2016.
 */

var info_event;
var creatingEvent;
var allEvents;
var NOT_ACTIVE_EVENT = '#33cc33';
var BORDER_COLOR = '#000000';

$(function () {

    $('#update-recurrent').hide();
    $('#update-recurrent-booking').hide();

    $('.modal-dialog-recurrently').dialog({
        modal: true,
        autoOpen: false
    });

    $('#dialog-recurrently').dialog({
        title: 'Recurrent events',
        width: 500
    });

    $('#dialog').dialog({
        title: 'New event',
        width: 550,
        autoOpen: false,
        beforeClose: function () {
            $('#deleting-recurrent-event').hide();
        }

    });


    $('.timepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
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

    $('#updating').dialog({
        autoOpen: false,
        title: 'Change event'
    });

    $('#choose-updating-type').dialog({
        autoOpen: false
    }).closest('.ui-dialog')
        .find('.ui-dialog-title')
        .html('This is one appointment <br> is a series.<br>What would you like to open');


    $('#updatingButton').click(function () {

        $('#calendar').fullCalendar('removeEvents', info_event.calEvent.id);

        var eventForUpdate = {
            id: info_event.calEvent.id,
            title: $('#titleUpdate').val(),
            start: makeISOTime(info_event.calEvent.start.format(), 'startTimeUpdate'),
            end: makeISOTime(info_event.calEvent.end.format(), 'endTimeUpdate'),
            editable: false,
            description: $('#descriptionUpdate').val()
        };

        $('#calendar').fullCalendar('renderEvent', eventForUpdate);

        sendToServerForUpdate(eventForUpdate, info_event.roomID);

        $('#updating').dialog('close');
    });

    $('#deleting-recurrent-event').click(function () {
        deleteRecurrentEvents(info_event.calEvent.recurrentId);
        $('#dialog').dialog('close');
    });

    $('#deleting-single-event').click(function () {
        sendToServerForDelete(info_event.calEvent);
        $('#calendar').fullCalendar('removeEvents', info_event.calEvent.id);
        $('#updating').dialog('close');
    });

    $('#cancel-update').click(function () {
        $('#updating').dialog('close');
    });

    $('#creating').click(function () {
        createRecurrentEvents();
    });

    $('#recurrent').click(function () {
        $('#dialog-recurrently').dialog('open');

        $('#recurrent-event-start-date').val($('#title').val());
        $('#recurrent-event-end-date').val($('#endDate').val());
    });

    $('#cancel').click(function () {
        $('#title').val('');
        $('#dialog').dialog('close');
        closeDialog('dialog');
    });

    $('#create-new-event').click(function () {
        var newEventDate = $('#calendar').fullCalendar('getDate').format();
        $('#title').val(newEventDate.substring(0, 10));
        $('#endDate').val(newEventDate.substring(0, 10));
        $('#dialog').dialog('open');
    });

    $('.my-radio').click(function () {
        if ($('#weekly').is(':checked')) {
            $('#days-for-recurrent-form').attr('hidden', false);
        } else {
            $('#days-for-recurrent-form').attr('hidden', true);
        }

        if ($('#no-recurrent').is(':checked')) {
            $('#endDate').val($('#title').val());
        }
    });

    $('#confirm-choose').click(function () {
        if ($('#single-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#updating').dialog('open');
        }

        if ($('#recurrent-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#update-recurrent').show();
            $('#creating').hide();
            $('#deleting-recurrent-event').show();
            $('#dialog').dialog('open');
        }
    });

    $('#cancel-choose').click(function () {
        $('#choose-updating-type').dialog('close');

        $('#single-update').prop('checked', true);

        $('#recurrent-update').prop('checked', false);

    });

    $('#update-recurrent').click(function () {

        deleteRecurrentEvents(info_event.calEvent.recurrentId);
        
        createRecurrentEvents();
    });
});

/**
 *  This function gets events from controller.
 *  After that call renderCalendarForManager for rendering
 */

function selectRoomForManager(id) {

    $('#calendar').fullCalendar('destroy');

    $.ajax({
        url: 'getroomproperty/' + id,
        success: function(result){
            result = result.split(' ');

            result[0] += ":00";
            result[1] += ":00";

            var startTime = result[0];
            var endTime = result[1];

            $.ajax({
                url: 'getevents/' + id,

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
                                description: result[i].description,
                                color: result[i].color,
                                borderColor: BORDER_COLOR,
                                recurrentId: result[i].recurrentId
                            }
                        }
                        renderCalendarForManager(objects, id, startTime, endTime);
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
                        renderCalendarForManager(objects, id, startTime, endTime);
                    }
                }
            });
        }
    });
}

/**
 *  This function gets array of events and render calendar.
 *  dayClick methods and eventClick methods implemented here
 */
function renderCalendarForManager(objects, roomID, workingHoursStart, workingHoursEnd) {
    info_event = {};
    creatingEvent = {};
    allEvents = objects;

    $('#calendar').fullCalendar({
        slotDuration: '00:15:00',
        timeFormat : 'HH:mm',
        minTime: workingHoursStart,
        maxTime: workingHoursEnd,

        dayClick: function (date) {
            var clickDate = date.format();

            $('#startDate').val('');
            $('#title').val(clickDate.substring(0, 10));
            $('#endDate').val(clickDate.substring(0, 10));

            $('#dialog').dialog('open');

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }

            creatingEvent.clickDate = clickDate;
            creatingEvent.roomID = roomID;
        },

        eventClick: function (calEvent) {

            if (calEvent.color === NOT_ACTIVE_EVENT) {
                return;
            }

            $('#titleUpdate').val(calEvent.title);
            $('#startDayUpdate').val(calEvent.start.format().substring(0, 10));
            $('#endDateUpdate').val(calEvent.end.format().substring(0, 10));
            $('#descriptionUpdate').val(calEvent.description);


            $('#startDate').val(calEvent.title);
            $('#title').val(calEvent.start.format().substring(0, 10));
            $('#endDate').val(calEvent.end.format().substring(0, 10));
            $('#description').val(calEvent.description);


            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);

            $('#startTimeUpdate').timepicker('setTime', newDate);
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);

            $('#basicExample').timepicker('setTime', newDate);
            $('#ender').timepicker('setTime', newDateForEnd);

            info_event.calEvent = calEvent;
            info_event.roomID = roomID;
            info_event.description = $('#descriptionUpdate').val();


            if (!!calEvent.recurrentId) {
                $('#choose-updating-type').dialog('open');
            }
            else {
                $('#updating').dialog('open');
            }
        },

        selectable: true,
        selectHelper: true,
        select: function (start, end) {

            $('#dialog').dialog('open');

            /**
             * This 'if' statement provide that click on day
             * will have start date and end date the same
             */
            if (start.format().length < 11) {
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
            roomId: localStorage['roomId'],
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

function sendRecurrentEventsForCreate(recurrentEvents, dayWhenEventIsRecurrent, eventColor) {
    var stringWithDaysForRecurrent = '';

    dayWhenEventIsRecurrent.forEach(function (item) {
        stringWithDaysForRecurrent += item + ' ';
    });

    stringWithDaysForRecurrent.substring(0, stringWithDaysForRecurrent.length - 1);

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getrecurrentevents',
        dataType: 'json',
        data: JSON.stringify({
            name: recurrentEvents.title,
            startTime: recurrentEvents.start,
            endTime: recurrentEvents.end,
            daysOfWeek: stringWithDaysForRecurrent,
            roomId: localStorage['roomId'],
            description: recurrentEvents.description,
            color: eventColor,
            borderColor: BORDER_COLOR
        }),
        success: function (result) {
            var recurrentEventsForRender = [];

            result.forEach(function (item, i) {
                var newRecurrentEvent = {
                    id: item.id,
                    title: item.name,
                    start: item.startTime,
                    end: item.endTime,
                    editable: false,
                    type: 'event',
                    description: item.description,
                    color: item.color,
                    borderColor: BORDER_COLOR,
                    recurrentId: item.recurrentId
                };

                allEvents.push(newRecurrentEvent);

                recurrentEventsForRender.push(newRecurrentEvent);

                $('#calendar').fullCalendar('renderEvent', recurrentEventsForRender[i], true);
            });
        }
    });
}

function createRecurrentEvents() {

    var startDateForCreatingRecurrentEvents = $('#title').val() + 'T00:00:00';
    var endDate = $('#endDate').val() + 'T00:00:00';

    var eventColor = $('#color-select').val();

    var ev = {
        id: -1,
        title: $('#startDate').val(),
        start: makeISOTime(startDateForCreatingRecurrentEvents, 'basicExample'),
        end: makeISOTime(endDate, 'ender'),
        backgroundColor: NOT_ACTIVE_EVENT,
        borderColor: NOT_ACTIVE_EVENT,
        editable: false,
        description: $('#description').val()
    };

    if ($('#weekly').is(':checked')) {
        var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        var dayWhenEventIsRecurrent = [];
        var indexForRecurrentDay = 0;

        checkBoxesDays.forEach(function (item) {
            var ckbox = $('#' + item);
            if (ckbox.is(':checked')) {
                dayWhenEventIsRecurrent[indexForRecurrentDay] = ckbox.val();
                indexForRecurrentDay++;
            }
        });

        sendRecurrentEventsForCreate(ev, dayWhenEventIsRecurrent, eventColor);

        $('#title').val('');
        $('#dialog').dialog('close');

        closeDialog('dialog');
        return;
    }

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
            roomId: localStorage['roomId'],
            description: ev.description,
            color: eventColor
        }),
        success: function (newId) {

            $('#calendar').fullCalendar('removeEvents', ev.id);

            ev.id = parseInt(newId);
            ev.backgroundColor = eventColor;
            ev.borderColor = BORDER_COLOR;

            $('#calendar').fullCalendar('renderEvent', ev);
        }
    });

    $('#title').val('');
    $('#dialog').dialog('close');

    closeDialog('dialog');
}

/**
 * This function using for unchecking all checkboxes
 * and setting all fields in original state
 */
function closeDialog(divid) {
    $('#' + divid + ' :checkbox:enabled').prop('checked', false);
    $('#no-recurrent').prop('checked', true);
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#update-recurrent').hide();
    $('#creating').show();

}

function deleteRecurrentEvents(recurrentId) {
    allEvents.forEach(function (item) {
        if (item.recurrentId === recurrentId) {
            $('#calendar').fullCalendar('removeEvents', item.id);
            sendToServerForDelete(item);
        }
    });
}




