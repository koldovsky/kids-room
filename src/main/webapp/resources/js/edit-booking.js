var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState = localStorage["bookingsState"];
var table = null;
var dailyNotCompletedBookings;  //array containing bookings DTO
var roomCapacity; // The capacity (number of people) current room

$(function() {
    if(localStorage["bookingsState"] == null) {
        localStorage["bookingsState"] = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    }
    });
$('#date-booking').val(dateNow.toISOString().substr(0, 10));
$('#bookingStartTimepicker').val("07:00");
$('#bookingEndTimepicker').val("20:00");
function validateData(startTime, endTime) {
    var isValid = startTime !== "" && endTime !== "";
    return isValid;
}

$().ready(function() {
    $('#booking-table').show();
    $('[data-toggle="tooltip"]').tooltip();
    $('#booking').click(function() {
        bookingsArray = [];
        var idUser = $('#selectUser :selected').val();
        var date = $('#bookingStartDate').val();
        var startTime = $('#bookingStartTimepicker').val();
        var endTime = $('#bookingEndTimepicker').val();
        var startISOtime = date + 'T' + startTime + ':00';
        var endISOtime = date + 'T' + endTime + ':00';

       if(validateData(startTime, endTime)){
            var urlForKids = "get-kids/" + idUser;
            $.ajax({
                url: urlForKids,
                success: function(result) {
                    var kids = JSON.parse(result);
                    $.each(kids, function(i, kid) {
                        var kidId = kid.id;
                        if ($('#checkboxKid' + kid.id).is(':checked')) {
                            var comment = ($('#child-comment-' + kid.id).val());
                            var booking = new Booking(startISOtime, endISOtime, comment, kidId, localStorage["roomId"], idUser);
                            bookingsArray.push(booking);
                        }
                    });
                    sendBookingToServerForCreate(bookingsArray);
                }
            });
            $('#bookingDialog').dialog('close');
        }else{
            $('#bookingStartTimepicker').css("background-color", "red");
        }

    });
});

$().ready(function() {
    $('#deletingBooking').on('click', function() {
        $('#cancelModal').modal('show');
        $('#cancelButton').on('click', function() {
            cancelBooking();
        });
    });
    $('#updatingBooking').click(function() {
        var getData = $('#data-edit').val();
        var inputDate = {
            id: idBooking,
            startTime: getData + " " + $('#bookingUpdatingStartTimepicker').val(),
            endTime: getData + " " + $('#bookingUpdatingEndTimepicker').val(),
            roomId: localStorage["roomId"],
            comment: $('#kid-comment').val(),
        };
        updatingBooking(inputDate);
        $('#' + id).removeClass('highlight-active');
    });

    $('#selectUser').on('change', function() {
        $('#kids').empty();
        var idUser = $(this).val();
        var getKidsUrl = "get-kids/" + idUser;
        addKids(getKidsUrl);
    });
});

/**
 * Receives JSON containing bookings DTO with status ACTIVE and BOOKED
 * for one given day and given room. Information is evaluated to working
 * hours of the room. Information is stores in the global variable
 * dailyNotCompletedBookings
 */

function getNotCompletedBokings() {
    var time = $('#date-booking').val();
    var idRoom = localStorage["roomId"];
    var results;
    src = 'dailyNotCompletedBookings/' + time + "/" + idRoom;
        $.ajax({
            url: src,
            async: false,
            success: function (data) {
                results = data;
            }
        });
    dailyNotCompletedBookings = JSON.parse(results);
}

$(function() {
    $('#date-booking').change(function() {
        refreshTable(localStorage["bookingsState"]);
    });
    $('.picker').timepicker({
        timeFormat: 'H:i',
        step: 1,
        minTime: '07:00',
        maxTime: '20:00'
    });

});

function selectRoomForManager(roomId) {
    refreshTable(localStorage["bookingsState"]);
    $.ajax({
        url: 'getroomproperty/' + roomId,
        success: function(result){
            result = result.split(' ');
            $('#bookingStartTimepicker').val(result[0]);
            $('#bookingEndTimepicker').val(result[1]);
            result[0] += ":00";
            result[1] += ":00";

            var startTime = result[0];
            var endTime = result[1];
            $('.picker').timepicker('option', 'minTime', startTime);
            $('.picker').timepicker('option', 'maxTime', endTime);

            roomCapacity = result[2];
        }

    });
}

function bookedBooking() {
    localStorage["bookingsState"] = ['BOOKED'];
    refreshTable(localStorage["bookingsState"]);
}

