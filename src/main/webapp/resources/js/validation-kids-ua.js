

/**
 * Created by shuk on 13.11.16.
 */
$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        },"Ви ввели ім'я невірно");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Ви ввели прізвище невірно");
        $('#kidregistrform').validate({
            rules: {
                firstName: {
                    required: true,
                    regexKidFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    maxlength: 35

                },
                lastName: {
                    required: true,
                    regexKidLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    maxlength: 35

                }
            },
            messages: {
                firstName: {
                    required: "Вкажіть будь ласка ім'я дитини",
                    regexKidFirstName: "Для вводу імені використовуйте лише літери"

                },
                lastName: {
                    required: "Вкажіть будь ласка прізвище дитини",
                    regexKidLastName: "Для вводу прізвища використовуйте лише літери"

                }

            }
        });

    });
});
