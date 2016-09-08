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
       }, "Ви ввели ім'я невірно");
       $.validator.addMethod("regexLastName", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Ви ввели прізвище невірно");
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
                   //regex: /^(\w){1,60}[@][s]oft[s]erveinc[.]com$/
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
                    required: "Введіть, будь ласка, своє ім'я",
                    regexFirstName: "Ви ввели ім'я невірно"
                },
                lastName: {
                    required: "Введіть, будь ласка, своє прізвище",
                    regexLastName: "Ви ввели прізвище невірно"
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