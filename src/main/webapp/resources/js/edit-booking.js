var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState = localStorage['bookingsState'];
var table = null;
var roomCapacity; // The capacity (number of people) current room
var roomWorkingStartTime;
var roomWorkingEndTime;

$(function() {
    $("#bookingDialog").attr("accept-charset", "UTF-8");
    if(localStorage['bookingsState'] == null) {
        localStorage['bookingsState'] = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    }
});

$('#date-booking').val(dateNow.toISOString().substr(0, 10));

function validateData(startTime, endTime) {
    var isValid = startTime !== '' && endTime !== '';
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

        if (validateData(startTime, endTime)) {
            var urlForKids = 'get-kids/' + idUser;
            $.ajax({
                url: urlForKids,
                encoding:'UTF-8',
                contentType: 'charset=UTF-8',
                success: function(result) {
                    var kids = JSON.parse(result);
                    $.each(kids, function(  i, kid) {
                        var kidId = kid.id;
                        if ($('#checkboxKid' + kid.id).is(':checked')) {
                            var comment = ($('#child-comment-' + kid.id).val());
                            var booking = new Booking(startISOtime, endISOtime, comment, kidId, localStorage['roomId'], idUser);
                            bookingsArray.push(booking);
                        }
                    });
                    sendBookingToServerForCreate(bookingsArray);
                }
            });
            $('#bookingDialog').dialog('close');
        } else {
            $('#bookingStartTimepicker').css('background-color', 'red');
        }

    });
});

$().ready(function() {

    $('#deletingBooking').on('click', function() {
        $('#cancelModal').modal('show');
        $('#cancelButton').on('click', function() {
            cancelBooking();
        });
        $('#closeCencel').on('click', function() {
            $('#cancelModal').modal('hide');
            $('#bookingUpdatingDialog').dialog('close');
        });
    });
    $('#updatingBooking').click(function() {
        var getData = $('#data-edit').val();
        var inputDate = {
            id: idBooking,
            startTime: getData + ' ' + $('#bookingUpdatingStartTimepicker').val(),
            endTime: getData + ' ' + $('#bookingUpdatingEndTimepicker').val(),
            roomId: localStorage['roomId'],
            comment: $('#kid-comment').val(),
        };
        updatingBooking(inputDate);
        $('#' + id).removeClass('highlight-active');
    });

    $('#selectUser').on('change', function() {
        $('#kids').empty();
        var idUser = $(this).val();
        var getKidsUrl = 'get-kids/' + idUser;
        addKids(getKidsUrl);
    });
});

$(function() {
    $('#date-booking').change(function() {
        refreshTable(localStorage['bookingsState']);
    });
    $('.picker').timepicker({
        timeFormat: 'H:i',
        step: 1,
        minTime: '07:00',
        maxTime: '20:00'
    });

});

function selectRoomForManager(roomId) {
    refreshTable(localStorage['bookingsState']);
    $.ajax({
        url: 'getroomproperty/' + roomId,
        encoding:'UTF-8',
        contentType: 'charset=UTF-8',
        success: function(result){
            result = result.split(' ');

            $('#bookingStartTimepicker').val(result[0]);
            $('#bookingEndTimepicker').val(result[1]);

            roomWorkingStartTime = result[0];
            roomWorkingEndTime = result[1];

            $('.picker').timepicker('option', 'minTime', roomWorkingStartTime);
            $('.picker').timepicker('option', 'maxTime', roomWorkingEndTime);

            roomCapacity = result[2];
        }
    });
}

function bookedBooking() {
    localStorage['bookingsState'] = ['BOOKED'];
    refreshTable(localStorage['bookingsState']);
}

function activeBooking() {
    localStorage['bookingsState'] = ['ACTIVE'];
    refreshTable(localStorage['bookingsState']);
}

function leavedBooking() {
    localStorage['bookingsState'] = ['COMPLETED'];
    refreshTable(localStorage['bookingsState']);
}

