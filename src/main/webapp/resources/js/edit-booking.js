var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState = localStorage["bookingsState"];
var table = null;


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

$(function() {


    $('#date-booking').change(function() {
        refreshTable(localStorage["bookingsState"]);
    });
    $('.picker').timepicker({
        timeFormat: 'H:i',
        step: 15,
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
                        + oData.comment + '"></span>');
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

function openCreateBookingDialog() {
    var date = $('#date-booking').val();
    $('#bookingStartDate').val(date);
    $('#bookingDialog').dialog();
}

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
                refreshTable(localStorage["bookingsState"]);
            }
        },
        error: function() {
            alert("Unfortunately could not create new booking, please contact admin");
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
    var time = dateNow.toString().match(/\d{2}:\d{2}/)[0];
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