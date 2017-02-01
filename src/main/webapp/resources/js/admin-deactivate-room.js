$().ready(function () {

    var roomId;
    var btn;
    var dialog;

    $('.activate').click(function () {
        btn = this;
        roomId = getRoomProp(constants.room.properties.id);   //get room id from table
        var isActive =  getRoomProp(constants.room.properties.isActive);
        if(isActive === 'true') {
            dialog = $('#deactivateModal');
            verifyRoomBookingState(roomId);
        } else {
            dialog = $('#activateModal');
        }

        dialog.modal('show');
    });

    $('#deactivateYesButton').click(function () {
        changeActiveRoomState(roomId, btn);
        dialog.modal('hide');
    });

    $('#deactivateNoButton').click(function () {
        dialog.modal('hide');
    });

    $('#activateYesButton').click(function () {
        changeActiveRoomState(roomId, btn);
        dialog.modal('hide');
    });

    function verifyRoomBookingState(roomId) {
        var src = 'adm-edit-room\\warnings';
        var inputData = {id: roomId};
        var warningMessages = [];
        $.ajax({
            url: src,
            data: inputData,
            success: function (data) {
                data = JSON.parse(data);
                if (data != []) {
                    if (data.includes(constants.room.warnings.active)) {
                        warningMessages.push(messages.room.warnings.active);
                    }
                    if (data.includes(constants.room.warnings.planning)) {
                        warningMessages.push(messages.room.warnings.planning);
                    }
                    $('#warningMessages').html('');
                    $.each(warningMessages, function (index, value) {
                        $('#warningMessages').append('<div class = warningMessage>' + value + '</div>');
                    })
                }
            }
        });
    }

    function getRoomProp(propIndex) {

        return $(btn).parents('tr').find('td').eq(propIndex).text();
    }

    function setRoomProp(propIndex, prop) {
        $(btn).parents('tr').find('td').eq(propIndex).text(prop);
    }

    function changeActiveRoomState(roomId, btn) {
        var src = 'adm-edit-room';
        $.ajax({
            url: src,
            type: 'POST',
            data: roomId,
            contentType: 'application/json; charset=utf-8',
            success: function (isActivated) {

                setRoomProp(constants.room.properties.isActive, isActivated);

                if(isActivated) {
                    setActivateClass(btn);
                } else {
                    setDeactivateClass(btn)
                }
            }
        });
    }

    function setDeactivateClass(btn) {
        $(btn).removeClass('deactivateButton save')
        $(btn).addClass('activateButton delete')
    }

    function setActivateClass(btn) {
        $(btn).removeClass('activateButton delete')
        $(btn).addClass('deactivateButton save')
    }
});

