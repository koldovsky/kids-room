$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regEmail", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Email is invalid.");
       $.validator.addMethod("regexFirstName", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "First Name is invalid.");
       $.validator.addMethod("regexLastName", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Last Name is invalid.");
       $.validator.addMethod("regexPhone", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Phone number is invalid. Example +380991234567");
    $('#managerForm').validate({
            rules:{
                email: {
                   required: true,
                   regEmail: /^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@(([0-9]{1,3})|([a-zA-Z]{2,11})|(aero|coop|info|museum|name))+(\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name)))*\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))*$/
                },
                firstName: {
                   required: true,
                   regexFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                lastName: {
                   required: true,
                   regexLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                phoneNumber:{
                   required: true,
                   regexPhone: /^\+(?:[0-9] ?){6,14}[0-9]$/
                },
            },
        });
    });
});