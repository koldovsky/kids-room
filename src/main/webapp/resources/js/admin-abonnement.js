'use strict';

$(function () {
    let columns = [
        {
            'data': 'name',
            'fnCreatedCell': function (nTd, sData) {
                $(nTd).html(
                    "<div class='name'>" + sData + "</div>"
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

    const abonnemtsFunctions = function () {
        $('#createAbonnementForm').submit(function (event) {
            let path = 'adm-create-abonnement';
            event.preventDefault();
            let dataSender = getObjectFromForm($('#createAbonnementForm'));
            $.ajax({
                url: path,
                type: 'GET',
                contentType: 'application/json',
                datatype: 'json',
                data: JSON.stringify(dataSender),
                success: function () {
                    datatable.ajax.reload();
                },
                error: function (data) {
                    console.log(data);
                }
            });
        });
        $('#updateAbonnementForm').submit(function (event) {
            let path = 'adm-update-abonnement';
            event.preventDefault();
            let dataSender = getObjectFromForm('#updateAbonnementForm');

            $.ajax({
                url: path,
                type: 'PUT',
                contentType: 'application/json',
                datatype: 'json',
                data: JSON.stringify(dataSender),
                success: function () {
                    datatable.ajax.reload();
                },
                error: function () {
                }
            });
        });
        // initing update Modal
        $(document.body).on('click', '.btn-edit', function (event) {
            let node = null;
            if (event.target.nodeName == 'I') {
                node = $(event.target).parent().parent().parent().parent().parent();
            } else {
                node = $(event.target).parent().parent().parent().parent();
            }
            let objInit = {
                name: $(node).children().find('.name').text(),
                price: $(node).children().find('.price').text(),
                hour: $(node).children().find('.hour').text()
            };
            let abonnFormId = "#updateAbonnementForm ";
            for (let prop in objInit) {
                $(abonnFormId + "." + prop).val(objInit[prop]);
            }
        });
        function changeActiveState(id, checked) {
            let path = 'adm-active-abonnement';
            if (!checked) {
                checked = false;
            }
            let dataSender = {
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
    };

    let datatable = buildDataTable('.datatable', 'adm-pag-abonnements', columns, abonnemtsFunctions);

    // initing update Modal
    $(document.body).on('click', '.btn-edit', function (event) {
        let node = null;
        if (event.target.nodeName == 'I') {
            node = $(event.target).parent().parent().parent().parent().parent();
        } else {
            node = $(event.target).parent().parent().parent().parent();
        }
        let objInit = {
            name: $(node).children().find('.name').text(),
            price: $(node).children().find('.price').text(),
            hour: $(node).children().find('.hour').text()
        };
        let abonnFormId = "#updateAbonnementForm ";
        for (let prop in objInit) {
            $(abonnFormId + "." + prop).val(objInit[prop]);
        }
    });

});