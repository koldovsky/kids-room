var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState = localStorage['bookingsState'];
var table = null;
var roomCapacity; // The capacity (number of people) current room
var roomWorkingStartTime;
var roomWorkingEndTime;


$(function () {
    $('#bookingDialog').attr('accept-charset', 'UTF-8');
    if (localStorage['bookingsState'] == null) {
        localStorage['bookingsState'] = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    }
    $('#booking-table').show();
    $('[data-toggle="tooltip"]').tooltip();
    $('#booking').click(function () {
        bookingsArray = [];
        var idUser = $('#selectUser :selected').val();
        var date = $('#bookingStartDate').val();
        var startTime = $('#bookingStartTimepicker').val();
        var endTime = $('#bookingEndTimepicker').val();
        var startISOtime = date + 'T' + startTime + ':00';
        var endISOtime = date + 'T' + endTime + ':00';

        if (validateEmptyTime(startTime, endTime)) {
            var urlForKids = 'restful/manager-booking/' + idUser;
            $.ajax({
                url: urlForKids,
                encoding: 'UTF-8',
                contentType: 'charset=UTF-8',
                success: function (result) {
                    var kids = result;
                    $.each(kids, function (i, kid) {
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

    $('#deletingBooking').on('click', function () {
        $('#cancelModal').modal('show');
        $('#cancelButton').on('click', function () {
            cancelBooking();
        });
        $('#closeCencel').on('click', function () {
            $('#cancelModal').modal('hide');
            $('#bookingUpdatingDialog').dialog('close');
        });
    });

    $('#updatingBooking').click(function () {
        var getData = $('#data-edit').val();
        var inputDate = {
            id: idBooking,
            startTime: getData + 'T' + $('#bookingUpdatingStartTimepicker').val() + ':00',
            endTime: getData + 'T' + $('#bookingUpdatingEndTimepicker').val() + ':00',
            roomId: localStorage['roomId'],
            comment: $('#kid-comment').val(),
        };
        updatingBooking(inputDate);
        $('#' + id).removeClass('highlight-active');
    });

    $('#selectUser').on('change', function () {
        $('#kids').empty();
        var idUser = $(this).val();
        if(idUser===null) return;
        var getKidsUrl = 'restful/manager-booking/' + idUser;
        addKids(getKidsUrl);
    });

    $('#date-booking').change(function () {
        refreshTable(localStorage['bookingsState']);
        getAmountOfChildrenByCurrentDate($('#date-booking').val());
        getDayDiscount($('#date-booking').val());
    });

    $('.picker').timepicker({
        timeFormat: 'H:i',
        step: 1,
        minTime: roomWorkingStartTime,
        maxTime: roomWorkingEndTime
    });

    $("#selectUser").select2();
    getDayDiscount($('#date-booking').val());
});

$('#date-booking').val(moment().format(constants.parameters.dateFormatUpperCase));

function selectRoomForManager(roomId) {
    refreshTable(localStorage['bookingsState']);
    $.ajax({
        url: 'getroomproperty/' + roomId,
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        success: function (result) {
            getAmountOfChildrenByCurrentDate($('#date-booking').val());
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

function chekBookingState() {
    $('.btn-raised').removeClass('btn-info');
    if (localStorage['bookingsState'] === 'ACTIVE,BOOKED,CALCULATE_SUM,COMPLETED') {
        $('#btn-all').addClass('btn-info');
    } else {
        if (localStorage['bookingsState'] === 'ACTIVE') {
            $('#btn-active').addClass('btn-info');
        } else {
            if (localStorage['bookingsState'] === 'COMPLETED') {
                $('#btn-leaved').addClass('btn-info');
            } else {
                $('#btn-booked').addClass('btn-info');
            }
        }
    }
}

function refreshTable(bookingsState) {
    chekBookingState();
    var time = $('#date-booking').val();
    if(time == "" || time == null || time === undefined)
        time = moment().format(constants.parameters.dateFormatUpperCase);

    var idRoom = localStorage['roomId'];
    src = 'restful/manager-booking/' + time + '/' + idRoom + '/' + bookingsState;
    jQuery.extend({
        getValues: function () {
            var result = null;
            $.ajax({
                url: src,
                encoding: 'UTF-8',
                contentType: 'charset=UTF-8',
                async: false,
                success: function (data) {
                    result = data;
                }
            });
            return result;
        }
    });
    var results = $.getValues();
    var data = results;

    if (!(table === null)) {
        table.destroy();
    }

    table = $('#booking-table').DataTable({
        language: {
            processing: messages.dateTable.processing,
            search: messages.dateTable.search,
            lengthMenu: messages.dateTable.lengthMenu,
            info: messages.dateTable.info,
            infoEmpty: messages.dateTable.infoEmpty,
            infoFiltered: messages.dateTable.infoFiltered,
            loadingRecords: messages.dateTable.loadingRecords,
            zeroRecords: messages.dateTable.zeroRecords,
            emptyTable: messages.dateTable.emptyTable,
            paginate: {
                first: messages.dateTable.paginate.first,
                previous: messages.dateTable.paginate.previous,
                next: messages.dateTable.paginate.next,
                last: messages.dateTable.paginate.last
            },
            aria: {
                sortAscending: messages.dateTable.aria.sortAscending,
                sortDescending: messages.dateTable.aria.sortDescending
            }
        },
        rowId: 'id',
        'columnDefs': [{
            'searchable': false,
            'orderable': false,
            'targets': 0
        }],
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
                            + '<span data-toggle="tooltip"' + 'id="comment-' + oData.id
                            + '" class="glyphicon glyphicon-info-sign"' + 'title="'
                            + oData.comment + '"></span>');
                    } else {
                        $(nTd).html('<a href=profile?id=' + oData.idChild + '>' + oData.kidName + '</a>');
                    }
                }
            },
            {
                'data': 'durationBooking',
                'className': 'edit-button',
                'fnCreatedCell': function (nTd, sData, oData) {
                    var td = '<span class="book-id" id =' + oData.id + '><span id="book-start-time">'
                        + oData.startTime + '</span> - ' + '<span id="book-end-time">' + oData.endTime + '</span></span>';
                    if (localStorage['bookingsState'] == ['BOOKED']) {
                        td += '<button class="btn btn-sm glyphicon glyphicon-edit edit-button-btn"></button>';
                    }
                    $(nTd).empty();
                    $(nTd).append(td);

                }
            },
            {
                'data': 'startTime',
                'className': 'arrivalTime',
                'fnCreatedCell': function (nTd,sData,oData) {
                    var startTime;
                    if(oData.bookingState === 'ACTIVE'||oData.bookingState ==='COMPLETED'){
                        startTime = oData.startTime;
                    }else{
                        startTime = "";
                    }
                    var td = '<input type="text" class="inp-arrivalTime picker" '
                        + 'value="'+startTime+'">'
                        + '<button class="btn btn-sm btn-success glyphicon glyphicon-arrow-right" ' +
                        'data-toggle="tooltip" title="' + messages.booking.hint.arrivedTime + '" id="arrival-btn" ></button>';
                    $(nTd).empty();
                    $(nTd).append(td);
                }
            },
            {
                'data': 'endTime',
                'className': 'leaveTime',
                'fnCreatedCell': function (nTd, sData, oData) {
                    var endTime;
                    if(oData.bookingState ==='COMPLETED'){
                        endTime = oData.endTime;
                    }else{
                        endTime = "";
                    }
                    var td = '<input type="text" class="inp-leaveTime picker" '
                        + 'value="'+endTime+'">'
                        + '<button class="btn btn-sm btn-success glyphicon glyphicon-arrow-right" ' +
                        'data-toggle="tooltip" title="' + messages.booking.hint.leaveTime + '" id="leave-btn" ></button>';
                    $(nTd).empty();
                    $(nTd).append(td);
                }
            }
        ],
        'pagingType': 'simple_numbers',
        "drawCallback": function () {
            $('.picker').timepicker({
                timeFormat: 'H:i',
                step: 1,
                minTime: roomWorkingStartTime,
                maxTime: roomWorkingEndTime
            });
        }
    });

    table.on('search.dt', function () {
        table.column(0, {
            search: 'applied',
        }).nodes().each(function (cell, i) {
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

function setStartTime(id, startTime) {
    if (validateRoomTime(startTime)) {
        sendStartTime(id, startTime);
    } else {
        $('#startTimeOutOfRange').modal('show');
    }
}

function sendStartTime(id, startTime) {
    var inputData = {
        startTime: startTime,
        id: id,
        roomId: localStorage['roomId']
    };
    $.ajax({
        url: 'restful/manager-booking/startTime',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(inputData),
        type: 'PUT',
        success: function () {
            $('#' + id).addClass('highlight-active');
            refreshTable(localStorage['bookingsState']);
        }
    });
}
function setEndTime(id, startTime, endTime) {
    if (startTime < endTime) {
        if (validateRoomTime(endTime)) {
            sendEndTime(id, endTime);
        } else {
            $('#endTimeOutOfRange').data('id', id);
            $('#endTimeOutOfRange').data('time', endTime);
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
        url: 'restful/manager-booking/endTime',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(inputData),
        type: 'PUT',
        success: function () {
            $('#' + id).addClass('highlight-complet');
            refreshTable(localStorage['bookingsState']);
        }
    });
}

function addHilighted(bookings) {
    $.each(bookings, function (index, value) {
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
    var str = 'cancelBooking/' + idBooking;
    $.ajax({
        url: str,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        success: function () {
            $('#cancelModal').modal('hide');
            $('#bookingUpdatingDialog').dialog('close');
            refreshTable(localStorage['bookingsState']);
        }
    });
}

function updatingBooking(inputDate) {
    $.ajax({
        url: 'updatebooking',
        type: 'POST',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: JSON.stringify(inputDate),
        success: function (data) {
            if (data) {
                refreshTable(localStorage['bookingsState']);
                $('#updatingSuccess').modal('show');
                $('#bookingUpdatingDialog').dialog('close');
            } else {
                $('#updatingInvalid').modal('show');
            }
        },
        error: function (xhr) {
            $('#errorMessage').html(xhr.responseText);
            $('#errorWindow').modal('show');
        }
    });
}

function addKids(getKidsUrl) {
    $.ajax({
        url: getKidsUrl,
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        success: function (result) {
            var kids = result;
            var kidsCount = kids.length;
            var tr = '';
            var label = '<div>' + '<label id="choose-kid"></label>' + '</div>';
            $.each(kids, function (i, kid) {
                tr += '<br><div >' + '<label><input type="checkbox"' + 'id="checkboxKid' + kid.id + '">' + kid.firstName
                    + '<input type = "text"' + 'class="form-control"' + 'id="child-comment-'
                    + kid.id + '">' + '</div>';
            });
            $('#kids-count').val(kidsCount);
            $('#kids').append(label).append(tr);
            $('[id^=child-comment]').attr('placeholder', messages.modal.kid.comment);
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
//TODO-VL Here is function for sending new booking for server
function sendBookingToServerForCreate(bookingsArray) {
    $.ajax({
        type: 'post',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        url: 'makenewbooking',
        dataType: 'json',
        data: JSON.stringify(bookingsArray),
        success: function (result) {
            if (result == '') {
                $('#updatingInvalid').modal('show');
                $('#bookingDialog').dialog({modal: true, resizable: false});
            } else {
                $('#createSuccess').modal('show');
                setTimeout(function () {
                    $('#createSuccess').modal('hide');
                }, 1300);
                refreshTable(localStorage['bookingsState']);
                $('#kids').empty();
                clearModalVindow();
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            $('#errorMessage').html(xhr.responseText);
            $('#errorWindow').modal('show');
        }


    });
}

$('#booking-table tbody').on('click', '.inp-arrivalTime', function () {
    var time = new Date().toString().match(constants.regex.timeRegex)[0];
    $(this).val(time);
});

$('#booking-table tbody').on('click', '#arrival-btn', function () {
    var tr = $(this).closest('tr');
    var id = table.row(tr).data().id;
    var time = $(this).closest('td').find('input').val();
    setStartTime(id, time);
});

$('#booking-table tbody').on('click', '.inp-leaveTime', function () {
    var leaveTime = $(this).val();
    var arrivalTime = $(this).parents('tr').find('.inp-arrivalTime').val();
    if (arrivalTime === '') {
        $('#failureNoArriveTime').modal('show');
    } else if (leaveTime === '') {
        var time = new Date().toString().match(constants.regex.timeRegex)[0];
        $(this).val(time);
    }

});
//TODO-VL here is method to set leave time
$('#booking-table tbody').on('click', '#leave-btn', function () {
    var tr = $(this).closest('tr');
    var id = table.row(tr).data().id;
    var endTime = $(this).closest('td').find('input').val();
    var startTime = table.row(tr).data().startTime;
    setEndTime(id, startTime, endTime);
});
$('#booking-table tbody').on('click', '[id^=comment]', function () {
    var comment = $(this).attr('title');
    $('#kidCommentMessage').find('h4').html(comment);
    $('#kidCommentMessage').modal('show');
});


$('#booking-table tbody').on('click', '.edit-button-btn', function () {
    idBooking = $(this).closest('td').find('.book-id').attr('id');
    var comment = $('#comment-' + idBooking).attr('title');
    var date = $('#date-booking').val();
    var startTime = $(this).closest('td').find('#book-start-time').text();
    var endTime = $(this).closest('td').find('#book-end-time').text();
    $('#kid-comment').val(comment);
    $('#bookingUpdatingStartTimepicker').val(startTime);
    $('#bookingUpdatingEndTimepicker').val(endTime);
    $('#data-edit').val(date);

    $('#bookingUpdatingDialog').dialog({modal: true, resizable: false});
    $('#' + idBooking).addClass('highlight-active');
});

$('#bookingUpdatingDialog').on('dialogclose', function () {
    $('#' + idBooking).removeClass('highlight-active');
});

function handler() {
    var selected = $(this).hasClass('highlight-row');
    $('#booking-table > tbody > tr').removeClass('highlight-row');
    if (!selected) {
        $(this).addClass('highlight-row');
    }
}

$('#booking-table > tbody').on('click', 'tr', handler);

$('#closeBookingsLegend').click(function () {
    $('#bookingLegendModal').modal('hide');
});

clearModalVindow = function () {
    $('#selectUser').select2('val', ' ');
    $('#bookingStartTimepicker').val(roomWorkingStartTime);
    $('#bookingEndTimepicker').val(roomWorkingEndTime);
}


function getDayDiscount(date) {
    if(date == "" || date == null || date === undefined)
        date = moment().format(constants.parameters.dateFormatUpperCase);

    let array = date.split('-');
    let startDate = `${array[0]}-${array[1]}-${array[2]}`;
    let request = `restful/discount/${startDate}/${startDate}/00:00:00/23:59:59`;

    $.ajax({
        url: request,
        type: 'GET',
        success: function (result) {
            let users = result;
            let tr = "";

            if (users.length > 0) {
                $('#discounts-list tbody').html('');

                $.each(users, function (i, discount) {
                    tr += '<tr>' +
                        '<td>' + discount.startTime + '</td>' +
                        '<td>' + discount.endTime + '</td>' +
                        '<td>' + discount.value + '%</td>' +
                        '<td>' + discount.reason + '</td></tr>';

                });

                $("#discount-table").append(tr);
                $("#discounts-list").show();
            } else {
                $("#discounts-list").hide();
            }
        }
    })
}