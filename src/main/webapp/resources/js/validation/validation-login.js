$(document).ready(function () {
    $('#loginForm').validate({
        rules: {
            j_username: {
                required: true,
            },
            j_password: {
                required: true,
            }
        }
    });

    /*var email = $.cookie('email');
    var password = $.cookie('password');
    var remember = $.cookie('remember');
    console.log('Remember - me checkbox : ' + remember);
    if (remember == 'true') {
        // autofill the fields
        $('#email').val(email);
        $('#password').val(password);
        $("#remember").prop("checked", true);
    }


    $("#loginForm").submit(function () {
        if ($('#remember').is(':checked')) {
            var email = $('#email').val();
            var password = $('#password').val();
            // set cookies to expire in 14 days
            $.cookie('email', email, {expires: 14});
            $.cookie('password', password, {expires: 14});
            $.cookie('remember', 'true', {expires: 14});
        }
        else {
            // reset cookies
            $.cookie('email', '');
            $.cookie('password', '');
            $.cookie('remember', false);
        }
    });*/

});
jQuery.extend(jQuery.validator.messages, {
    required: messages.adminValidation.required,
});
