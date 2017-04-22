$(function () {
    $('#edit-kid-cancel').click(function () {
        var kidredirect='/home/mykids';
        $(location).attr('href', kidredirect).reload(true);
    });
    $('#confirmation-dialog-div').dialog({
        autoOpen: false,
        resizable: false,
        width: 350,
        modal: true
    });
    $('#removeKids').click(function () {
        var myDialog = $('#confirmation-dialog-div');
        myDialog.dialog('open');
        $('#confirmYes').click(function () {
            takingKidOff();
            myDialog.dialog('close');
        });
        $('#confirmNo').click(function () {
            myDialog.dialog('close');
        });
    });
    $('#removeKids').hover(function(){
        $(this).css('color','red');
        $(this).css('cursor','pointer ');
    }, function(){
        $(this).css('color', 'black');
    });
});
function takingKidOff() {
    // var kidId = kid.getId();
    var parameters = location.search.substring(1).split('&');
    var kidMap = parameters[0].split('=');
    var kidId = kidMap[1];
    var kidredirect='/home/mykids';
    $.post('remove-kid/' + kidId)
        .success(function () {
            $(location).attr('href', kidredirect).reload(true);
        })
        .error(function (xhr, status, error) {
            console.log('Error!' + error + ' status = ' + status);
        });
}

