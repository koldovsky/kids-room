$('.for-table tbody').on('click', '#deactivateButton', function() {
    var dialog = $('#deactivateModal');
    var btn = this;
    dialog.modal('show');
    $('#deactivateYesButton').click(function () {
        var roomNum  = $(btn).parents('form').serialize().substring(5);
        $.ajax({
            type: 'post',
            url: 'adm-edit-room?id=' + roomNum,
            dataType: 'json',
            sync: true,
            success: function() {
                alert("S ");
                window.location.reload();
                // var dialog = $('#positiveResultModa');
                // dialog.modal('show');
                // $('#negativeYesButton').click(function () {
                //     dialog.modal("hide");
                // });
            },
            error: function() {
                alert("E ");
                window.location.reload();
                // var dialog = $('#negativeResultModal');
                // dialog.modal("show");
                // $('#negativeYesButton').click(function () {
                //     dialog.modal("hide");
                // });
            }
        });
        dialog.modal("hide");

    });
    $('#deactivateNoButton').click(function () {
        dialog.modal("hide");
    });
});

$('.for-table tbody').on('click', '#activateButton', function() {
    var dialog = $('#activateModal');
    var btn = this;
    dialog.modal('show');
    $('#activateYesButton').click(function () {
        $(btn).parents('form').submit();
        dialog.modal("hide");
    });
});
