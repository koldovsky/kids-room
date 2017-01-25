function callErrorDialog(errorText) {

    $('#error-dialog').attr('title', 'We\'re sorry').text(errorText).dialog({
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

function eventsWereNotCreated(datesWhenNotCreated,recurrentId) {

    var contentForDialog = '<div>' + messages.event.errors.cannotCreateEventsForNonExistingDates +
        '</div><br>';
    datesWhenNotCreated.forEach(function (item) {
        contentForDialog += item + '<br>';
    });
    $('#warning-dialog').html(contentForDialog);
    $('#warning-dialog').attr('title', 'Warning').text(contentForDialog).dialog({
        buttons: {
            'Edit': function () {
                $(this).dialog('close');
                editRecurrentEventRequest(recurrentId);
            },
            'Ok': function () {
                $(this).dialog('close');
            }
        },
        closeOnEscape: true,
        draggable: false,
        resizable: false,
        modal: true,
        show: 500
    });

    $('#warning-dialog').html(contentForDialog);
}
