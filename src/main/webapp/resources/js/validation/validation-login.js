$(document).ready(function() {
        $('#loginForm').validate({
            rules:{
                username: {
                   required: true,
                },
                password:{
                   required: true,
                }
            }
        });
});

$(document).on(ready, function() {

    fillByMemory()
    $('button#Sign').on('click', function() {

        if ($('#remember').val()) {
            rememberMe();
        }
        doLogin();
    });
});

function rememberMe() {
    $.cookie('id', $('#username').val());
    $.cookie('pass', $('#password').val());
}

function fillByMemory() {
    if (!!$.cookie('id'))
        $('#username').val($.cookie('id'));

    if (!!$.cookie('pass'))
        $('#password').val($.cookie('pass'));
}