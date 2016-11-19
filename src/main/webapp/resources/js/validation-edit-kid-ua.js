$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Ваше ім'я має складатись як мінімум із двох символів");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Ваше прізвище має складатись як мінімум із двох символів");
        $('#editkidform').validate({
            rules: {

                firstName: {
                    required: true,
                    regexKidFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    minlength: 2
                },
                lastName: {
                    required: true,
                    regexKidLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    minlength: 2
                }
            },
            messages: {
                firstName: {
                    required: "Введіть, будь ласка, своє ім'я",
                    regexFirstName: "Ви ввели ім'я невірно"
                },
                lastName: {
                    required: "Введіть, будь ласка, своє прізвище",
                    regexFirstName: "Ви ввели ім'я невірно"
                },
            }
        });
    });

});
