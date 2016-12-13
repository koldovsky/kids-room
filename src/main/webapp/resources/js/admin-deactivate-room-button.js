$('.for-table tbody').on('click', '#deactivateButton', function() {
    var dialog = $('#deactivateModal');
    var btn = this;
    dialog.modal('show');
    $('#deactivateYesButton').click(function () {
        $(btn).parents('form').submit();
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
