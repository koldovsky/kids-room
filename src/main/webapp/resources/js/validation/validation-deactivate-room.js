$(document).ready(function () {
    $.validator.addMethod("requiredWithEmptySpace", function (value) {
        return value.trim().length != 0;
    }, messages.room.errors.requiredWithEmptySpace);
    $('#reasonDeactivate').validate({
        rules:{
            reason:{
                minlength: 1,
                maxlength: 255,
                requiredWithEmptySpace: true
            }
        }
    })
});
