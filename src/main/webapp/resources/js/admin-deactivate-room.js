$().ready(function () {

    var roomId;
    var btn;
    var dialog;


    $('.activate').click(function () {
        btn = this;
        roomId = getRoomProp(constants.room.properties.id);   //get room id from table
        var isActive = getRoomProp(constants.room.properties.isActive);
        if (isActive === 'true') {
            dialog = $('#deactivateModal');
            $('#reasonDeactivate').css('display', 'none');
            verifyRoomBookingState(roomId);
        } else {
            dialog = $('#activateModal');
        }

        dialog.modal('show');
    });

    $('#deactivateYesButton').click(function () {
        if($('#reasonDeactivate').valid()){
            var reasonText = $('#reasonText').val();
            changeActiveRoomState(roomId, btn, reasonText);
            dialog.modal('hide');
        }
        
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
                $('#warningMessages').html('');
                $.each(data, function (index, value) {
                    $('#warningMessages').append('<div class = warningMessage>' + value + '</div>');
                    $('#reasonText').empty();
                    $('#reasonDeactivate').css('display', 'block');
                });
            }
        });
    }

    function changeActiveRoomState(roomId, btn, reason) {
        var src = 'adm-edit-room';
        var inputData = {
            id: roomId,
            reason: reason
        };
        $.ajax({
            url: src,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(inputData),
            async: false,
            type: 'POST',
            success: function (isActivated) {
                setRoomProp(constants.room.properties.isActive, isActivated);
                if (isActivated) {
                    setActivateClass(btn);
                } else {
                    setDeactivateClass(btn);
                }
            }
        });
    }

    function setDeactivateClass(btn) {
        $(btn).removeClass('deactivateButton save');
        $(btn).addClass('activateButton delete');
    }

    function setActivateClass(btn) {
        $(btn).removeClass('activateButton delete');
        $(btn).addClass('deactivateButton save');
    }

    function getRoomProp(propIndex) {

        return $(btn).parents('tr').find('td').eq(propIndex).text();
    }

    function setRoomProp(propIndex, prop) {
        $(btn).parents('tr').find('td').eq(propIndex).text(prop);
    }
});

