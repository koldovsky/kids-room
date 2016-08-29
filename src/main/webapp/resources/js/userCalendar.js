var info;
var bookingsArray;
var bookingDate;
var roomIdForHandler;
var usersID;
var clickedEvent;
var clickedEventRecurrentId;
var BORDER = '#4caf50';
var BOOKING = '#4caf50';
var NOT_SYNCHRONIZED = '#068000';
var BLOCKED = '#ff0000'
var allBookings;
var blockedTimeSpans;

$(function () {

    $('#recurrent-change').dialog({
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

    $('#bookingForm').dialog({
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

    $('#bookingUpdatingDialog').dialog({
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

    $('#confirmation-dialog-div').dialog({
        autoOpen: false,
        width: 350,
        modal: true
    });

    $('#bookingStartTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#bookingEndTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#bookingUpdatingStartTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#bookingUpdatingEndTimepicker').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#recurrent-booking-start-time').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#recurrent-booking-end-time').timepicker({
        timeFormat: 'H:i',
        step: 15,
        minTime: '07:00',
        maxTime: '20:00'
    });

    $('#make-recurrent-booking').dialog({
        autoOpen: false,
        title: 'New booking',
        width: 550,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        },
        beforeClose: function (){
            $('#child-selector').show();

            $('#deleting-recurrent-booking').hide();

            $('#update-recurrent-booking').hide();
            $('#book').show();
            $("#make-recurrent-booking").dialog("option", "title", "New booking");
        }
    });

    $('#update-recurrent-booking').click(function () {
        updateRecurrentBooking();
    });
    $('#close-choose').click(function () {
        $('#single-update-booking').prop('checked', true);
        $('#recurrent-update-booking').prop('checked', false);
        $('#recurrent-change').dialog('close');
    });

    $('#confirm-choose-booking').click(function () {
        if ($('#single-update-booking').is(':checked')) {
            $('#bookingUpdatingDialog').dialog('open');
        } else {
            $('#make-recurrent-booking').dialog('open');
            $('#book').hide();
            $('#update-recurrent-booking').show();

            $('#child-selector').hide();
            $('#comment-for-one-child-updating').show();
            $('#deleting-recurrent-booking').show();
        }

        $('#single-update-booking').prop('checked', true);
        $('#recurrent-update-booking').prop('checked', false);

        $('#recurrent-change').dialog('close');
    });

    $('.booking-radio').click(function () {
        if ($('#weekly-booking').is(':checked')) {

            $('#days-for-recurrent-booking-form').attr('hidden', false);
        } else {

            $('#days-for-recurrent-booking-form').attr('hidden', true);
        }

        if ($('#no-recurrent-booking').is(':checked')) {
            var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

            checkBoxesDays.forEach(function (item) {
                $('#' + item + '-booking').attr('checked', false);
            });
            $('#recurrent-booking-end-date').val($('#recurrent-booking-start-date').val());
        }
    });


    $('#updatingBooking').click(function () {
        updateBooking();
        $('#bookingUpdatingDialog').dialog('close');
    });


    $('#deleting-single-booking').click(function () {
        $('#bookingUpdatingDialog').dialog('close');
        var myDialog = $('#confirmation-dialog-div');
        myDialog.dialog('open');
        $('#confirmYes').click(function () {
            cancelBooking(info.id)
            myDialog.dialog('close');
        });
        $('#confirmNo').click(function () {
            debugger
            myDialog.dialog('close');
        });
    });


    $('#deletingBooking').click(function () {
        $('#bookingUpdatingDialog').dialog('close');
        //cancelBooking(info.id);
    });

    //create booking and close the dialog window
    $('#book').click(function () {
        //here the data validation

        if ($('#no-recurrent-booking').is(':checked')) {
            var success = createBooking();
            if(success)
                closeBookingDialog();
        }

        if ($('#weekly-booking').is(':checked')) {
            makeRecurrentBookings();
            closeBookingDialog();
        }
    });

    //open booking creating dialog
    $('#create-new-booking').click(function () {
        var newBookingDate = $('#user-calendar').fullCalendar('getDate').format();
        var currentDate = new Date();
        $('#recurrent-booking-start-date').val(newBookingDate.substring(0, 10));
        $('#recurrent-booking-end-date').val(newBookingDate.substring(0, 10));
        $('#recurrent-booking-start-time').timepicker('setTime', currentDate.toLocaleTimeString());
        $('#recurrent-booking-end-time').timepicker('setTime', currentDate.toLocaleTimeString());
        $("#data-validation-information-string").html("");
        $('#make-recurrent-booking').dialog('open');
    });

    //close booking creating dialog
    $('#cancel-changes').click(function () {
        closeBookingDialog();
    });

    $('#deleting-recurrent-booking').click(function () {
        $('#make-recurrent-booking').dialog('close');
        cancelRecurrentBookings(info.calEvent.recurrentId);
    });


});

function selectRoomForUser(roomParam, userId, phoneNumber, managers) {


    var id = roomParam;


    showRoomPhone(phoneNumber);

    showRoomManagers(managers);


    //   getDisabledTime("2016-07-06", "2016-08-06", id);


    roomIdForHandler = id;
    usersID = userId;

    $('#user-calendar').fullCalendar('destroy');

    $('input').on('click', function () {
        var numberOfKids = $('#number-of-kids').val();
        var commentId;

        for (var i = 0; i < numberOfKids; i++) {
            commentId = $('#comment-' + i).val();
            if ($('#checkboxKid' + commentId).is(':checked')) {
                $('#child-comment-' + commentId).prop('hidden', false);
                $('#child-comment-' + commentId + '-1').prop('hidden', false);

            } else {
                $('#child-comment-' + commentId).prop('hidden', true);
                $('#child-comment-' + commentId + '-1').prop('hidden', true);
            }
        }
    });

    var path = 'getevents/' + id;

    $.ajax({
        url: 'getroomproperty/' + id,
        success: function (result) {

            result = result.split(' ');
            result[0] += ":00";
            result[1] += ":00";
            var startTime = result[0];
            var endTime = result[1];

            $.ajax({
                url: path, success: function (result) {
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
                                color: result[i].color,
                                description :result[i].description,
                                type: 'event'
                            }
                        }
                        renderingForUser(objects, id, userId, startTime, endTime);
                    } else {
                        $('#user-calendar').fullCalendar('destroy');
                        /**
                         * This object is required for creating empty calendar
                         * Without this object calendar will not be rendered
                         */
                        objects = [{
                            title: '1',
                            start: '1',
                            end: '1',
                            editable: false
                        }];
                        renderingForUser(objects, id, userId, startTime, endTime);
                    }
                }
            });
        }
    });


}

