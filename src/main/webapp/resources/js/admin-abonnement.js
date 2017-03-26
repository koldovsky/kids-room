'use strict';

var table = null;

$(function () {
    refreshDataTable();
    console.log(hello());
    // function queryAbonnements() {
    //     var abonnements = null;
    //     var path = "adm-all-abonnements";
    //     $.ajax({
    //         url: path,
    //         contentType: 'application/json',
    //         datatype: 'json',
    //         success: function (data) {
    //             refreshDataTable(data);
    //         }
    //     });
    //     return abonnements;
    // }

    function refreshDataTable() {
        table = $(".list-objects").DataTable({
            'processing': true,
            'bServerSide': true,
            'ajax': {
                'url': 'adm-all-abonnements',
                'data': function(d) {
                    return JSON.stringify(d);
                },
                'contentType': 'application/json',
                'type': 'POST'
            },
            'columns': [
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
                    'data': 'id',
                    'fnCreatedCell': function (nTd) {
                        $(nTd).html(
                            "<span><a tabindex='-1'>" +
                            "<button class='btn btn-raised btn-info btn-edit' " +
                            "data-toggle='modal' data-target='#updateAbonnement'>" +
                            "<i class='glyphicon glyphicon-pencil'></i></button></a></span>"
                        );
                    }
                },
                {
                    'data': 'id',
                    'fnCreatedCell': function (nTd) {
                        $(nTd).html(
                            "<span class='assign'><button class='btn btn-success btn-responsive pull-center'>" +
                            "<span class='glyphicon glyphicon-user'>" +
                            "</button></span>"
                        );
                    }
                },
                {
                    'data': 'id',
                    'fnCreatedCell': function (nTd) {
                        $(nTd).html(
                            "<span><label class='switch'>" +
                            "<input type='checkbox' checked class='activate'>" +
                            "<div class='slider round'></div>" +
                            "</label></span>"
                        );
                    }
                }
            ]
        });
    }

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

    $(".update-object").click(function (event) {
        event.preventDefault();
        var path = "adm-update-abonnement";
        var dataSender = getObjectFromForm('#updateAbonnementForm');
        dataSender.id = 1;
        $.ajax({
            url: path,
            type: 'PUT',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function (data) {
                table.ajax.reload();
            },
            error: function () {
            }
        });
    });

    function getObjectFromForm(tag) {
        return $(tag).serializeArray().reduce(function (obj, item) {
            obj[item.name] = item.value;
            return obj;
        }, {});
    }
});