function allBooking() {
    localStorage['bookingsState'] = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    refreshTable(localStorage['bookingsState']);
}

function chekBookingState(){
    $('.btn-raised').removeClass('btn-info');
    if(localStorage['bookingsState'] === 'ACTIVE,BOOKED,CALCULATE_SUM,COMPLETED'){
        $('#btn-all').addClass('btn-info');
    } else if(localStorage['bookingsState'] === 'ACTIVE'){
        $('#btn-active').addClass('btn-info');
    } else if(localStorage['bookingsState'] ==='COMPLETED'){
        $('#btn-leaved').addClass('btn-info');
    } else{
        $('#btn-booked').addClass('btn-info');
    }
}

function refreshTable(bookingsState) {
    chekBookingState();
    var time = $('#date-booking').val();
    var idRoom = localStorage['roomId'];
    src = 'dailyBookings/' + time + '/' + idRoom + '/' + bookingsState;
    jQuery.extend({
        getValues: function() {
            var result = null;
            $.ajax({
                url: src,
                encoding:'UTF-8',
                contentType: 'charset=UTF-8',
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
        language: {
            processing:     messages.dateTable.processing,
            search:         messages.dateTable.search,
            lengthMenu:     messages.dateTable.lengthMenu,
            info:           messages.dateTable.info,
            infoEmpty:      messages.dateTable.infoEmpty,
            infoFiltered:   messages.dateTable.infoFiltered,
            loadingRecords: messages.dateTable.loadingRecords,
            zeroRecords:    messages.dateTable.zeroRecords,
            emptyTable:     messages.dateTable.emptyTable,
            paginate: {
                first:      messages.dateTable.paginate.first,
                previous:   messages.dateTable.paginate.previous,
                next:       messages.dateTable.paginate.next,
                last:       messages.dateTable.paginate.last
            },
            aria: {
                sortAscending:  messages.dateTable.aria.sortAscending,
                sortDescending: messages.dateTable.aria.sortDescending
            }
        },
        rowId: 'id',
        'columnDefs': [{
            'searchable': false,
            'orderable': false,
            'targets': 0
        }],
        'order': [
            [1, 'asc']
        ],
        'data': data,
        'columns': [{
            'data': null
        },
        {
            'data': 'kidName',
            'className': 'kid-name',
            'fnCreatedCell': function (nTd, sData, oData) {
                if (!(oData.comment == '')) {
                    $(nTd).html('<a href=profile?id=' + oData.idChild + '>' + oData.kidName + '</a>' + ' '
                        + '<span data-toggle="tooltip"' + 'id="comment-'+oData.id
                        + '" class="glyphicon glyphicon-info-sign"' + 'title="'
                        + oData.comment +  '"></span>');
                } else {
                    $(nTd).html('<a href=profile?id=' + oData.idChild + '>' + oData.kidName + '</a>');
                }
            }
        },
        {
            'data': 'durationBooking',
            'className': 'edit-button',
            'fnCreatedCell': function(nTd, sData, oData) {
                var td = '<span class="book-id" id =' + oData.id + '><span id="book-start-time">'
                    + oData.startTime + '</span> - ' + '<span id="book-end-time">' + oData.endTime + '</span></span>';
                if(localStorage['bookingsState'] ==['BOOKED']){
                    td+= '<button class="btn btn-sm glyphicon glyphicon-edit edit-button-btn"></button>';
                }
                $(nTd).empty();
                $(nTd).append(td);
            }
        },
        {
            'data': 'startTime',
            'className': 'arrivalTime',
            'fnCreatedCell': function(nTd) {
                var td = '<input type="time" class="form-control inp-arrivalTime" >'
                    + '<button class="btn btn-sm btn-success glyphicon glyphicon-arrow-right" ' +
                    'data-toggle="tooltip" title="' + messages.booking.hint.arrivedTime + '" id="arrival-btn" ></button>';
                $(nTd).empty();
                $(nTd).append(td);
            }
        },
        {
            'data': 'endTime',
            'className': 'leaveTime',
            'fnCreatedCell': function(nTd) {
                var td = '<input type="time" class="form-control inp-leaveTime" >'
                    + '<button class="btn btn-sm btn-success glyphicon glyphicon-arrow-right" ' +
                    'data-toggle="tooltip" title="' + messages.booking.hint.leaveTime + '" id="leave-btn" ></button>';
                $(nTd).empty();
                $(nTd).append(td);
            }
        }
        ],
        'pagingType': 'simple_numbers'
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
    checkTablePage();
}
function checkTablePage() {
    $('#booking-table_previous').hide();
    $('#booking-table_paginate').click(function () {
        if ($('#booking-table_paginate').find('.active a.page-link').html() == 1)
            $('#booking-table_previous').hide();
    });
    $('#booking-table').find('th').click(function () {
        if ($('#booking-table_paginate').find('.active a.page-link').html() == 1)
            $('#booking-table_previous').hide();
    });
}

function validateRoomTime(time){
    var result = false;
    if(time != '' && time >= roomWorkingStartTime && time <= roomWorkingEndTime){
        result = true;
    }
    return result;
}

function setStartTime(id, startTime) {

    if(validateRoomTime(startTime)) {
       sendStartTime(id, startTime);
    } else {
        $('#startTimeOutOfRange').modal('show');
    }
}

function sendStartTime(id , startTime) {
    var inputData = {
        startTime: startTime,
        id: id,
        roomId : localStorage['roomId']
    };
    $.ajax({
        url: 'setTime',
        encoding:'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(inputData),
        type: 'POST',
        success: function() {
            $('#' + id).addClass('highlight-active');
            refreshTable(localStorage['bookingsState']);
        }
    });
}

$('#setEndTime').click(function () {
    var id = $('#endTimeOutOfRange').data('id');
    var time = $('#endTimeOutOfRange').data('time');
    sendEndTime(id, time);
});

function setEndTime(id, time) {
    if ($('#' + id).find('.inp-arrivalTime').val() < $('#' + id).find('.inp-leaveTime').val()) {
       if(validateRoomTime(time)) {
           sendEndTime(id, time);
       } else {
           $('#endTimeOutOfRange').data('id', id);
           $('#endTimeOutOfRange').data('time', time);
           $('#endTimeOutOfRange').modal('show');
       }
    } else {
        $('#invalidTimeModal').modal('show');
    }
}

function sendEndTime(id, endTime) {
    var inputData = {
        endTime: endTime,
        id: id
    };
    $.ajax({
        url: 'setEndTime',
        encoding:'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(inputData),
        type: 'POST',
        success: function() {
            $('#' + id).addClass('highlight-complet');
            refreshTable(localStorage['bookingsState']);
        }
    });
}
function addHilighted(bookings) {
    $.each(bookings, function(index, value) {
        if (value.bookingState == 'ACTIVE') {
            var row = table.row('#' + value.id).node();
            $(row).addClass('highlight-active');
            $('#' + value.id).find('.inp-arrivalTime').val(value.startTime);
        } else if (value.bookingState == 'COMPLETED') {
            var row = table.row('#' + value.id).node();
            $(row).addClass('highlight-complet');
            $('#' + value.id).find('.inp-arrivalTime').val(value.startTime);
            $('#' + value.id).find('.inp-leaveTime').val(value.endTime);
        }
    });
}

function cancelBooking() {

    var str = 'cancelBook/' + idBooking;
    $.ajax({
        url: str,
        success: function() {
            $('#cancelModal').modal('hide');
            $('#bookingUpdatingDialog').dialog('close');
            refreshTable(localStorage['bookingsState']);
        }
    });
}

function updatingBooking(inputDate) {
    $.ajax({
        url: 'change-booking',
        type: 'POST',
        encoding:'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(inputDate),
        success: function(data) {
            if (data) {
                refreshTable(localStorage['bookingsState']);
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
        encoding:'UTF-8',
        contentType: 'charset=UTF-8',
        success: function(result) {
            var kids = JSON.parse(result);
            var kidsCount = kids.length;
            var tr = '';
            var label = '<div>' + '<label id="choose-kid"></label>' +'</div>';
            $.each(kids, function(i, kid) {
                tr += '<br><div >' + '<label><input type="checkbox"' + 'id="checkboxKid' + kid.id + '">' + kid.firstName
                + '<input type = "text"' + 'class="form-control"' + 'id="child-comment-'
                + kid.id + '">' + '</div>';
            });
            $('#kids-count').val(kidsCount);
            $('#kids').append(label).append(tr);
            $('[id^=child-comment]').attr('placeholder',messages.modal.kid.comment);
            if (kidsCount > 0) {
                $('#choose-kid').text(messages.modal.kid.choose);
            }
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
        encoding:'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        url: 'makenewbooking',
        dataType: 'json',
        data: JSON.stringify(bookingsArray),
        success: function(result) {
            if (result == '') {
                $('#updatingInvalid').modal('show');
                $('#bookingDialog').dialog();
            } else {
                $('#createSuccess').modal('show');
                setTimeout(function () {
                    $('#createSuccess').modal('hide');
                }, 1300);
                refreshTable(localStorage['bookingsState']);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (thrownError === 'Not Acceptable') $('#creatingfailue1').modal('show');
            else if (thrownError === 'Bad Request') $('#creatingfailue2').modal('show');
            else {
                //todo refatcor this tresh
                alert('Unfortunately could not create new booking, please contact admin');
            }
        }
    });
}

$('#booking-table tbody').on('focus', '.inp-arrivalTime', function() {
    var time = new Date().toString().match(/\d{2}:\d{2}/)[0];
    $(this).val(time);
});

$('#booking-table tbody').on('click', '#arrival-btn', function() {
    var tr = $(this).closest('tr');
    var id = table.row(tr).data().id;
    var time = $(this).closest('td').find('input').val();
    setStartTime(id, time);
});


$('#booking-table tbody').on('click', '.inp-leaveTime', function() {
    var leaveTime = $(this).val();
    var arrivalTime = $(this).parents('tr').find('.inp-ArrivalTime').val();
    if (arrivalTime === '') {
        $('#failureNoArriveTime').modal('show');
    } else if(leaveTime === '') {
        var time = new Date().toString().match(/\d{2}:\d{2}/)[0];
        $(this).val(time);
    }
    $('#booking-table tbody').on('click', '#leave-btn', function() {
        var tr = $(this).closest('tr');
        var id = table.row(tr).data().id;
        var time = $(this).closest('td').find('input').val();
        setEndTime(id, time);
    });
});

$('#booking-table tbody').on('click', '[id^=comment]', function() {
    var comment = $(this).attr('title');
    $('#kidCommentMessage').find('h4').html(comment);
    $('#kidCommentMessage').modal('show');
});


$('#booking-table tbody').on('click', '.edit-button-btn', function() {
    idBooking = $(this).closest('td').find('.book-id').attr('id');
    var comment = $('#comment-' + idBooking).attr('title');
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

$('#bookingUpdatingDialog').on('dialogclose', function() {
    $('#' + idBooking).removeClass('highlight-active');
});

function handler() {
    var selected = $(this).hasClass('highlight-row');
    $('#booking-table > tbody > tr').removeClass('highlight-row');
    if(!selected){
        $(this).addClass('highlight-row');
    }
}
$('#booking-table > tbody').on( 'click', 'tr', handler);

$('#closeBookingsLegend').click(function () {
    $('#bookingLegendModal').modal('hide');
});


