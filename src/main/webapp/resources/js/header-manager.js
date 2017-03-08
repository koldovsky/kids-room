$(function () {
    $('.datepickers').datepicker({
        dateFormat: 'yy-mm-dd',
        setDate: moment().format("YYYY-MM-DD")
    });

    var currentDate = moment().format("YYYY-MM-DD");
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
        url: 'getAmountOfChildren/' + currentDate + '/' + localStorage['roomId'],
        success: function (result) {
            $('#amountOfChildren').text(result);
        }
    });
}