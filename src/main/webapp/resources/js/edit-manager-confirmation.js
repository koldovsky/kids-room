/**
 * Listens 2 buttons with class .button-size-default.button.delete
 * and .button-size-default.button.save. When the click is done,
 * then confirmation dialog appears. Then if the admin confirms
 * some action then the submit button is presses on jsp with the
 * following sending appropriate information to the controller.
 *
 * Created by Sviatoslav Hryb on 11-Dec-16.
 */

var currentFormElement;

$().ready(function() {

    $('#confirmation-dialog-event-div').dialog ({
        autoOpen: false,
        modal:true,
        width: 320,
    });

    $('.submit-manager-active').click(function() {
        currentFormElement =  $(this).prev('form');
        $('#inactive-manager-span').hide();
        $('#active-manager-span').show();
        $('#confirmation-dialog-event-div').dialog('open');
    });

    $('.submit-manager-inactive').click(function() {
        currentFormElement =  $(this).prev('form');
        $('#active-manager-span').hide();
        $('#inactive-manager-span').show();
        $('#confirmation-dialog-event-div').dialog('open');
    });

    $('#confirmYesEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
        currentFormElement.submit();
    });

    $('#confirmNoEvent').click(function() {
        $('#confirmation-dialog-event-div').dialog('close');
    });
});
