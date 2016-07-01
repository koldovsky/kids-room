/**
 * Created by dima- on 12.05.2016.
 */

var beforeUpdate;
var info;
var creatingEvent;
var allEvents;
var ACTIVE_EVENT = '#6AA4C1';
var NOT_ACTIVE_EVENT = '#33cc33';

$(function () {

    $('#update-recurrent').hide();

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
        autoOpen: false
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

    $('#updating').dialog({
        autoOpen: false,
        title: 'Change event'
    });

    $('#choose-updating-type').dialog({
        autoOpen: false
    }).closest(".ui-dialog")
        .find(".ui-dialog-title")
        .html("This is one appointment <br> is a series.<br>What would you like to open");

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
   /*     creatingEvent.clickDate = $('#title').val();
        creatingEvent.clickDate = creatingEvent.clickDate + 'T00:00:00';

        var endDate = $('#endDate').val() +'T00:00:00';

        var eventColor = $('#color-select').val();

        var ev = {
            id: -1,
            title: $('#startDate').val(),
            start: makeISOTime(creatingEvent.clickDate, 'basicExample'),
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
                roomId: localStorage["roomId"],
                description: ev.description,
                color: eventColor
            }),
            success: function (result) {
                var newId = parseInt(result);

                $('#calendar').fullCalendar('removeEvents', ev.id);

                ev.id = newId;
                ev.backgroundColor = eventColor;
                ev.borderColor = '#000000';

                $('#calendar').fullCalendar('renderEvent', ev);
            }
        });

        $('#title').val('');
        $('#dialog').dialog('close');
        closeDialog('dialog');*/

        createRecurrentEvents();
    });

    $('#recurrent').click(function () {
        $('#dialog-recurrently').dialog('open');

        $('#recurrent-event-start-date').val($('#title').val());
        $('#recurrent-event-end-date').val($('#endDate').val());
    });

    $('#cancel').click(function (){
        $('#title').val('');
        $('#dialog').dialog('close');
        closeDialog('dialog');
    });

    $('#create-new-event').click(function () {
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

    $('#confirm-choose').click(function (){
        if($('#single-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#updating').dialog('open');
        }

        if($('#recurrent-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#update-recurrent').show();
            $('#creating').hide();
            $('#dialog').dialog('open');
        }
    });

    $('#cancel-choose').click(function () {
        $('#choose-updating-type').dialog('close');

        $("#single-update").prop("checked", true);

        $("#recurrent-update").prop("checked", false);
    });

    $('#update-recurrent').click(function (){

        for(var i = 0; i < allEvents.length; i++) {
            if(allEvents[i].recurrentId === info.calEvent.recurrentId) {
                $('#calendar').fullCalendar('removeEvents', allEvents[i].id);
                sendToServerForDelete(allEvents[i]);
            }
        }

        createRecurrentEvents();
    });
});

/**
 *  This function gets events from controller.
 *  After that call renderCalendarForManager for rendering
 */
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
                        description: result[i].description,
                        color: result[i].color,
                        borderColor: '#000000',
                        recurrentId: result[i].recurrentId
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

/**
 *  This function gets array of events and render calendar.
 *  dayClick methods and eventClick methods implemented here
 */
function renderCalendarForManager(objects, roomID) {
    info = {};
    creatingEvent = {};
    allEvents = objects;

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

            creatingEvent.clickDate = clickDate;
            creatingEvent.roomID = roomID;
        },

        eventClick: function (calEvent) {

            if (calEvent.color === NOT_ACTIVE_EVENT) {
                return;
            }

            beforeUpdate = calEvent.title;

            $('#titleUpdate').val(calEvent.title);                                  //назва
            $('#startDayUpdate').val(calEvent.start.format().substring(0, 10));     //дата початку
            $('#endDateUpdate').val(calEvent.end.format().substring(0, 10));        //дата кінця
            $('#descriptionUpdate').val(calEvent.description);                      //опис


            $('#startDate').val(calEvent.title);                                  //назва
            $('#title').val(calEvent.start.format().substring(0, 10));     //дата початку
            $('#endDate').val(calEvent.end.format().substring(0, 10));        //дата кінця
            $('#description').val(calEvent.description);


            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);

            $('#startTimeUpdate').timepicker('setTime', newDate);                   //час початку
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);               //час кінця

            $('#basicExample').timepicker('setTime', newDate);                   //час початку
            $('#ender').timepicker('setTime', newDateForEnd);               //час кінця

            info.beforeUpdate = beforeUpdate;
            info.endDate = endDate;
            info.calEvent = calEvent;
            info.roomID = roomID;
            info.date = date;
            info.description = $('#descriptionUpdate').val();


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
            roomId: localStorage["roomId"],
            description: recurrentEvents.description,
            color: eventColor,
            borderColor: '#000000'
        }),
        success: function (result) {
            alert(result);
        }
    });
}

function createRecurrentEvents() {
    creatingEvent.clickDate = $('#title').val();
    creatingEvent.clickDate = creatingEvent.clickDate + 'T00:00:00';

    var endDate = $('#endDate').val() +'T00:00:00';

    var eventColor = $('#color-select').val();

    var ev = {
        id: -1,
        title: $('#startDate').val(),
        start: makeISOTime(creatingEvent.clickDate, 'basicExample'),
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
            roomId: localStorage["roomId"],
            description: ev.description,
            color: eventColor
        }),
        success: function (result) {
            var newId = parseInt(result);

            $('#calendar').fullCalendar('removeEvents', ev.id);

            ev.id = newId;
            ev.backgroundColor = eventColor;
            ev.borderColor = '#000000';

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
    $("#no-recurrent").prop("checked", true);
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#update-recurrent').hide();
    $('#creating').show();

}