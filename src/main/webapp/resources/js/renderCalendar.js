var info_event;
var creatingEvent;
var allEvents;
const NOT_ACTIVE_EVENT = '#33cc33';
const BORDER_COLOR = '#000000';
const DISCOUNT_COLOR = '#F00000';
const DAYS_IN_MONTH = 31;
const DAYS_IN_WEEK = 7;

$(function () {
    $('#update-recurrent-button').hide();
    $('#update-recurrent-booking').hide();

    $('#confirmation-dialog-event-div').dialog({
        autoOpen: false,
        modal: true,
        width: 350
    });

    $('#cancel-event-dialog').dialog({
        autoOpen: false,
        modal: true,
        width: 320,
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
        modal: true,
        width: 550,
        autoOpen: false,
        beforeClose: function () {
            $('#deleting-recurrent-event').hide();
        },
        open: function (event, ui) {
            cleanValidationInfo();
            clearEventDialogSingleMulti();
        },
        show: {
            effect: 'drop',
            duration: 250
        },
        hide: {
            effect: 'drop',
            duration: 250
        }
    });

    $('.timepicker').timepicker({
        timeFormat: 'H:i',
        step: 1
    });

    $('#updating').dialog({
        autoOpen: false,
        modal: true,
        width: 400
    });

    $('#choose-updating-type').dialog({
        autoOpen: false,
        modal: true
    });

    $('#updatingButton').click(function () {
        cleanGeneralValidationInfo(GENERAL_ERROR_FIELD);
        if (isSingleUpdateFormValid()) {
            updateSingleEvent();
        } else {
            printGeneralMessage(GENERAL_ERROR_FIELD);
        }
    });

    /**
     * Deletes all recurrent events with given id starting from startDate
     * and ending with endDate.
     *
     * @param recurrentId
     * @param startDate
     * @param endDate
     */
    function deleteRecurrentEventsForTime(recurrentId, startDate, endDate) {
        var arStartDates, arEndDates;
        $.each(allEvents, function (index, item) {
            if (item.recurrentId === recurrentId) {
                arStartDates = item.start.split('T');
                arEndDates = item.end.split('T');
                if (new Date(arStartDates[0]) >= new Date(startDate) &&
                    new Date(arEndDates[0]) <= new Date(endDate)) {
                    $('#calendar').fullCalendar('removeEvents', item.id);
                    sendToServerForDelete(item);
                }
            }
        });
    }

    /**
     * Deleting recurrent event
     */
    $('#deleting-recurrent-event').click(function () {
        $('#dialog').dialog('close');
        $('#cancel-event-dialog').dialog('open');
        $('#yes-cancel-event-button').click(function () {
            var startDate = $('#start-date-cancel-picker').val();
            var endDate = $('#end-date-cancel-picker').val();

            if (!validateRecurrentDates(info_event.calEvent.recurrentId, startDate, endDate))
                return;
            deleteRecurrentEventsForTime(info_event.calEvent.recurrentId, startDate, endDate);
            $('#cancel-event-dialog').dialog('close');
        });
        $('#no-cancel-event-button').click(function () {
            $('#cancel-event-dialog').dialog('close');
        });
    });

    $('#deleting-recurrent-event').hover(function () {
        $(this).css('color', 'red');
        $(this).css('cursor', 'pointer ');
    }, function () {
        $(this).css('color', 'black');
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
        });
    });

    $('#deleting-single-event').hover(function () {
        $(this).css('color', 'red');
        $(this).css('cursor', 'pointer ');
    }, function () {
        $(this).css('color', 'black');
    });

    $('#cancel-update').click(function () {
        $('#updating').dialog('close');
    });

    $('#create-button').click(function () {
        cleanGeneralValidationInfo(GENERAL_ERROR_FIELD);
        if (isRadioButtonSelected(CREATE_EVENT_DIALOG_SINGLE_EVENT_RADIOBUTTON)) {
            if (!isCreateEventFormValid(CREATE_SINGLE_EVENT)) {
                printGeneralMessage(GENERAL_ERROR_FIELD);
                return;
            }
        } else if (isRadioButtonSelected(CREATE_EVENT_DIALOG_WEEKLY_EVENT_RADIOBUTTON)) {
            if (!isCreateEventFormValid(CREATE_RECURRENT_EVENT)) {
                printGeneralMessage(GENERAL_ERROR_FIELD);
                return;
            }
        } else if (isRadioButtonSelected(CREATE_EVENT_DIALOG_MONTHLY_EVENT_RADIOBUTTON)) {
            if (!validateEventDialogData(CREATE_MONTHLY_EVENT))
                return;
        }
        createSingleOrRecurrentEvents();
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
        $('#single-event-radio-button').prop("checked", true);
        $('#start-date-picker').val(moment().format(constants.parameters.dateFormatUpperCase));
        setEndDateForSingle();
        $('#start-time-picker').timepicker('setTime', currentDate.toLocaleTimeString());
        $('#end-time-picker').timepicker('setTime', increaseTimeByHour(currentDate.toLocaleTimeString()));
        buildTableMonthly();
        $('#dialog').dialog('open');
    });

    if ($('#single-event-radio-button').is(':checked')) {
        setEndDateForSingle();
    }

    $('.my-radio').click(function () {
        $('#end-date-picker').attr('disabled', false);
        if ($('#weekly-radio-button').is(':checked')) {
            $('#days-for-recurrent-form').attr('hidden', false);
            $('#days-for-monthly-form').attr('hidden', true);
            $('#end-date-picker').attr('disabled', false);
            $('#dialog').off('change', '#start-date-picker');
        } else {
            $('#days-for-monthly-form').attr('hidden', true);
        }
        if ($('#monthly-radio-button').is(':checked')) {
            $('#days-for-monthly-form').attr('hidden', false);
            $('#days-for-recurrent-form').attr('hidden', true);
            $('#dialog').off('change', '#start-date-picker');
        }
        if ($('#single-event-radio-button').is(':checked')) {
            $('#days-for-monthly-form').attr('hidden', true);
            $('#days-for-recurrent-form').attr('hidden', true);
            setEndDateForSingle();
        }
    });

    $('#confirm-choose').click(function () {
        if ($('#single-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#updating').dialog('open');
        }

        if ($('#recurrent-update').is(':checked')) {
            $('#choose-updating-type').dialog('close');
            $('#dialog').off('change', '#start-date-picker');
            editRecurrentEventRequest(info_event.calEvent.recurrentId);
        }
    });

    $('#cancel-choose').click(function () {
        $('#choose-updating-type').dialog('close');
        $('#single-update').prop('checked', true);
    });

    $('#update-recurrent-button').click(function () {
        cleanGeneralValidationInfo(GENERAL_ERROR_FIELD);
        var valid = false;
        if (isRadioButtonChecked(CREATE_EVENT_DIALOG_WEEKLY_EVENT_RADIOBUTTON)) {
            valid = isCreateEventFormValid(UPDATE_RECURRENT_EVENT);
            if (!valid) {
                printGeneralMessage(GENERAL_ERROR_FIELD);
            }
        }
        else {
            valid = validateEventDialogData(UPDATE_MONTHLY_EVENT);
        }
        if (valid) {
            createSingleOrRecurrentEvents(info_event.calEvent.recurrentId);
        }
    });
});

