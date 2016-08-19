function callErrorDialog(errorText) {

    $('#error-dialog').attr('title', 'Error').text(errorText).dialog({
        buttons: {
            'Ok': function () {
                $(this).dialog('close');
            }
        }, dialogClass: 'error',
        closeOnEscape: true,
        draggable: false,
        resizable: false,
        modal: true,
        show: 500
    });
}
