/**
 * Listens 2 buttons with class .button-size-default.button.delete
 * and .button-size-default.button.save. When the click is done,
 * then confirmation dialog appears. Then if the admin confirms
 * some action then the submit button is presses on jsp with the
 * following sending appropriate information to the controller.
 *
 * Created by Sviatoslav Hryb on 11-Dec-16.
 */

var activeManagerStatus;
var idManager;

$().ready(function() {

    $('#confirmation-dialog-event-div').dialog ({
        autoOpen: false,
        modal:true,
        width: 320,
    });

    $('.button-size-default.button.delete').click(function() {
        activeManagerStatus = false;
        $('#active-manager-span').show();
        $('#confirmation-dialog-event-div').dialog('open');
    });

    $('.button-size-default.button.save').click(function() {
        activeManagerStatus = true;
        $('#inactive-manager-span').show();
        $('#confirmation-dialog-event-div').dialog('open');
    });

    $('#confirmYesEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
        if (activeManagerStatus) {
            $('#submit-manager-inactive-' + idManager).click();
            $('#inactive-manager-span').hide();
        }
        else {
            $('#submit-manager-active-' + idManager).click();
            $('#active-manager-span').hide();
        }
    });

    $('#confirmNoEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
        if (activeManagerStatus)
            $('#active-manager-span').hide();
        else
            $('#inactive-manager-span').hide();
    });

});
