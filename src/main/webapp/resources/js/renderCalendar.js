/**
 * Created by dima- on 12.05.2016.
 */
var info_event;
var creatingEvent;
var allEvents;
var NOT_ACTIVE_EVENT = '#33cc33';
var BORDER_COLOR = '#000000';

$(function () {

    $('#update-recurrent-button').hide();
    $('#update-recurrent-booking').hide();

    $('#confirmation-dialog-event-div').dialog({
        autoOpen: false,
        modal:true,
        width: 350,
        modal: true
    });

    $('.modal-dialog-recurrently').dialog({
        modal: true,
        autoOpen: false
    });

    $('#dialog-recurrently').dialog({
        title: 'Recurrent events',
        modal: true,
        width: 500
    });

    $('#dialog').dialog({
        title: 'New event',
        modal: true,
        width: 550,
        autoOpen: false,
        beforeClose: function () {
            $('#deleting-recurrent-event').hide();
        },
        open: function(event, ui) {
            cleanValidationInfo();
            clearEventDialogSingleMulti();
        },
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    });


    $('.timepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
    });

    $('#updating').dialog({
        autoOpen: false,
        title: 'Change event',
        modal:true,
        width:400
    });

    $('#choose-updating-type').dialog({
        autoOpen: false,
        modal:true
    });


    $('#updatingButton').click(function () {
        if(validateUpdateSingleDialog()){
            updateSingleEvent();
        }
    });

    $('#deleting-recurrent-event').click(function () {
        $('#dialog').dialog('close');
        var myDialog = $('#confirmation-dialog-event-div');
        myDialog.dialog('open');
        $('#confirmYesEvent').click(function () {
            deleteRecurrentEvents(info_event.calEvent.recurrentId);
            clearEventDialogSingleMulti();
            myDialog.dialog('close');
        });
        $('#confirmNoEvent').click(function () {
            clearEventDialogSingleMulti();
            myDialog.dialog('close');
        });
    })
    $('#deleting-recurrent-event').hover(function(){
        $(this).css('color','red');
        $(this).css('cursor','pointer ');
    }, function(){
        $(this).css("color", "black");
    });

    $('#deleting-single-event').click(function () {
        $('#updating').dialog('close');
        var myDialog = $('#confirmation-dialog-event-div');
        myDialog.dialog('open');
        $('#confirmYesEvent').click(function () {
            sendToServerForDelete(info_event.calEvent);
            $('#calendar').fullCalendar('removeEvents', info_event.calEvent.id);
            myDialog.dialog('close');
        });
        $('#confirmNoEvent').click(function () {
            myDialog.dialog('close');
        })
    });
    $('#deleting-single-event').hover(function(){
        $(this).css('color','red');
        $(this).css('cursor','pointer ');
    }, function(){
        $(this).css("color", "black");
    });

    $('#cancel-update').click(function () {
        $('#updating').dialog('close');
    });

    $('#create-button').click(function () {
        if(isRadioButtonSelected(CREATE_EVENT_DIALOG_SINGLE_EVENT_RADIOBUTTON)){
            if(!validateEventDialogData(CREATE_SINGLE_EVENT))
                return;
        }else{
            if(!validateEventDialogData(CREATE_RECURRENT_EVENT))
                return;
        }
        createRecurrentEvents();

    });

    $('#recurrent').click(function () {
        $('#dialog-recurrently').dialog('open');

        $('#recurrent-event-start-date').val($('#start-date-picker').val());
        $('#recurrent-event-end-date').val($('#end-date-picker').val());
    });

    $('#cancel').click(function () {
        $('#start-date-picker').val('');
        clearEventDialogSingleMulti();
        $('#dialog').dialog('close');
        closeDialog('dialog');
    });

    $('#create-new-event').click(function () {
        var newEventDate = $('#calendar').fullCalendar('getDate').format();
        var currentDate = new Date();
        $('#start-date-picker').val(newEventDate.substring(0, 10));
        $('#end-date-picker').val(newEventDate.substring(0, 10));
        $('#start-time-picker').timepicker('setTime', currentDate.toLocaleTimeString());
        $('#end-time-picker').timepicker('setTime', increaseTimeByHour(currentDate.toLocaleTimeString()));
        $('#dialog').dialog('open');
    });

    $('.my-radio').click(function () {
        if ($('#weekly-radio-button').is(':checked')) {
            $('#days-for-recurrent-form').attr('hidden', false);
        } else {
            $('#days-for-recurrent-form').attr('hidden', true);
        }

        if ($('#single-event-radio-button').is(':checked')) {
            $('#end-date-picker').val($('#start-date-picker').val());
        }
    });

    $('#confirm-choose').click(function () {
        if ($('#single-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#updating').dialog('open');
        }

        if ($('#recurrent-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            editRecurrentEventRequest(info_event.calEvent.recurrentId);
        }
    });

    $('#cancel-choose').click(function () {
        $('#choose-updating-type').dialog('close');
        $('#single-update').attr('checked', true);
        $('#recurrent-update').attr('checked', false);

    });

    $('#update-recurrent-button').click(function () {
        if(validateEventDialogData(UPDATE_RECURRENT_EVENT)){
            deleteRecurrentEvents(info_event.calEvent.recurrentId);
            createRecurrentEvents();
        }
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
                         * This object is required for create-button empty calendar
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
    $('.timepicker').timepicker('option', 'minTime', workingHoursStart);
    $('.timepicker').timepicker('option', 'maxTime', workingHoursEnd);
    $('.timepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: workingHoursStart,
        maxTime: workingHoursEnd
    });
    $('#calendar').fullCalendar({
        slotDuration: '00:15:00',
        timeFormat : 'HH:mm',
        minTime: workingHoursStart,
        maxTime: workingHoursEnd,

        dayClick: function (date) {
            var clickDate = date.format();



            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }
            var currentDate = new Date();
            var neededTime = Number(clickDate.substring(11, 13))+1;
            var endClickDate = String(neededTime).concat(clickDate.substring(13, 19));
            $('#event-title').val('');
            $('#start-date-picker').val(clickDate.substring(0, 10));
            $('#end-date-picker').val(clickDate.substring(0, 10));
            if (clickDate.substring(11, 19) == "00:00:00"){
                $('#start-time-picker').timepicker('setTime', currentDate.toLocaleTimeString());
                $('#end-time-picker').timepicker('setTime', increaseTimeByHour(currentDate.toLocaleTimeString()))}
            else {$('#start-time-picker').timepicker('setTime', clickDate.substring(11, 19));
                $('#end-time-picker').timepicker('setTime', endClickDate)}
            $('#dialog').dialog('open');
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
            $('#color-select-single-event').val(calEvent.color)

            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);

            $('#startTimeUpdate').timepicker('setTime', newDate);
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);

            $('#start-time-picker').timepicker('setTime', newDate);
            $('#end-time-picker').timepicker('setTime', newDateForEnd);

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

            $('#start-date-picker').val(start.format().substring(0, 10));
            $('#end-date-picker').val(end.format().substring(0, 10));

            var timeStart = makeUTCTime(new Date(), new Date(start.format()));
            var timeEnd = makeUTCTime(new Date(), new Date(end.format()));

            $('#start-time-picker').timepicker('setTime', timeStart);
            $('#end-time-picker').timepicker('setTime', timeEnd);
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

function updateSingleEvent(){
    $('#calendar').fullCalendar('removeEvents', info_event.calEvent.id);
    var eventForUpdate = {
        id: info_event.calEvent.id,
        title: $('#titleUpdate').val(),
        start: makeISOTime(info_event.calEvent.start.format(), 'startTimeUpdate'),
        end: makeISOTime(info_event.calEvent.end.format(), 'endTimeUpdate'),
        editable: false,
        description: $('#descriptionUpdate').val(),
        color: $('#color-select-single-event').val(),
    };

    $('#calendar').fullCalendar('renderEvent', eventForUpdate);

    sendToServerForUpdate(eventForUpdate, info_event.roomID);

    $('#updating').dialog('close');
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
            description: event.description,
            color: event.color
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

    var startDateForCreatingRecurrentEvents = $('#start-date-picker').val() + 'T00:00:00';
    var endDate = $('#end-date-picker').val() + 'T00:00:00';
    var eventColor = $('#color-select').val();

    var ev = {
        id: -1,
        title: $('#event-title').val(),
        start: makeISOTime(startDateForCreatingRecurrentEvents, 'start-time-picker'),
        end: makeISOTime(endDate, 'end-time-picker'),
        backgroundColor: NOT_ACTIVE_EVENT,
        borderColor: NOT_ACTIVE_EVENT,
        editable: false,
        description: $('#description').val(),
        color:eventColor
    };
    // ====================== In this part recurrent events are create======
    if ($('#weekly-radio-button').is(':checked')) {
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

        $('#start-date-picker').val('');
        clearEventDialogSingleMulti();
        $('#dialog').dialog('close');

        closeDialog('dialog');
        return;
    }
    //======================================================================
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

    $('#start-date-picker').val('');
    clearEventDialogSingleMulti();
    $('#dialog').dialog('close');

    closeDialog('dialog');
}

/**
 * This function using for unchecking all checkboxes
 * and setting all fields in original state
 */
function closeDialog(divid) {
    $('#' + divid + ' :checkbox:enabled').attr('checked', false);
    $('#single-event-radio-button').attr('checked', true);
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#update-recurrent-button').hide();
    $('#create-button').show();

}

function deleteRecurrentEvents(recurrentId) {
    allEvents.forEach(function (item) {
        if (item.recurrentId === recurrentId) {
            $('#calendar').fullCalendar('removeEvents', item.id);
            sendToServerForDelete(item);
        }
    });
}
function increaseTimeByHour(date){
    var currentDate = new Date();
    var endTimeHours = String(currentDate.getHours()+1);
    return endTimeHours.concat(date.substring(2, 8));
}

function editRecurrentEventRequest(eventRecurrentId) {
    var recurrentEventForEditing = {};
    var path = 'getRecurrentEventForEditing/' + eventRecurrentId;
    $.ajax({
        url: path,
        success: function (result) {
            if (result.length) {
                result = JSON.parse(result);
                recurrentEventForEditing = {
                    recurentId: result.recurrentId,
                    color: result.color,
                    description: result.description,
                    startDate: result.startTime.substr(0, 10),
                    startTime: result.startTime.substr(11, 5),
                    endDate: result.endTime.substr(0, 10),
                    endTime: result.endTime.substr(11, 5),
                    daysOfweek: result.daysOfWeek.trim(),
                    title:result.name,
                }
                console.log("title="+recurrentEventForEditing.title);
                console.log("name="+result.name);
                console.log("description="+recurrentEventForEditing.description);
                console.log("color="+recurrentEventForEditing.color);
            }
            editRecurrentEvent(recurrentEventForEditing);
        },
    })
}
function editRecurrentEvent(recurrentEventForEditing){
    $('#dialog').dialog('open');
    $('#update-recurrent-button').show();
    $('#create-button').hide();
    $('#deleting-recurrent-event').show();
    $('#weekly-radio-button').prop( "checked", true );
    $('#single-event-radio-button').prop( "checked", false );
    $("#single-event-radio-button").prop("disabled", true);
    $('#days-for-recurrent-form').attr("hidden", false);

    $('#start-date-picker').val(recurrentEventForEditing.startDate);
    $('#end-date-picker').val(recurrentEventForEditing.endDate);
    $('#start-time-picker').timepicker('setTime', recurrentEventForEditing.startTime);
    $('#end-time-picker').timepicker('setTime', recurrentEventForEditing.endTime);
    $('#color-select').val(recurrentEventForEditing.color);
    $('#description').val(recurrentEventForEditing.description);
    $('#event-title').val(recurrentEventForEditing.title);
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    var i = 0;
    var DoW = recurrentEventForEditing.daysOfweek.split(" ");
    DoW.forEach(function (item) {
        switch(item) {
            case 'Mon':
                day = 'Monday';
                break;
            case 'Tue':
                day = 'Tuesday';
                break;
            case 'Wed':
                day = 'Wednesday';
                break;
            case 'Thu':
                day = 'Thursday';
                break;
            case 'Fri':
                day = 'Friday';
                break;
            case 'Sat':
                day = 'Saturday';
                break;
        }
        $('#' + day).prop('checked', true);
    });


}

function clearEventDialogSingleMulti(){
    $('#create-button').show();
    $('#update-recurrent-button').hide();
    $('#deleting-recurrent-event').hide();
    $("#single-event-radio-button").prop("disabled", false);
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#weekly-radio-button').prop( "checked", false )
    $('#single-event-radio-button').prop( "checked", true )
    checkBoxesDays.forEach(function (item) {
        $('#' + item).attr('checked', false);
    });
    $('#color-select').val("#1ba1e2");
    $('#event-title').val("");
    $('#description').val("");

}