function renderingForUser(objects, id, userId, workingHoursStart, workingHoursEnd) {
    bookingDate = {};
    info = {};

    bookingsArray = [];

    var pathForUploadingAllBookingsForUsers = 'getallbookings/' + userId + '/' + id;

    $.ajax({
        url: pathForUploadingAllBookingsForUsers, success: function (result) {

            result = JSON.parse(result);
            allBookings = result;
            var objectsLen = objects.length;

            result.forEach(function (item, i) {
                objects[objectsLen + i] = {
                    id: item.id,
                    title: item.kidName,
                    start: item.date + 'T' + item.startTime + ':00',
                    end: item.date + 'T' + item.endTime + ':00',
                    color: BOOKING,
                    borderColor: BORDER,
                    kidId: item.idChild,
                    editable: false,
                    type: 'booking',
                    comment: item.comment,
                    recurrentId: item.recurrentId
                };
            });
            renderingBlockedTimeSpans(objects, id, workingHoursStart, workingHoursEnd);
        }
    });
}

function renderingBlockedTimeSpans(objects, id, workingHoursStart, workingHoursEnd) {

    var path = 'disabled?roomID=' + id;

    $.ajax({
        url: path, success: function (result) {

            result = JSON.parse(result);
            var objectsLen = objects.length;
            var keyArr = Object.keys(result);
            keyArr.sort();

            keyArr.forEach(function (item, i) {
                objects[objectsLen + i] = {
                    title: 'Room is full',
                    start: item,
                    end: result[item],
                    color: BLOCKED,
                    borderColor: BORDER,
                    editable: false,
                };
            });
            renderCalendar(objects, id, workingHoursStart, workingHoursEnd);
        },
        error : function() {
            renderCalendar(objects, id, workingHoursStart, workingHoursEnd);
        }

    });
}


