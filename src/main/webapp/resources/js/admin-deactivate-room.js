$(function () {

    var roomId;
    var btn;
    var dialog;

    $('#activateModal, #deactivateModal').dialog({
        modal: true,
        resizable: false,
        autoOpen: false,
        width: 300
    });
    $('#rooms-table').on('change', '.activate', function(){
        btn = this;
        roomId = getRoomProp(constants.room.properties.id);
        if(this.checked){
            dialog = $('#activateModal').dialog();
        }
        else{
            $('#reasonDeactivate').css('display', 'none');
            verifyRoomBookingState(roomId);
            dialog = $('#deactivateModal').dialog();
        }

        dialog.dialog('open');

    });

    $('#deactivateYesButton').click(function () {
        if($('#reasonDeactivate').valid()){
            var reasonText = $('#reasonText').val();
            changeActiveRoomState(roomId, btn, reasonText);
            dialog.dialog('close');
        }
        
    });

    $('#deactivateNoButton').click(function () {
        $(btn).prop('checked', true);
        dialog.dialog('close');
    });

    $('#activateYesButton').click(function () {
        changeActiveRoomState(roomId, btn);
        $(btn).parents('tr').removeClass('tr-not-active').addClass('room');
        dialog.dialog('close');
    });

    $('#activateNoButton').click(function () {
        $(btn).prop('checked', false);
        dialog.dialog('close');
    });
    function verifyRoomBookingState(roomId) {
        var src = 'restful/admin/rooms/deactivate-check';
        var inputData = {id: roomId};
        var warningMessages = [];
        $.ajax({
            url: src,
            data: inputData,
            success: function (data) {
                $('#warningMessages').html('');
                $.each(data, function (index, value) {
                    $('#warningMessages').append('<div class = warningMessage>' + value + '</div>');
                    $('#reasonText').val('');
                    $('#reasonDeactivate').css('display', 'block');
                });
            }
        });
    }

    function changeActiveRoomState(roomId, btn, reason) {
        var src = 'restful/admin/rooms/deactivate';
        var inputData = {
            id: roomId,
            reason: reason
        };
        $.ajax({
            url: src,
            dataType: 'json',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(inputData),
            success: function (isActivated) {
                if(isActivated){
                    $(btn).parents('tr').removeClass('tr-not-active').addClass('room');
                }
                else{
                    $(btn).parents('tr').removeClass('room').addClass('tr-not-active');
                }
            }
        });
    }

    function getRoomProp(propIndex) {
        return $(btn).closest('tr').attr('id');
    }
});

