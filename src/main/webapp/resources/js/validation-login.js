$(document).ready(function() {
        $('#loginForm').validate({
            rules:{
                j_username: {
                   required: true,
                },
                j_password:{
                   required: true,
                }
            }
        });
});