//tested
function makeISOTime(clickDate, idOfTimePicker) {
    if (clickDate.length < 12) {
        clickDate = clickDate + 'T00:00:00';
    }

    var element = '#' + idOfTimePicker;
    var installedTime = $(element).timepicker('getTime');

    var timepickerMinutes = installedTime.getMinutes();
    var timepickerHours = installedTime.getHours();

    if (timepickerMinutes === 0) {
        timepickerMinutes = '00';
    } else {
        timepickerMinutes = timepickerMinutes.toString();
    }

    if (timepickerHours < 10) {
        timepickerHours = '0' + timepickerHours.toString();
    } else {
        timepickerHours = timepickerHours.toString();
    }

    return clickDate.substring(0, 11) + timepickerHours + ':' +
        timepickerMinutes + clickDate.substring(16);
}

//tested
function Booking(startTime, endTime, comment, kidId, roomId, usersID) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
    this.userId = usersID
}

function sendBookingToServerForUpdate(bookingForUpdate) {

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'change-booking',
        dataType: 'json',
        data: JSON.stringify({
            id: bookingForUpdate.id,
            startTime: bookingForUpdate.start.substring(0, 10) + ' ' + bookingForUpdate.start.substring(11, 16),
            endTime: bookingForUpdate.end.substring(0, 10) + ' ' + bookingForUpdate.end.substring(11, 16),
            comment: bookingForUpdate.comment
        }),
        success: function (result) {
            if (result) {

                bookingForUpdate.color = BOOKING;
                bookingForUpdate.borderColor = BORDER;
                $('#user-calendar').fullCalendar('removeEvents', bookingForUpdate.id);
                $('#user-calendar').fullCalendar('renderEvent', bookingForUpdate);
            }
            else {
                callErrorDialog('We regret to inform you that there are no available places left in the room on the time you\'ve chosen');
            }
        }
    });
}

function sendBookingToServerForCreate(bookingsArray) {

    var currentTime = new Date().toISOString();

    bookingsArray.forEach(function (item, i) {
        if (item.startTime < currentTime) {
            delete bookingsArray[i];
        }

    });
    var empty = true;
    for (var i = 0; i < bookingsArray.length; i++) {

        if (bookingsArray[i] != null) {
            empty = false;
        }
    }

    if (!empty) {
        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'makenewbooking',
            dataType: 'json',
            data: JSON.stringify(bookingsArray),
            success: function (result) {

                var refresh = result;

                $('#user-calendar').fullCalendar('removeEvents', -1);

                refresh.forEach(function (item) {

                    $('#user-calendar').fullCalendar('renderEvent', {
                        id: item.id,
                        title: item.kidName,
                        start: item.startTime,
                        end: item.endTime,
                        color: BOOKING,
                        borderColor: BORDER,
                        kidId: item.idChild,
                        editable: false,
                        type: 'booking',
                        comment: item.comment
                    });
                });
            },
            error: function () {
                $('#user-calendar').fullCalendar('removeEvents', -1);
                callErrorDialog('Room is full or duplicate booking. Please contact the manager if you have any problems with booking ');
            }
        });
    } else {
        $('#user-calendar').fullCalendar('removeEvents', -1);
        callErrorDialog('You cannot make booking in past time. Please contact the room manager if you have problems with booking');
    }
}

function cancelBooking(bookingId) {
    $('#user-calendar').fullCalendar('removeEvents', bookingId);
    $.ajax({
        type: 'get',
        contentType: 'application/json',
        url: 'cancelBook/' + bookingId,
        dataType: 'json',
        data: JSON.stringify({})
    });
}

//tested
function getComment(commentInputId) {
    return $('#' + commentInputId).val();
}

