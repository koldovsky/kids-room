var BORDER_COLOR = '#000000';
var NOT_ACTIVE_EVENT = '#33cc33';

function myFunction(id) {
    console.log('sfgdkgf');
    $('#updating-dialog').dialog('open');
    adminManagerUpdate(id);

}

function adminManagerUpdate(userid) {
    var userDto = {
        id: userid,
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: ''
    };
    console.log(userid);
    $.ajax({
        url: 'getManager/' + userid,
        encoding: 'UTF-8',
        contentType: 'charset=UTF-8',
        success: function (userDto) {
            console.log(userDto);

            $('#manager-id').val(userid);
            $('#new-manager-emailUpdate').val(userDto.email);
            $('#new-manager-firstNameUpdate').val(userDto.firstName);
            $('#new-manager-lastNameUpdate').val(userDto.lastName);
            $('#new-manager-phoneNumberUpdate').val(userDto.phoneNumber);
        }
    });
}
$('#updatingButton').click(function () {
    clickedUpdate();
});

function clickedUpdate() {
    var mn = {
        id: $('#manager-id').val(),
        emailManager: $('#new-manager-emailUpdate').val(),
        firstNameManager: $('#new-manager-firstNameUpdate').val(),
        lastNameManager: $('#new-manager-lastNameUpdate').val(),
        phoneNumberManager: $('#new-manager-phoneNumberUpdate').val()
    };
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'getmanagerforupdate',
        dataType: 'json',
        data: JSON.stringify({
            id: mn.id,
            email: mn.emailManager,
            firstName: mn.firstNameManager,
            lastName: mn.lastNameManager,
            phoneNumber: mn.phoneNumberManager
        }),
        success: function (mav) {
            console.log('okr');
            var bl = document.getElementById("resultVindUpdate");
            if (mav.message == null) {

               bl.innerHTML = mav.email + " updated successfully";
                location.reload();
            }
            else {
                bl.innerHTML = mav.email + mav.message;
            }
        },
        complete: function () {
            $('.loading').hide();
        }
    });
}

$(function () {
    $('#update-recurrent-button').hide();

    $('#manager-dialog').dialog({
        modal: true,
        width: 550,
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 250
        },
        hide: {
            effect: 'drop',
            duration: 250
        }
    });

    $('#updating-dialog').dialog({
        autoOpen: false,
        modal: true,
        width: 400
    });

    $('#cancel-update').click(function () {
        $('#updating-dialog').dialog('close');
    });

    $('#create-button').click(function () {
        createSingleManager();
    });

    $('#cancel').click(function () {
        clearNewManagerDialog();
        $('#manager-dialog').dialog('close');
    });
    $('#cancel-update').click(function () {
        $('#updating-dialog').dialog('close');
    });

    $('#go').click(function () {
        $('#manager-dialog').dialog('open');
    });
});
function showDialogToAddManager(){
    $('#manager-dialog').dialog('open');
}
function clearNewManagerDialog() {
    $('#go').show();
    $('#new-manager-email').val('');
    $('#new-manager-firstName').val('');
    $('#new-manager-lastName').val('');
    $('#new-manager-phoneNumber').val('');
}

function createSingleManager(idIfEdited) {
    var mn = {
        id: -1,
        recurrentId: idIfEdited,
        emailManager: $('#new-manager-email').val(),
        firstNameManager: $('#new-manager-firstName').val(),
        lastNameManager: $('#new-manager-lastName').val(),
        phoneNumberManager: $('#new-manager-phoneNumber').val()
    };
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'admincreatenewmanager',
        dataType: 'json',
        data: JSON.stringify({
            email: mn.emailManager,
            firstName: mn.firstNameManager,
            lastName: mn.lastNameManager,
            phoneNumber: mn.phoneNumberManager
        }),
        success: function (mav) {
            console.log('okr');
            console.log(mav);
            if (mav.message == null) {
                var bl = document.getElementById("resultVind");
                bl.innerHTML = mav.email + " created successfully";
                location.reload();
            }
            else {
                var bl = document.getElementById("resultVind");
                bl.innerHTML = mav.email + " " + mav.message;
            }
        },
        complete: function () {
            $('.loading').hide();
        }
    });
}
function printServerError(errorField, responsetext) {
    $("." + errorField).html(responsetext);
}