function setEndDateForSingle() {
    $('#end-date-picker').val($('#start-date-picker').val()).attr('disabled', true);
    $('#dialog').on('change', '#start-date-picker', function () {
        $('#end-date-picker').val($('#start-date-picker').val());
    });
}
/**
 *  This function gets events from controller.
 *  After that call renderCalendarForManager for rendering
 */

function selectRoomForManager(id) {

    $('#calendar').fullCalendar('destroy');

    $.ajax({
        url: 'getroomproperty/' + id,
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        success: function (result) {
            result = result.split(' ');

            result[0] += ':00';
            result[1] += ':00';

            const startTime = result[0];
            const endTime = result[1];

            $.ajax({
                url: 'getevents/' + id,
                encoding: 'UTF-8',
                contentType: 'charset=UTF-8',
                success: function (result) {
                    let objects = [];
                    result = JSON.parse(result);
                    for (let i = 0; i < result.length; i++) {
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
                        };
                    }

                    renderDiscountsInCalendarForManager(objects, id, startTime, endTime);
                }
            });
        }
    });
}

function renderDiscountsInCalendarForManager(objects, roomID, workingHoursStart, workingHoursEnd) {
    const eventLength = objects.length;

    $.ajax({
        url: "restful/discount/all",
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        success: function (result) {
            if (result.length || objects.length) {
                for (let i = 0; i < result.length; i++) {
                    objects[eventLength + i] = {
                        title: result[i].value + "% - " + result[i].reason,
                        start: formatDate(result[i].startDate) + "T" + result[i].startTime,
                        end: formatDate(result[i].endDate) + "T" + result[i].endTime,
                        editable: false,
                        borderColor: BORDER_COLOR,
                        color: DISCOUNT_COLOR
                    };
                }

                renderCalendarForManager(objects, roomID, workingHoursStart, workingHoursEnd);
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
                 renderCalendarForManager(objects, roomID, workingHoursStart, workingHoursEnd);
            }
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
    var timepicker = $('.timepicker');
    timepicker.timepicker('option', 'minTime', workingHoursStart);
    timepicker.timepicker('option', 'maxTime', workingHoursEnd);
    timepicker.timepicker({
        timeFormat: 'H:i',
        step: 1,
        minTime: workingHoursStart,
        maxTime: workingHoursEnd
    });
    $('#calendar').fullCalendar({
        slotDuration: '00:15:00',
        timeFormat: 'HH:mm',
        minTime: workingHoursStart,
        maxTime: workingHoursEnd,

        dayClick: function (date) {
            var clickDate = date.format();
            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }
            var currentDate = new Date();
            var neededTime = Number(clickDate.substring(11, 13)) + 1;
            var endClickDate = String(neededTime).concat(clickDate.substring(13, 19));
            $('#event-title').val('');
            $('#start-date-picker').val(clickDate.substring(0, 10));
            $('#end-date-picker').val(clickDate.substring(0, 10));
            if (clickDate.substring(11, 19) == '00:00:00') {
                $('#start-time-picker').timepicker('setTime', currentDate.toLocaleTimeString());
                $('#end-time-picker').timepicker('setTime', increaseTimeByHour(currentDate.toLocaleTimeString()));
            }
            else {
                $('#start-time-picker').timepicker('setTime', clickDate.substring(11, 19));
                $('#end-time-picker').timepicker('setTime', endClickDate);
            }
            $('#dialog').dialog('open');
            creatingEvent.clickDate = clickDate;
            creatingEvent.roomID = roomID;
            buildTableMonthly();
        },

        eventClick: function (calEvent) {
            if (calEvent.color === NOT_ACTIVE_EVENT || calEvent.color === DISCOUNT_COLOR) {
                return;
            }

            $('#titleUpdate').val(calEvent.title);
            $('#startDayUpdate').val(calEvent.start.format().substring(0, 10));
            $('#endDateUpdate').val(calEvent.end.format().substring(0, 10));
            $('#descriptionUpdate').val(calEvent.description);
            $('#color-select-single-event').val(calEvent.color);
            
            var newDate = calEvent.start.format().substring(11, 16);
            var newDateForEnd = calEvent.end.format().substring(11, 16);

            $('#startTimeUpdate').timepicker('setTime', newDate);
            $('#endTimeUpdate').timepicker('setTime', newDateForEnd);

            $('#start-time-picker').timepicker('setTime', newDate);
            $('#end-time-picker').timepicker('setTime', newDateForEnd);

            info_event.calEvent = calEvent;
            info_event.roomID = roomID;
            info_event.description = $('#descriptionUpdate').val();

            if (calEvent.recurrentId) {
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

function makeUTCTime(time, date) {
    time.setHours(date.getUTCHours());
    time.setMinutes(date.getUTCMinutes());
    time.setSeconds(date.getUTCSeconds());
    return time;
}

/**
 * This function using for unchecking all checkboxes
 * and setting all fields in original state
 */

function increaseTimeByHour(date) {
    var currentDate = new Date();
    var endTimeHours = String(currentDate.getHours() + 1);
    return endTimeHours.concat(date.substring(2, 8));
}

function createSingleOrRecurrentEvents(idIfEdited) {
    $('.loading').show();
    var startDateForCreatingRecurrentEvents = $('#start-date-picker').val() + 'T00:00:00';
    var endDate = $('#end-date-picker').val() + 'T00:00:00';
    var eventColor = $('#color-select').val();

    var ev = {
        id: -1,
        recurrentId: idIfEdited,
        title: $('#event-title').val(),
        start: makeISOTime(startDateForCreatingRecurrentEvents, 'start-time-picker'),
        end: makeISOTime(endDate, 'end-time-picker'),
        backgroundColor: NOT_ACTIVE_EVENT,
        borderColor: NOT_ACTIVE_EVENT,
        editable: false,
        description: $('#description').val(),
        color: eventColor,
    };
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
        sendRecurrentEventsForCreate(ev, dayWhenEventIsRecurrent);
    } else if ($('#monthly-radio-button').is(':checked')) {
        var daysWhenEventIsRecurrent = [];
        $('#monthly-days').find('.active').each(function () {
            daysWhenEventIsRecurrent.push(this.innerHTML);
        });
        sendMonthlyEventsForCreate(ev, daysWhenEventIsRecurrent);
    } else {
        $('#calendar').fullCalendar('renderEvent', ev, true);
        $('#calendar').fullCalendar('unselect');
        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'createsingleevent',
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
                $('#start-date-picker').val('');
                clearEventDialogSingleMulti();
                $('#dialog').dialog('close');
                closeDialog('dialog');
            },
            error: function (xhr) {
                if (xhr.status == 406) {
                    $('#calendar').fullCalendar('removeEvents', ev.id);
                    $('#dialog').dialog('close');
                    closeDialog('dialog');
                    callErrorDialog(xhr['responseText']);
                } else {
                    $('#calendar').fullCalendar('removeEvents', ev.id);
                    printServerError(GENERAL_ERROR_FIELD, xhr['responseText']);
                }
            },
            complete: function () {
                $('.loading').hide();
            }
        });
    }
}
function printServerError(errorField, responsetext) {
    $("." + errorField).html(responsetext);
}
function sendRecurrentEventsForCreate(recurrentEvents, dayWhenEventIsRecurrent) {
    var stringWithDaysForRecurrent = '';
    dayWhenEventIsRecurrent.forEach(function (item) {
        stringWithDaysForRecurrent += item + ' ';
    });

    stringWithDaysForRecurrent.substring(0, stringWithDaysForRecurrent.length - 1);
    $.ajax({
        type: 'post',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        url: 'getrecurrentevents',
        dataType: 'json',
        data: JSON.stringify({
            recurrentId: recurrentEvents.recurrentId,
            name: recurrentEvents.title,
            startTime: recurrentEvents.start,
            endTime: recurrentEvents.end,
            daysOfWeek: stringWithDaysForRecurrent,
            roomId: localStorage['roomId'],
            description: recurrentEvents.description,
            color: recurrentEvents.color,
            borderColor: BORDER_COLOR
        }),
        success: function (result) {
            if (recurrentEvents.recurrentId)
                deleteRecurrentEvents(recurrentEvents.recurrentId);
            popSetOfEvents(result);
            $('#dialog').dialog('close');
        },
        complete: function (xhr) {
            if (xhr.status == 406) {
                deleteRecurrentEvents(recurrentEvents.recurrentId);
                $('#dialog').dialog('close');
                closeDialog('dialog');
                callErrorDialog(xhr['responseText']);
            } else if (xhr.status == 400) {
                deleteRecurrentEvents(recurrentEvents.recurrentId);
                printServerError(GENERAL_ERROR_FIELD, xhr['responseText']);
            }
            $('.loading').hide();
        }
    });
}
function popSetOfEvents(set) {
    if (set.length) {
        set.forEach(function (item, i) {
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
        });
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('addEventSource', allEvents);
        $('#calendar').fullCalendar('refetchEvents');
        animateCalendar(set[0].startTime, set[0].endTime, set[0].name);
    }
}

function animateCalendar(startTime, endTime, setName) {
    startTime = new Date(startTime);
    endTime = new Date(endTime);
    var findTime = '.fc-content .fc-time';
    var findName = '.fc-content .fc-title';

    function getTime(date) {
        time = '';
        if (date.getHours() < constants.parameters.timeZone) {
            time += (date.getHours() - constants.parameters.timeZone + constants.parameters.hoursInDay);
        } else if ((date.getHours() - constants.parameters.timeZone) < 10) {
            time += '0' + (date.getHours() - constants.parameters.timeZone);
        } else
            time += (date.getHours() - constants.parameters.timeZone);
        if ((date.getMinutes()) < 10) {
            time += ':0' + date.getMinutes();
        } else
            time += ':' + date.getMinutes();
        return time;
    }

    var setTime = getTime(startTime);
    if ($('.fc-state-active').hasClass('fc-agendaWeek-button') ||
        $('.fc-state-active').hasClass('fc-agendaDay-button')) {
        setTime += ' - ' + getTime(endTime);
        findTime += ' span';
    }
    var elementsToAnimate = [];
    $('#calendar').find(findTime).each(function () {
        if (this.innerHTML == setTime) {
            elementsToAnimate.push($(this).closest('.fc-event'));
        }
    });
    elementsToAnimate.forEach(function (item, i) {
        $(item).find(findName).each(function () {
            if (this.innerHTML == setName) {
                $(this).closest('.fc-event').addClass('animated').addClass('pulse').addClass('infinite');
            }
        });
    });
    setTimeout(function () {
        $('.fc-event').removeClass("animated").removeClass("pulse").removeClass("infinite");
    }, 4000);
}
function sendMonthlyEventsForCreate(recurrentEvents, dayWhenEventIsRecurrent) {
    $.ajax({
        type: 'post',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        url: 'getmonthlyevents',
        dataType: 'json',
        data: JSON.stringify({
            recurrentId: recurrentEvents.recurrentId,
            name: recurrentEvents.title,
            startTime: recurrentEvents.start,
            endTime: recurrentEvents.end,
            daysOfMonth: dayWhenEventIsRecurrent,
            roomId: localStorage['roomId'],
            description: recurrentEvents.description,
            color: recurrentEvents.color,
            borderColor: BORDER_COLOR
        }),
        success: function (result) {
            $('#dialog').dialog('close');
            if (result.eventsCreated.length) {
                if (result.datesWhenNotCreated.length) {
                    eventsWereNotCreated(result.datesWhenNotCreated, result.eventsCreated[0].recurrentId);
                }
                if (recurrentEvents.recurrentId)
                    deleteRecurrentEvents(recurrentEvents.recurrentId);
                popSetOfEvents(result.eventsCreated);
            } else {
                callErrorDialog(messages.event.errors.noEligibleEventsForPeriod);
            }
        },
        error: function (xhr) {
            $('#dialog').dialog('close');
            callErrorDialog(xhr['responseText']);
        },
        complete: function () {
            $('.loading').hide();
        }
    });
}

function deleteRecurrentEvents(recurrentId) {
    var remainingAllEvents = [];
    allEvents.forEach(function (item) {
        if (item.recurrentId !== recurrentId) {
            remainingAllEvents.push(item);
        }
    });
    allEvents = remainingAllEvents;
}

function updateSingleEvent() {
    var eventForUpdate = {
        id: info_event.calEvent.id,
        title: $('#titleUpdate').val(),
        start: makeISOTime(info_event.calEvent.start.format(), 'startTimeUpdate'),
        end: makeISOTime(info_event.calEvent.end.format(), 'endTimeUpdate'),
        editable: false,
        description: $('#descriptionUpdate').val(),
        color: $('#color-select-single-event').val(),
    };
    sendToServerForUpdate(eventForUpdate, info_event.roomID);

}
function sendToServerForUpdate(event, roomID) {
    cleanGeneralValidationInfo(GENERAL_ERROR_FIELD);
    $.ajax({
        type: 'post',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
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
        }),
        success: function () {
            $('#calendar').fullCalendar('removeEvents', event.id);
            event.borderColor = BORDER_COLOR;
            $('#calendar').fullCalendar('renderEvent', event, true);
            $('#updating').dialog('close');
        },
        error: function (xhr) {
            if (xhr.status == 406) {
                $('#updating').dialog('close');
                callErrorDialog(xhr['responseText']);
            } else
                printServerError(GENERAL_ERROR_FIELD, xhr['responseText']);
        }
    });
}

function sendToServerForDelete(event) {
    $.ajax({
        type: 'post',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
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

function editRecurrentEvent(recurrentEventForEditing) {
    $('#dialog').dialog('option', 'title', messages.event.info.changeEvent);
    $('#dialog').dialog('open');
    $('#update-recurrent-button').show();
    $('#create-button').hide();
    $('#deleting-recurrent-event').show();
    $('#single-event-radio-button').prop('checked', false);
    buildTableMonthly();
    if (recurrentEventForEditing) {
        $('#start-date-picker').val(recurrentEventForEditing.startDate);
        $('#start-date-cancel-picker').val(recurrentEventForEditing.startDate);
        $('#end-date-picker').val(recurrentEventForEditing.endDate);
        $('#end-date-picker').prop('disabled', false);
        $('#end-date-cancel-picker').val(recurrentEventForEditing.endDate);
        $('#start-time-picker').timepicker('setTime', recurrentEventForEditing.startTime);
        $('#end-time-picker').timepicker('setTime', recurrentEventForEditing.endTime);
        $('#color-select').val(recurrentEventForEditing.color);
        $('#description').val(recurrentEventForEditing.description);
        $('#event-title').val(recurrentEventForEditing.title);
        $('#event-cancel-title').html(recurrentEventForEditing.title);

        if (recurrentEventForEditing.monthDays) {
            $('#monthly-radio-button').prop('checked', true);
            $('#days-for-monthly-form').attr('hidden', false);
            $('#days-for-recurrent-form').attr('hidden', true);
            $('#weekly-radio-button').prop('checked', false);

            recurrentEventForEditing.monthDays.forEach(function (item) {
                $('#monthly-days').find('td').each(function () {
                    if (item == this.innerHTML) {
                        $(this).addClass('active');
                    }
                });
            });
        }
        if (recurrentEventForEditing.weekDays) {
            $('#weekly-radio-button').prop('checked', true);
            $('#monthly-radio-button').prop('checked', false);
            $('#days-for-monthly-form').attr('hidden', true);
            $('#days-for-recurrent-form').attr('hidden', false);
            recurrentEventForEditing.weekDays.forEach(function (item) {
                switch (item) {
                    case 2:
                        day = 'Monday';
                        break;
                    case 3:
                        day = 'Tuesday';
                        break;
                    case 4:
                        day = 'Wednesday';
                        break;
                    case 5:
                        day = 'Thursday';
                        break;
                    case 6:
                        day = 'Friday';
                        break;
                    case 7:
                        day = 'Saturday';
                        break;
                }
                $('#' + day).prop('checked', true);
            });
        }
    }
}

function editRecurrentEventRequest(eventRecurrentId) {
    var recurrentEventForEditing = {};
    var path = 'getRecurrentEventForEditing/' + eventRecurrentId;
    $.ajax({
        type: 'GET',
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        dataType: 'json',
        url: path,
        success: function (result) {
            recurrentEventForEditing = {
                recurentId: result.recurrentId,
                color: result.color,
                description: result.description,
                startDate: result.startTime.substr(0, 10),
                startTime: result.startTime.substr(11, 5),
                endDate: result.endTime.substr(0, 10),
                endTime: result.endTime.substr(11, 5),
                weekDays: result.weekDays,
                monthDays: result.daysOfTheMonth,
                title: result.name
            };
            editRecurrentEvent(recurrentEventForEditing);
        }
    });
}

function clearEventDialogSingleMulti() {
    $('#create-button').show();
    $('#update-recurrent-button').hide();
    $('#deleting-recurrent-event').hide();
    $('#single-event-radio-button').prop('disabled', false);
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    $('#days-for-monthly-form').attr('hidden', true);
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#weekly-radio-button').prop('checked', false);
    $('#single-event-radio-button').prop('checked', true);
    checkBoxesDays.forEach(function (item) {
        $('#' + item).attr('checked', false);
    });
    $('#color-select').val('#1ba1e2');
    $('#event-title').val('');
    $('#description').val('');
}

function buildTableMonthly() { //generating table for DAYS_IN_MONTH days
    var tableHTML = '';
    var dayNum = 0;
    while (dayNum <= DAYS_IN_MONTH) {
        tableHTML += '<tr>';
        for (var j = 0; j < DAYS_IN_WEEK; j++) {
            dayNum++;
            if (dayNum > DAYS_IN_MONTH) break;
            tableHTML += ('<td>' + (dayNum) + '</td>');
        }
        tableHTML += '</tr>';
    }
    $('#monthly-days').html(tableHTML);
    $('#monthly-days').find('td').click(function () {
        $(this).toggleClass('active');
    });
}

function closeDialog(divid) {
    $('#' + divid + ' :checkbox:enabled').attr('checked', false);
    $('#single-event-radio-button').attr('checked', true);
    $('#days-for-recurrent-form').attr('hidden', true);
    $('#update-recurrent-button').hide();
    $('#create-button').show();
}