function updateBooking() {
    var newStartDate = makeISOTime(info.calEvent.start.format(), 'bookingUpdatingStartTimepicker');
    var newEndDate = makeISOTime(info.calEvent.end.format(), 'bookingUpdatingEndTimepicker');

    var bookingForUpdate = {
        id: info.calEvent.id,
        title: info.calEvent.title,
        start: newStartDate,
        end: newEndDate,
        comment: $('#child-comment-update').val()
    };

    sendBookingToServerForUpdate(bookingForUpdate);

}

//tested

function validateSingleBookingData(){
    var startDate = $.datepicker.parseDate("yy-mm-dd",$('#recurrent-booking-start-date').val());
    var currentDate = new Date();
    var startTime = $("#recurrent-booking-start-time").timepicker('getTime');
    var endTime = $("#recurrent-booking-end-time").timepicker('getTime');
    var dayLengthInMiliseconds = startTime.getHours()*60*60*1000;
    var dataValidationStrings = new Array();
    if( (startDate.getTime() < currentDate.getTime())){
        if(startDate.getDate()!=currentDate.getDate()){
            dataValidationStrings.push("Date can't be in the past, current date is: "+currentDate.toLocaleDateString());
        }
    }
    if(startDate.getDate()==currentDate.getDate())
        if(startTime.getTime() < currentDate.getTime()){
            dataValidationStrings.push("Start can't be in the past, current time is: "+currentDate.toLocaleTimeString());
        }
    if(startTime.getTime() > endTime.getTime()){
        dataValidationStrings.push("End time can't be earlier than the start time");
    }
    var numberOfSelectedKids = 0;
    for (var i = 0; i < ($('#number-of-kids').val()); i++) {
        kidsCommentId = $('#comment-' + i).val();
        if ($('#checkboxKid' + kidsCommentId).is(':checked')) {
            numberOfSelectedKids++;
        }
    }
    if(numberOfSelectedKids<1){
        dataValidationStrings.push("At least one kid must be selected");
    }
    if(dataValidationStrings.length>0){
        var text = "Incorrect input data:";
        for(var i=0; i<dataValidationStrings.length; i++){
            text+="<br/>- "+dataValidationStrings[i]+".";
        }
        $("#data-validation-information-string").html(text);
        return false;
    }else
        return true;
}
function createBooking() {

    if(!validateSingleBookingData())
        return false;

    bookingDate.clickDate = $('#recurrent-booking-start-date').val();
    bookingsArray = [];
    var kidsCommentId;

    for (var i = 0; i < ($('#number-of-kids').val()); i++) {
        kidsCommentId = $('#comment-' + i).val();
        if ($('#checkboxKid' + kidsCommentId).is(':checked')) {
            bookingsArray.push(
                new Booking(makeISOTime(bookingDate.clickDate, 'recurrent-booking-start-time'),
                    makeISOTime(bookingDate.clickDate, 'recurrent-booking-end-time'),
                    getComment("child-comment-" + kidsCommentId),
                    kidsCommentId,
                    roomIdForHandler,
                    usersID));

            $('#user-calendar').fullCalendar('renderEvent', {
                id: -1,
                title: '',
                start: makeISOTime(bookingDate.clickDate, 'recurrent-booking-start-time'),
                end: makeISOTime(bookingDate.clickDate, 'recurrent-booking-end-time'),
                editable: false
            });
            $('#checkboxKid' + kidsCommentId).attr('checked', false);
            $('#child-comment-' + kidsCommentId).prop('hidden', true);
            $('#child-comment-' + kidsCommentId + '-1').prop('hidden', true);
        }
    }
    sendBookingToServerForCreate(bookingsArray);
    return true;





}

