/**
 * Listens 2 buttons with class .button-size-default.button.delete
 * and .button-size-default.button.save. When the click is done,
 * then confirmation dialog appears. Then if the admin confirms
 * some action then the submit button is presses on jsp with the
 * following sending appropriate information to the controller.
 *
 */

var currentFormElement;

$().ready(function() {

    $('#confirmation-dialog-event-div').dialog ({
        autoOpen: false,
        modal:true,
        width: 320
    });

    $('.activate').click(function() {
        managerId = $(this).parents('tr').find('td').eq(0).text();
        btn = $(this);
        $('#inactive-manager-span').hide();
        $('#active-manager-span').show();
        $('#confirmation-dialog-event-div').dialog('open');
    });

    $('#confirmYesEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
        var src = 'adm-edit-manager';
        $.ajax({
            url: src,
            method: 'POST',
            data:  managerId,
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result == 'true') {
                    setActivateClass(btn);
                } else {
                    setDeactivateClass(btn);
                }
            }
        });
    });

    $('#confirmNoEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
    });

    function setDeactivateClass(btn) {
        $(btn).removeClass('deactivateButton save')
        $(btn).addClass('activateButton delete')
    }

    function setActivateClass(btn) {
        $(btn).removeClass('activateButton delete')
        $(btn).addClass('deactivateButton save')
    }
});
