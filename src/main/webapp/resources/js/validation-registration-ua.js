$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regex", function(value, element, regexpr) {
         return regexpr.test(value);
       }, "Please enter email with softserveinc.com domain.");
       $.validator.addMethod("regexPhone", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Please enter valid phone number. Example 0981234567");
    $('#userform').validate({
            rules:{

                firstName: {
                   required: true
                },
                lastName: {
                   required: true
                },
                email: {
                   required: true,
                   regex: /^(\w){1,60}[@][s]oft[s]erveinc[.]com$/
                },
                password: {
                   required: true,
                   minlength: 8
                },
                confirm:{
                   required: true,
                   minlength: 8,
                   equalTo: "#userPassword"
                },
                phoneNumber:{
                   required: true,
                   regexPhone: /[0-9]{10,14}/
                }
            }, messages: {
                firstName: {
                    required: "Введіть, будь ласка, своє ім'я"
                },
                lastName: {
                    required: "Введіть, будь ласка, своє прізвище"
                },
                email: {
                    required: "Введіть, будь ласка, свій емейл",
                    regex: "Введіть емейл з softserveinc.com домейном"
                },
                phoneNumber:{
                    required: "Введіть, будь ласка, свій номер телефону",
                    regexPhone: "Ви ввели невірний номер. Приклад: 0981234567"
                },
                password:{
                    required: "Введіть, будь ласка, пароль"
                },
                confirm:{
                    required: "Введіть, будь ласка, пароль",
                    equalTo: "Паролі не співпадають"
                }
            }

        });
});


});