//tested
function renderCalendar(objects, id, workingHoursStart, workingHoursEnd) {
    $('#user-calendar').fullCalendar({
        minTime: workingHoursStart,
        maxTime: workingHoursEnd,
        timeFormat : 'HH:mm',
        eventBackgroundColor: NOT_SYNCHRONIZED,
        eventColor: 'transparent',
        eventBorderColor: 'transparent',
        eventTextColor: '#fff',
        slotDuration: '00:15:00',

        dayClick: function (date) {


            var clickDate = date.format();

            if (clickDate.length < 12) {
                clickDate = clickDate + 'T00:00:00';
            }
            var currentDate = new Date();


            $('#recurrent-booking-start-date').val(clickDate.substring(0, 10));
            $('#recurrent-booking-end-date').val(clickDate.substring(0, 10));
            if (clickDate.substring(11, 19) == "00:00:00"){
                $('#recurrent-booking-start-time').timepicker('setTime', currentDate.toLocaleTimeString());
                $('#recurrent-booking-end-time').timepicker('setTime', currentDate.toLocaleTimeString());}
            else {$('#recurrent-booking-start-time').timepicker('setTime', clickDate.substring(11, 19));
                $('#recurrent-booking-end-time').timepicker('setTime', clickDate.substring(11, 19));}
            $("#data-validation-information-string").html("");

            bookingDate.clickDate = clickDate;

            $('#make-recurrent-booking').dialog('open');
        },

        eventMouseover: function(calEvent) {

            cursorIsOverEvent = false;

            if (calEvent.type === 'event' || calEvent.color === NOT_SYNCHRONIZED) {
                if (calEvent.description != "") {
                    eventDescription = calEvent.description
                }

                $(this).mouseover(function (e) {
                    $(this).css('z-index', 10000);
                    $('#eventTitle').html(calEvent.title);
                    $('#startTime').html('Start at :' + '<b>'+ calEvent.start.format('HH:mm'));
                    $('#endTime').html('Ends at :' + '<b>'+calEvent.end.format('HH:mm'));
                    if (calEvent.description != "") {
                        $('#eventDescription').html('<b>Description : </b><br>' + eventDescription);
                    }
                    $('.eventInfo').delay(600).fadeTo(200, 1);

                    if(cursorIsOverEvent == false) {
                        cursorOverEventX = e.pageX;
                        cursorOverEventY = e.pageY;
                        cursorIsOverEvent = true;
                    }
                    else if (cursorIsOverEvent == true) {
                        return
                    }

                }).mousemove(function (e) {

                    var eventInfoHeight = parseInt($('.eventInfo').css('height'));
                    var eventInfoWidth = parseInt($('.eventInfo').css('width'));
                    var windowWidth = $( document ).width();
                    var windowHeight = $( document ).height();

                    if (cursorOverEventX > windowWidth - eventInfoWidth * 1.5) {
                        if (cursorOverEventY > windowHeight - eventInfoHeight * 2) {
                            $('.eventInfo').css('top', e.pageY - eventInfoHeight);
                            $('.eventInfo').css('left', e.pageX - eventInfoWidth);
                        } else {
                            $('.eventInfo').css('top', e.pageY + 20);
                            $('.eventInfo').css('left', e.pageX - eventInfoWidth);
                        }
                    } else {
                        if (cursorOverEventY > windowHeight - eventInfoHeight * 2) {
                            $('.eventInfo').css('top', e.pageY - eventInfoHeight);
                            $('.eventInfo').css('left', e.pageX + 10);
                        } else {
                            $('.eventInfo').css('top', e.pageY + 20);
                            $('.eventInfo').css('left', e.pageX + 10);
                        }
                    }
                });
            }
        },

        eventMouseout: function() {
            $(this).css('z-index', 8);
            $('.eventInfo').hide();
            cursorIsOverEvent = false;

        },

        eventClick: function (calEvent, data, view) {

            var eventDescription = "none";
            if (calEvent.description != ""){
                eventDescription = calEvent.description
            }

            if (calEvent.type === 'event' || calEvent.color === NOT_SYNCHRONIZED) {
                return;
            }

            if (calEvent.color === BLOCKED) {
                return;
            }


            if (calEvent.type === 'booking') {
                $('#child-comment-update').val(calEvent.comment);
            }

            clickedEvent = calEvent.kidId;
            clickedEventRecurrentId = calEvent.recurrentId;

            var beforeUpdate = calEvent.title;

            $("#bookingUpdatingDialog").dialog("option", "title", beforeUpdate);

            $('#bookingUpdatingStartDate').val(calEvent.start.format().substring(0, 10));
            $('#bookingUpdatingEndDate').val(calEvent.end.format().substring(0, 10));

            var date = new Date(calEvent.start.format());
            var endDate = new Date(calEvent.end.format());

            var newDate = makeUTCTime(new Date(), date);
            var newDateForEnd = makeUTCTime(new Date(), endDate);

            $('#bookingUpdatingStartTimepicker').timepicker('setTime', newDate);
            $('#bookingUpdatingEndTimepicker').timepicker('setTime', newDateForEnd);


            $("#make-recurrent-booking").dialog("option", "title", beforeUpdate);

            $('#recurrent-booking-start-date').val(calEvent.start.format().substring(0, 10));
            $('#recurrent-booking-end-date').val(calEvent.end.format().substring(0, 10));
            $('#recurrent-booking-start-time').timepicker('setTime', newDate);
            $('#recurrent-booking-end-time').timepicker('setTime', newDateForEnd);

            info.id = calEvent.id;
            info.beforeUpdate = beforeUpdate;
            info.endDate = endDate;
            info.calEvent = calEvent;
            info.roomID = id;
            info.date = date;

            if (!!calEvent.recurrentId) {
                $('#recurrent-change').dialog('open');
            } else {
                $('#bookingUpdatingDialog').dialog('open');
            }
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },

        defaultDate: $('#user-calendar').fullCalendar('getDate'),

        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#user-calendar').fullCalendar('renderEvent', eventData, false);
            }
            $('#user-calendar').fullCalendar('unselect');
        },

        eventRender: function (event, element) {

            if (event.rendering === 'background') {
                element.append(event.title);
                element.css('background-color', 'yellow');
                element.css('color', 'black');
            }

        },
        editable: false,
        eventLimit: true,
        events: objects
    });
}

