'use strict';

$(function () {
    // refreshDataTable();
    var columns = [
        {
            'data': 'name',
            'fnCreatedCell': function (nTd, sData) {
                $(nTd).html(
                    "<span class='name'>" + sData + "</span>"
                );
            }
        },
        {
            'data': 'price',
            'fnCreatedCell': function (nTd, sData) {
                $(nTd).html(
                    "<span class='price'>" + sData + "</span>"
                );
            }
        },
        {
            'data': 'hour',
            'fnCreatedCell': function (nTd, sData) {
                $(nTd).html(
                    "<span class='hour'>" + sData + "</span>"
                );
            }
        },
        {
            'data': null,
            'fnCreatedCell': function (nTd) {
                $(nTd).html(
                    "<span><a tabindex='-1'>" +
                    "<button class='btn btn-raised btn-info btn-edit' " +
                    "data-toggle='modal' data-target='#updateAbonnement'>" +
                    "<i class='glyphicon glyphicon-pencil'></i></button></a></span>"
                );
            },
            'orderable': false
        },
        {
            'data': null,
            'fnCreatedCell': function (nTd) {
                $(nTd).html(
                    "<span class='assign'><button class='btn btn-success btn-responsive pull-center'>" +
                    "<span class='glyphicon glyphicon-user'>" +
                    "</button></span>"
                );
            },
            'orderable': false
        },
        {
            'data': null,
            'fnCreatedCell': function (nTd) {
                $(nTd).html(
                    "<span><label class='switch'>" +
                    "<input type='checkbox' checked class='activate'>" +
                    "<div class='slider round'></div>" +
                    "</label></span>"
                );
            },
            'orderable': false
        }
    ];

    // $(".datepickers").datepicker({
    //     dateFormat: constants.parameters.dateFormat,
    //     setDate: moment().format(constants.parameters.dateFormatUpperCase)
    // });

    buildDataTable('.datatable', 'adm-all-abonnements', columns);

    // initing update Modal
    $(document.body).on('click', '.btn-edit', function (event) {
        var node = null;
        if (event.target.nodeName == 'I') {
            node = $(event.target).parent().parent().parent().parent().parent();
        } else {
            node = $(event.target).parent().parent().parent().parent();
        }
        var objInit = {
            name: $(node).children().find('.name').text(),
            price: $(node).children().find('.price').text(),
            hour: $(node).children().find('.hour').text()
        };
        var abonnFormId = "#updateAbonnementForm ";
        for (var prop in objInit) {
            $(abonnFormId + "." + prop).val(objInit[prop]);
        }
    });

    createObject('#createAbonnementForm', 'adm-create-abonnement');

    updateObject('.update-object', '#updateAbonnementForm', 'adm-update-abonnement');

    function changeActiveState(id, checked) {
        var path = "adm-active-abonnement";
        if (!checked) {
            checked = false;
        }
        var dataSender = {
            id: id,
            active: checked
        };
        $.ajax({
            url: path,
            type: 'PUT',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function (data) {
            }
        })
    }
});