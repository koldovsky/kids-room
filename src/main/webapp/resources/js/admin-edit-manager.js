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

    var managerId;
    var btn;

    $('.activate').click(function() {
        managerId = $(this).parents('tr').find('td').eq(0).text();
        btn = $(this);
        if($(btn).hasClass('activateButton')) {
            $('#inactive-manager-span').hide();
            $('#active-manager-span').show();
        } else {
            $('#active-manager-span').hide();
            $('#inactive-manager-span').show();
        }
        $('#confirmation-activate').modal('show');
    });

    $('#confirmYesEvent').click(function() {
        $('#confirmation-activate').modal('hide');
        var src = 'adm-edit-manager';
        $.ajax({
            url: src,
            method: 'POST',
            data:  managerId,
            contentType: 'application/json; charset=utf-8',
            success: function (isActive) {
                if(isActive) {
                    setActivateClass(btn);
                } else {
                    setDeactivateClass(btn);
                }
            }
        });
    });

    $('#confirmNoEvent').click(function() {
        $('#confirmation-activate').modal('hide');
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