function updateRecurrentBooking() {
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

    var checkedDays = '';

    checkBoxesDays.forEach(function (item) {
        if ($('#' + item + '-booking').is(':checked')) {
            checkedDays += $('#' + item + '-booking').val() + ' ';
        }
    });
    checkedDays = checkedDays.substring(0, checkedDays.length - 1);


    bookingDate.clickDate = $('#recurrent-booking-start-date').val();

    var recurrentStartDay = $('#recurrent-booking-start-date').val();

    var recurrentEndDay = $('#recurrent-booking-end-date').val();

    var newEventAfterUpdate = [{
        startTime: makeISOTime(recurrentStartDay, 'recurrent-booking-start-time'),
        endTime: makeISOTime(recurrentEndDay, 'recurrent-booking-end-time'),
        comment: $('#comment-for-update-recurrency').val(),
        kidId: clickedEvent,
        roomId: roomIdForHandler,
        userId: usersID,
        daysOfWeek: checkedDays
    }];


    cancelRecurrentBookings(clickedEventRecurrentId);

    $.ajax({
            url: 'getrecurrentbookings',
            type: 'post',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(newEventAfterUpdate),
            success: function (result) {

                result.forEach(function (item, i) {
                    var newBooking = {
                        id: item.id,
                        title: item.kidName,
                        start: item.date + 'T' + item.startTime + ':00',
                        end: item.date + 'T' + item.endTime + ':00',
                        color: BOOKING,
                        borderColor: BORDER,
                        editable: false,
                        kidId: item.idChild,
                        type: 'booking',
                        comment: item.comment,
                        recurrentId: item.recurrentId
                    };
                    allBookings[allBookings.length + i] = newBooking;

                    $('#user-calendar').fullCalendar('renderEvent', newBooking);

                });
            }
        }
    )
    closeUpdatingDialog();
}

