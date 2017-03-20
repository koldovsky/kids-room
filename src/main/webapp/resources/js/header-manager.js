$(function () {
    $('.datepickers').datepicker({
        dateFormat: constants.parameters.dateFormat,
        setDate: moment().format(constants.parameters.dateFormatUpperCase)
    });

    $('.picker').keypress(function (e) {
        if (e.which != 8 && e.which != 0 && e.which != 58 && (e.which < 48 || e.which > 57)) {
            return false;
        }
    });

    var currentDate = moment().format(constants.parameters.dateFormatUpperCase);
    var listRoom = $('#selectRoom li a');
    var inactiveRoom = true;
    for (var i = 0; i < listRoom.length; i++) {
        if ((localStorage['roomId']) == listRoom[i].id) {
            inactiveRoom = false;
            break;
        }
    }
    if (inactiveRoom) {
        localStorage['room'] = $('#selectRoom li a').first().text();
        localStorage['roomId'] = $('#selectRoom li a').first().attr('id');
    }
    $('#selectRoom li a').each(function () {
        $(this).click(function () {
            var room = $(this).text();
            var roomId = $(this).attr('id');

            localStorage['room'] = room;
            localStorage['roomId'] = roomId;

            getAmountOfChildrenByCurrentDate(currentDate);
            $('#room').text(room);
            selectRoomForManager(roomId);
        });
    });
    getAmountOfChildrenByCurrentDate(currentDate);
    $('#room').text(localStorage['room']);
    selectRoomForManager(localStorage['roomId']);

});

function getAmountOfChildrenByCurrentDate(currentDate) {
    $.ajax({
        url: 'restful/manager-booking/amountOfKids/' + currentDate + '/' + localStorage['roomId'],
        success: function (result) {
            $('#amountOfChildren').text(result);
        }
    });
}
