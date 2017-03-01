function cancelBooking(idBook) {
    $('#' + idBook).addClass('highlightedRed');
    $('#cancelModal').find('#closeCencel').click(function () {
        $('#' + idBook).removeClass('highlightedRed');
    });

    $('#cancelModal').find('#cancelButton').click(function () {
        var str = 'cancelBooking/' + idBook;
        $.ajax({
            url: str,
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            success: function (result) {
                $('#' + result.id).hide();
            }
        });
        $('#cancelModal').modal('hide');
    });
}


function setStartTime(idBooking) {

    var idElement = '#' + idBooking;
    var inputData = {
        startTime: $(idElement).find('#arrivalTime').val(),
        id: idBooking,
        roomId : localStorage['roomId']
    };
    $.ajax({
        url: 'setTime',
        contentType: 'application/json',
        data: JSON.stringify(inputData),
        type: 'PUT',
        success: function (data) {
            var obj = JSON.parse(data);
            var bookingTime = $(idElement).find('.bookingTime');
            bookingTime.empty();
            bookingTime.append(obj.startTime + ' - ' + obj.endTime);
            $(idElement).addClass('highlight-active');
        }
    });
}


function setEndTime(idBooking) {

    var idElement = '#' + idBooking;
    var inputData = {
        endTime: $(idElement).find('#leaveTime').val(),
        id: idBooking
    };

    $.ajax({
        url: 'BookDuration',
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify(inputData),
        success: function (data) {
            var bookingObj = JSON.parse(data);
            if (bookingObj.durationLong > 0) {
                $.ajax({
                    url: 'setEndTime',
                    contentType: 'application/json',
                    data: JSON.stringify(inputData),
                    type: 'PUT',
                    success: function (data) {
                        var obj = JSON.parse(data);
                        var bookingTime = $(idElement).find('.bookingTime');
                        bookingTime.empty();
                        bookingTime.append(obj.startTime + ' - ' + obj.endTime);
                        $(idElement).addClass('highlight-complet');
                    }
                });
            } else {
                $('#invalidTimeModal').modal('show');
            }
        }
    });
}


$('.input-group').find('#leaveTime').on('click', dateNow)
    .find('#arrivalTime').on('click', dateNow);


$.getJSON('listBook', function (list) {
    $.each(list, function (index, value) {
        if (value.bookingState == 'ACTIVE') {
            $('#' + value.id).addClass('highlight-active')
                .find('#arrivalTime').val(value.startTime);
        } else if (value.bookingState == 'COMPLETED') {
            $('#' + value.id).addClass('highlight-complet')
                .find('#arrivalTime').val(value.startTime)
                .find('#leaveTime').val(value.endTime);
        }
    });
});

function changeBooking(id) {

    $('#wrong-interval').hide();
    $('#fill-in').hide();
    var idElement = '#' + id;
    $('#change-booking-modal').find('#change-booking').click(function () {
        var getData = $(this).closest('.modal-dialog');
        var inputDate = {
            id: id,
            startTime: getData.find('#data').val() + ' ' + getData.find('#startTime').val(),
            endTime: getData.find('#data').val() + ' ' + getData.find('#endTime').val(),
            comment: ''
        };

        $.ajax({
            url: 'updatebooking',
            type: 'POST',
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(inputDate),
            success: function (data) {
                if (!data) {
                    $('#wrong-interval').show();
                } else

                    $('#change-booking-modal').modal('hide');
            }
        });

    });

}


function selectRoomForManager(roomId) {
    var src = 'manager-daily-booking/' + roomIdF;
    $.ajax({
        url: src,
        success: function (data) {
            var bookings = JSON.parse(data);
            var tr = '';
            $.each(bookings, function (i, booking) {
                var startButton = 'onclick=' + '"' + 'setStartTime(' + booking.id + ')"';
                var endButton = 'onclick=' + '"' + 'setEndTime(' + booking.id + ')"';
                tr += '<tr id=' + booking.id + ' class="trbooking"><td>'
                    + '<a href=profile?id=' + booking.idChild + '>'
                    + booking.kidName + '</td>'
                    + '<td>' + booking.startTime + " -" + booking.endTime + '</td>'
                    + '<td><div class="input-group">' + " " + '<input type="time"' + 'id="arrivalTime"' + 'class="form-control"/>'
                    + '<span class="input-group-btn">'
                    + '<input type="button"' + 'class="btn btn-raised btn-sm btn-info"'
                    + startButton + 'value="Set"' + '</input></span></td></div>'
                    + '<td><div class="input-group"><input type="time"' + 'id="leaveTime"' + 'class="form-control"/>'
                    + '<span class="input-group-btn">'
                    + '<input type="button"' + 'class="btn btn-raised btn-sm btn-info"'
                    + endButton + 'value="Set"' + '</input></span></td></div>'
                    + '</tr>';
            });
            $('.trbooking').remove();
            $('.table').append(tr);
            $.each(bookings, function (index, value) {
                if (value.bookingState == 'ACTIVE') {
                    $('#' + value.id).addClass('highlight-active')
                        .find('#arrivalTime').val(value.startTime);
                } else if (value.bookingState == 'COMPLETED') {
                    $('#' + value.id).addClass('highlight-complet')
                        .find('#arrivalTime').val(value.startTime)
                        .find('#leaveTime').val(value.endTime);
                }
            });

        }
    });
}