function activeBooking() {
    localStorage["bookingsState"] = ['ACTIVE'];
    refreshTable(localStorage["bookingsState"]);
}

function leavedBooking() {
    localStorage["bookingsState"] = ['COMPLETED'];
    refreshTable(localStorage["bookingsState"]);
}

function allBooking() {
    localStorage["bookingsState"] = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    refreshTable(localStorage["bookingsState"]);
}

function chekBookingState(){
    $('.btn-raised').removeClass("btn-info");
    if(localStorage["bookingsState"] === 'ACTIVE,BOOKED,CALCULATE_SUM,COMPLETED'){
       $('#btn-all').addClass("btn-info");
    } else if(localStorage["bookingsState"] === 'ACTIVE'){
        $('#btn-active').addClass("btn-info");
    } else if(localStorage["bookingsState"] ==='COMPLETED'){
        $('#btn-leaved').addClass("btn-info");
    } else{
        $('#btn-booked').addClass("btn-info");
    }
}

function refreshTable(bookingsState) {
    chekBookingState();
    var time = $('#date-booking').val();
    var idRoom = localStorage["roomId"];
    src = 'dailyBookings/' + time + "/" + idRoom + "/" + bookingsState;
    jQuery.extend({
        getValues: function() {
            var result = null;
            $.ajax({
                url: src,
                async: false,
                success: function(data) {
                    result = data;
                }
            });
            return result;
        }
    });
    var results = $.getValues();
    var data = JSON.parse(results);


    if (!(table === null)) {
        table.destroy();
    }

    table = $('#booking-table').DataTable({
        rowId: 'id',
        "columnDefs": [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
        "order": [
            [0, 'asc']
        ],
        'data': data,
        'columns': [{
                "data": null
            },

            {
                "data": 'kidName',
                "className": "kid-name",
                "fnCreatedCell": function(nTd, sData, oData) {
                    if (!(oData.comment == "")) {
                        $(nTd).html('<a href=profile?id=' + oData.idChild + '>' + oData.kidName + '</a>' + " "
                        + '<span data-toggle="tooltip"' + 'id="comment-'+oData.id
                        + '" class="glyphicon glyphicon-info-sign"' + 'title="'
                        + oData.comment +  '"' + ' onclick="callCommentDialog()"></span>');
                    } else {
                        $(nTd).html('<a href=profile?id=' + oData.idChild + '>' + oData.kidName + '</a>');
                    }
                }
            }, {
                "data": "startTime",
                "className": "edit-button",
                "fnCreatedCell": function(nTd, sData, oData) {
                    var td = '<span class="book-id" id =' + oData.id + '><span id="book-start-time">'
                    + oData.startTime + '</span> - ' + '<span id="book-end-time">' + oData.endTime + '</span></span>';
                    if(localStorage["bookingsState"] ==['BOOKED']){
                        td+= '<button class="btn btn-sm glyphicon glyphicon-edit edit-button-btn"></button>';
                    }
                    $(nTd).empty();
                    $(nTd).append(td);
                }
            }, {
                "className": 'arrivalTime',
                "defaultContent": '<input type="time" class="form-control inp-arrivalTime" >'
                + '<button class="btn btn-sm btn-success glyphicon glyphicon-arrow-down" id="arrival-btn" ></button>'
            }, {
                "className": 'leaveTime',
                "defaultContent": '<input type="time" class="form-control inp-leaveTime">'
                + '<button class="btn btn-sm btn-success glyphicon glyphicon-share-alt" id="leave-btn"></button>'
            },

        ]
    });

    table.on('order.dt search.dt', function() {
        table.column(0, {
            search: 'applied',
            order: 'applied'
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
    addHilighted(data);
}

function setStartTime(id, startTime) {
    var inputData = {
        startTime: startTime,
        id: id,
    };
    $('#' + id).addClass('highlight-active');
    $.ajax({
        url: "setTime",
        contentType: 'application/json',
        data: JSON.stringify(inputData),
        type: 'POST',
        success: function() {
            refreshTable(localStorage["bookingsState"]);
        }
    });
}

function setEndTime(id, time) {
    var inputData = {
        endTime: time,
        id: id,
    };
    if ($('#' + id).find('.inp-arrivalTime').val() < $('#' + id).find('.inp-leaveTime').val()) {
        $('#' + id).addClass('highlight-complet');
        $.ajax({
            url: "setEndTime",
            contentType: 'application/json',
            data: JSON.stringify(inputData),
            type: 'POST',
            success: function() {
                refreshTable(localStorage["bookingsState"]);
            }
        });
    } else {
        $('#invalidTimeModal').modal('show');
    }
}

function addHilighted(bookings) {

    $.each(bookings, function(index, value) {
        if (value.bookingState == "ACTIVE") {
            var row = table.row('#' + value.id).node();
            $(row).addClass('highlight-active');
            $('#' + value.id).find('.inp-arrivalTime').val(value.startTime);
        } else if (value.bookingState == "COMPLETED") {
            var row = table.row('#' + value.id).node();
            $(row).addClass('highlight-complet');
            $('#' + value.id).find('.inp-arrivalTime').val(value.startTime);
            $('#' + value.id).find('.inp-leaveTime').val(value.endTime);
        }
    });
}

/**
 * The method finds the maximum people in the room for period of time
 * from dateLo to dateHi. All of the parameters must not be a null.
 *
 * @param dateLo start of period
 * @param dateHi end of period
 * @param bookings all reserved bookings in the time period
 * @return The maximum number of people that are simultaneously in the room
 */
function maxRangeReservedBookings(startTimeMillis, endTimeMillis, bookings) {
    var oneMinuteMillis = 60 * 1000;
    var maxReservedBookings = 0;
    var temporaryMax;
    var bok;
    var ti;
    var i;
    for (ti = startTimeMillis + 1; ti < endTimeMillis; ti += oneMinuteMillis) {
        temporaryMax = 0;
        for (i = 0; i < bookings.length; i++) {
            bok = bookings[i];
            if (bok.startTimeMillis < ti && bok.endTimeMillis > ti)
                temporaryMax++;
            if (temporaryMax > maxReservedBookings)
                maxReservedBookings = temporaryMax;
        }
    }
    return maxReservedBookings;
}

/**
 * The method finds the available space in the room (number of people)
 * for the given period of time from dateLo to dateHi.
 * All of the parameters must not be a null.
 *
 * @param dateLo start of period
 * @param dateHi end of period
 * @return number of places available in the room for the period
 */
function getAvailableSpaceForPeriod(startTimeMillis, endTimeMillis) {
    return roomCapacity - maxRangeReservedBookings(
        startTimeMillis, endTimeMillis, dailyNotCompletedBookings);
}

/**
 * Opens Dialog on edit-booking.jsp for making a booking.
 */

function openCreateBookingDialog() {
    var date = $('#date-booking').val();
    $('#bookingStartDate').val(date);
    $('#bookingDialog').dialog();
}

/**
 * Receives time for normalization to format HH:mm.
 * If received time is not in format /^(([01]\d|2[0-3])|(\d)):(([0-5]\d)|(\d))$/
 * the method throws alert with relative information and returns from method.
 * If time is x:yz, the method returns 0x:yz. If time is xy:z, the method returns
 * xy:0z. If time is x:y, the method returns 0x:0y.
 *
 * @param time for normaliation
 * @returns normalizated time
 */

function timeNormalize(time) {
    var regexpTime = /^(([01]\d|2[0-3])|(\d)):(([0-5]\d)|(\d))$/;
    var timeAr;
    if (!regexpTime.test(time)) {
        return null;
    }
    timeAr = time.split(":");
    if(timeAr[0].length < 2){
        timeAr[0] = 0 + timeAr[0];
    }
    if(timeAr[1].length < 2){
        timeAr[1] = 0 + timeAr[1];
    }
    return timeAr[0] + ":" + timeAr[1];
}

/**
 * Figures out available places in the room for set period of time
 * and show it in dialog window (id="bookingDialog") on edit-booking.jsp page.
 * The period of time gets from elements (id=bookingStartTimepicker and id=bookingEndTimepicker)
 * on aforementioned page.
 * If the time is not in corrected form the message with appropriate information
 * is shows in aforementioned dialog message.
 */

function getAndSetAvailablePlaces() {
    var startTimeMillis;
    var endTimeMillis;
    var availablePlaces;
    var date = $('#date-booking').val();
    var timeStart = timeNormalize($('#bookingStartTimepicker').val());
    var timeEnd = timeNormalize($('#bookingEndTimepicker').val());
    if (timeStart != null && timeEnd != null) {
        startTimeMillis = new Date(date + " " + timeStart + ":00").getTime();
        endTimeMillis = new Date(date + " " + timeEnd + ":00").getTime();
        availablePlaces = getAvailableSpaceForPeriod(startTimeMillis, endTimeMillis);
    } else {
        availablePlaces = '<b>unknown</b>. Entered not correct time. Acceptable format is <b>HH:mm</b>';
    }
    document.getElementById('free-spaces').innerHTML = availablePlaces;
}

$("#btn-add-kid").click(function() {
    getNotCompletedBokings();
    getAndSetAvailablePlaces();
    openCreateBookingDialog();
});



$("#bookingStartTimepicker").change(function () {
    getAndSetAvailablePlaces();
});

$("#bookingEndTimepicker").change(function () {
    getAndSetAvailablePlaces();
});

function cancelBooking() {

    var str = "cancelBook/" + idBooking;
    $.ajax({
        url: str,
        success: function() {
            $('#cancelModal').modal('hide');
            $('#bookingUpdatingDialog').dialog('close');
            refreshTable(localStorage["bookingsState"]);
        }
    });
}

function updatingBooking(inputDate) {
    $.ajax({
        url: 'change-booking',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(inputDate),
        success: function(data) {
            if (data) {
                refreshTable(localStorage["bookingsState"]);
                $('#updatingSuccess').modal('show');
                $('#bookingUpdatingDialog').dialog('close');
            } else {
                $('#updatingInvalid').modal('show');
            }
        }
    });
}

function addKids(getKidsUrl) {
    $.ajax({
        url: getKidsUrl,
        success: function(result) {
            var kids = JSON.parse(result);
            var kidsCount = kids.length;
            var tr = "";
            $.each(kids, function(i, kid) {
                tr += '<br><div>' + '<label><input type="checkbox"' + 'id="checkboxKid' + kid.id + '">' + kid.firstName
                + '<input type = "text"' + 'placeholder="Comment"' + 'class="form-control"' + 'id="child-comment-'
                + kid.id + '">' + '</div>';
            });
            $('#kids-count').val(kidsCount);
            $('#kids').append(tr);
        }
    });
}

function Booking(startTime, endTime, comment, kidId, roomId, userId) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
    this.userId = userId;
}

function sendBookingToServerForCreate(bookingsArray) {
    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'makenewbooking',
        dataType: 'json',
        data: JSON.stringify(bookingsArray),
        success: function(result) {
            if (result == "") {
                $('#updatingInvalid').modal('show');
                $('#bookingDialog').dialog();
            } else {
                $('#createSuccess').modal('show');
                setTimeout(function () {
                    $('#createSuccess').modal('hide');
                }, 1300);
                refreshTable(localStorage["bookingsState"]);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (thrownError === "Not Acceptable") $('#creatingfailue1').modal('show');
            else if (thrownError === "Bad Request") $('#creatingfailue2').modal('show');
            else {
                alert("Unfortunately could not create new booking, please contact admin");
            }
        }
    });
}

$('#booking-table tbody').on('click', '#arrival-btn', function() {
    var tr = $(this).closest('tr');
    var id = table.row(tr).data().id;
    var time = $(this).closest('td').find('input').val();
    setStartTime(id, time);
});

$('#booking-table tbody').on('focus', 'input', function() {
    var time = new Date().toString().match(/\d{2}:\d{2}/)[0];
    $(this).val(time);
});

$('#booking-table tbody').on('click', '#leave-btn', function() {
    var tr = $(this).closest('tr');
    var id = table.row(tr).data().id;
    var time = $(this).closest('td').find('input').val();
    setEndTime(id, time);
});

$('#booking-table tbody').on('click', '.edit-button-btn', function() {
    idBooking = $(this).closest('td').find('.book-id').attr('id');
    var comment = $('#comment-'+idBooking).attr('title');
    var date = $('#date-booking').val();
    var startTime = $(this).closest('td').find('#book-start-time').text();
    var endTime = $(this).closest('td').find('#book-end-time').text();
    $('#kid-comment').val(comment);
    $('#bookingUpdatingStartTimepicker').val(startTime);
    $('#bookingUpdatingEndTimepicker').val(endTime);
    $('#data-edit').val(date);
    $('#bookingUpdatingDialog').dialog();
    $('#' + idBooking).addClass('highlight-active');
});

$("#bookingUpdatingDialog").on("dialogclose", function() {
    $('#' + idBooking).removeClass('highlight-active');
});

function handler() {
    var selected = $(this).hasClass("highlight-row");
    $("#booking-table > tbody > tr").removeClass("highlight-row");
    if(!selected){
        $(this).addClass("highlight-row");
    }
}
$( "#booking-table > tbody").on( "click", "tr", handler);