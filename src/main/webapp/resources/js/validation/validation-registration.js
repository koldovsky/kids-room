$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regex", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Please enter email with softserveinc.com domain.");
        $.validator.addMethod("regexPhone", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Please enter valid phone number. Example +380991234567");
        $.validator.addMethod("regexFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "First Name is invalid.");
        $.validator.addMethod("regexLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Last Name is invalid");
        $('#userform').validate({
            rules:{

                firstName: {
                    required: true,
                    regexFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                lastName: {
                    required: true,
                    regexLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                email: {
                    required: true,
                    //regex: /^(\w){2,60}[@][s]oft[s]erveinc[.]com$/
                },
                password: {
                    required: true,
                    minlength: 8
                },
                confirm:{
                    required: true,
                    equalTo: "#userPassword"
                },
                phoneNumber:{
                    required: true,
                    regexPhone: /^\+(?:[0-9] ?){6,14}[0-9]$/
                }
            }, messages: {
                firstName: {
                    required: "This field cannot be empty. Please enter your name"
                },
                lastName: {
                    required: "This field cannot be empty. Please enter your last name"
                },
                email: {
                    required: "This field cannot be empty. Please enter your email"
                },
                phoneNumber:{
                    required: "This field cannot be empty. Please enter your phone number"
                },
                password:{
                    required: "This field cannot be empty. Please enter your password"
                },
                confirm:{
                    required: "This field cannot be empty. Please enter your password",
                    equalTo: "This password does not match the password in ‘Password’ field. Please enter the same password"
                }
            }
        });
    });
});