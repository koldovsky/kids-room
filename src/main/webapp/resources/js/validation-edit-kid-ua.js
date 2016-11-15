/**
 * Created by Вова on 14.11.2016.
 */
$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Все плохо");
        $.validator.addMethod("regexLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Все плохо");
        $('#editkidform').validate({
            rules:{

                firstName: {
                    required: true,
                    minlength: 2,
                    regexFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                lastName: {
                    required: true,
                    minlength: 2,
                    regexLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                }

            }, messages: {
                firstName: {
                    required: "Будь ласка введіть щонайменше 2 символи"

                },
                lastName: {
                    required: "Будь ласка введіть щонайменше 2 символи"
                }
            }
        });
    });
});