function makeRecurrentBookings() {
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

    var checkedDays = '';

    checkBoxesDays.forEach(function (item) {
        if ($('#' + item + '-booking').is(':checked')) {
            checkedDays += $('#' + item + '-booking').val() + ' ';
        }
    });
    checkedDays = checkedDays.substring(0, checkedDays.length - 1);


    bookingDate.clickDate = $('#recurrent-booking-start-date').val();

    var recurrentStartDay = $('#recurrent-booking-start-date').val();

    var recurrentEndDay = $('#recurrent-booking-end-date').val();

    var bookingsRecurrentArray = [];
    var kidsCommentId;

    //TODO: REFACTOR THIS!!!
    for (var i = 0; i < $('#number-of-kids').val(); i++) {
        kidsCommentId = $('#comment-' + i).val();
        if ($('#checkboxKid' + kidsCommentId).is(':checked')) {
            bookingsRecurrentArray.push(
                {
                    startTime: makeISOTime(recurrentStartDay, 'recurrent-booking-start-time'),
                    endTime: makeISOTime(recurrentEndDay, 'recurrent-booking-end-time'),
                    comment: getComment('child-comment-' + kidsCommentId),
                    kidId: kidsCommentId,
                    roomId: roomIdForHandler,
                    userId: usersID,
                    daysOfWeek: checkedDays
                }
            );

            /*         $('#user-calendar').fullCalendar('renderEvent', {
             id: -1,
             title: '',
             start: makeISOTime(recurrentStartDay, 'recurrent-booking-start-time'),
             end: makeISOTime(recurrentEndDay, 'recurrent-booking-end-time'),
             editable: false
             });*/
            $('#checkboxKid' + kidsCommentId).attr('checked', false);
            $('#child-comment-' + kidsCommentId).prop('hidden', true);
            $('#child-comment-' + kidsCommentId + '-1').prop('hidden', true);


        }
    }

    $.ajax({
            url: 'getrecurrentbookings',
            type: 'post',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(bookingsRecurrentArray),
            success: function (result) {

                result.forEach(function (item, i) {
                    var newBooking = {
                        id: item.id,
                        title: item.kidName,
                        start: item.date + 'T' + item.startTime + ':00',
                        end: item.date + 'T' + item.endTime + ':00',
                        color: BOOKING,
                        borderColor: BORDER,
                        editable: false,
                        kidId: item.idChild,
                        type: 'booking',
                        comment: item.comment,
                        recurrentId: item.recurrentId
                    };

                    allBookings[allBookings.length + i] = newBooking;

                    $('#user-calendar').fullCalendar('renderEvent', newBooking);

                });
            }
        }
    )
}
//tested
function makeUTCTime(time, date) {
    time.setHours(date.getUTCHours());
    time.setMinutes(date.getUTCMinutes());
    time.setSeconds(date.getUTCSeconds());
    return time;
}
/*
 function getDisabledTime(dateLo, dateHi, roomId) {
 var urls = 'disabled?roomID=' + roomId + '&dateLo=' + dateLo + '&dateHi=' + dateHi;
 $.ajax({
 url: urls,
 contentType: 'application/json',
 dataType: 'text',
 success: function (result) {
 // alert(result);
 }
 });
 }
 */
//tested

function showRoomPhone(phoneNumber) {
    $('#roomPhone').empty().append('<span class="glyphicon glyphicon-earphone"></span>' + ' ' + phoneNumber);
}


function showRoomManagers(managers) {
    $('#showRoomManagers').empty().append('<span class="glyphicon glyphicon-user"></span>' + ' ' + managers);
}


function sendAjaxForRoomProperty(roomId) {

}

//tested
function closeBookingDialog() {
    $('#make-recurrent-booking').dialog('close');
    var checkBoxesDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

    checkBoxesDays.forEach(function (item) {
        $('#' + item + '-booking').attr('checked', false);
    });
}

function closeUpdatingDialog() {
    $('#make-recurrent-booking').dialog('close');
    $('#book').show();
    $('#update-recurrent-booking').hide();

    $('#child-selector').show();
    $('#comment-for-one-child-updating').hide();
}

function cancelRecurrentBookings(recurrentId) {
    var bookingsForDelete = [];
    var arrayForIndexesOfDeletingBookings = [];

    allBookings.forEach(function (item, i) {
        if (item.recurrentId === recurrentId) {
            bookingsForDelete.push(item);
            arrayForIndexesOfDeletingBookings.push(i);
        }
    });

    arrayForIndexesOfDeletingBookings.forEach(function (item) {
        allBookings.splice(item, 1);
    });

    bookingsForDelete.forEach(function (item) {
        $('#user-calendar').fullCalendar('removeEvents', item.id);
        cancelBooking(item.id);
    })
}
