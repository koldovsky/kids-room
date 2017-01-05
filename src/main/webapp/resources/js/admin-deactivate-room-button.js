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
            complete:  function (xhr) {
               var response = xhr['responseText'];
               var dialog, yes_button;
               if(response == "success") {
                   dialog = $('#positiveResultModal');
                   yes_button  = $('#positiveYesButton');
               } else {
                   dialog = $('#negativeResultModal');
                   yes_button  = $('#negativeYesButton');
               }
               dialog.modal("show");
               yes_button.click(function () {
                   dialog.modal("hide");
                   window.location.reload();
               });
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
