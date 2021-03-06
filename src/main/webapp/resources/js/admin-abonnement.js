'use strict';
let abonnementTable;
let purchasedAbonnementsTable;
let maxValue;
let minValue;

$(function () {
    getMaxPrice();

    abonnementTable = buildDataTable('.abonnement-datatable', 'adm-pag-abonnements',
        abonnementsColumns, abonnementsFunctions);
    purchasedAbonnementsTable = buildDataTable('.assigned-abonnement-datatable', 'adm-all-abonnement-assigment',
        purchasedAbonnements, function () {
            $(document.body).on('keyup', '.assigned-abonnement-datatable-wrapper .search-fields',
                $.debounce(400, function () {

                purchasedAbonnementsTable.ajax.reload(null, false);
            }));

            $(document.body).on('click', '#active-search-checkbox', function () {

                if ($('#active-search-checkbox').is(':checked')) {
                    $('#active-search-checkbox').attr('value', 0);
                } else {
                    $('#active-search-checkbox').attr('value', -1);
                }
                purchasedAbonnementsTable.ajax.reload(null, false);
            });
        });

    $('#DataTables_Table_1_wrapper').find('.row').eq(0).find('.col-md-6').eq(1)
        .html(`
            <div id="active-search" class="search-fields">
                <input type="checkbox" id="active-search-checkbox" name="active" placeholder="valid">
                <label>Only active</label>
            </div>`
        );
    $('#active-search-checkbox').attr('value', -1);
});

function validatePrice() {
    const priceRegex = /^(([1-9]{1}\d{0,3}|0) {0,1}- {0,1}([1-9]{1}\d{0,3}|0))$/g;
    if (priceRegex.test($("#abonnement-price").val())) {
        let priceRange = $("#abonnement-price").val();
        let price = priceRange.split(' ');
        if (price.length != 3) {
            price = priceRange.replace(' ', '').split('-');
            $("#abonnement-price").val(`${price[0]} - ${price[1]}`);
        }

        return true;
    } else {
        return false;
    }
}

let purchasedAbonnements = [
    {
        'data': 'user',
        'render': function (data, type, full) {
            return `<div class='name'>${full.user}</div>`;
        }
    },
    {
        'data': 'email',
        'orderable': false,
        'render': function (data, type, full) {
            return `<div class='name'>${full.email}</div>`;
        }
    },
    {
        'data': 'abonnement',
        'render': function (data, type, full) {
            return `<div class='name'>${full.abonnement}</div>`;
        }
    },
    {
        'data': 'hours',
        'orderable': false,
        'render': function (data, type, full) {
            return `<div class='hour'>${full.hours}</div>`;
        }
    },
    {
        'data': 'hourLeft',
        'render': function (data, type, full) {
            return `<div class='hour'>${full.leftTime}</div>`;
        }
    }
];

let abonnementsColumns = [
    {
        'data': 'name',
        'render': function (data, type, full) {
            return "<div class='name'>" + full.name + "</div>";
        }
    },
    {
        'data': 'price',
        'render': function (data, type, full) {
            return "<div class='price'>" + full.price + "</div>";
        }
    },
    {
        'data': 'hour',
        'render': function (data, type, full) {
            return "<div class='hour'>" + full.hour + "</div>";
        }
    },
    {
        'data': null,
        'orderable': false,
        'render': function () {
            return "<span><a tabindex='-1'>" +
                "<button class='btn btn-raised btn-info btn-edit' " +
                "data-toggle='modal' data-target='#updateAbonnement'>" +
                "<i class='glyphicon glyphicon-pencil'></i></button></a></span>";
        },
    },
    {
        'data': null,
        'render': function () {
            return "<span class='assign'>" +
                "<button class='btn btn-success btn-responsive pull-center btn-assign'" +
                "data-toggle='modal' data-target='#assignAbonnement'>" +
                "<span class='glyphicon glyphicon-user'>" +
                "</button></span>";
        },
        'orderable': false,
    },
    {
        'data': null,
        'render': function (data, type, full) {
            let addChecked;
            full.active ? addChecked = "checked" : addChecked = "";
            return "<span><label class='switch'>" +
                "<input type='checkbox' " + addChecked + " class='activate'>" +
                "<div class='slider round'></div>" +
                "</label></span>";
        },
        'orderable': false,
    }
];

function getMaxPrice () {
    $.ajax({
        type: 'GET',
        url: 'abonnement-max-price',
        success: function (max) {
            maxValue = max;
            getMinPrice();
        }
    })
}

function getMinPrice () {
    $.ajax({
        type: 'GET',
        url: 'abonnement-min-price',
        success: function (min) {
            minValue = min;

            $('#range-slider').slider({
                range: true,
                min: minValue,
                max: maxValue,
                values: [minValue, maxValue],
                slide: function(event, ui) {
                    $("#abonnement-price").val(`${ui.values[0]} - ${ui.values[1]}`);
                },
                stop: function (event, ui) {
                    abonnementTable.ajax.reload(null, false);
                }
            });

            $("#abonnement-price").val(`${$("#range-slider").slider("values", 0)} - ${$("#range-slider").slider("values", 1)}`);
        }
    })
}

const abonnementsFunctions = function () {

    $('#createAbonnementForm').submit(function (event) {
        let path = 'adm-abonnement';
        event.preventDefault();
        let dataSender = {
            id: $(".createId").val(),
            name: $(".createName").val(),
            price: $(".createPrice").val(),
            hour: $(".createHour").val()
        };
        $.ajax({
            url: path,
            type: 'POST',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function () {
                abonnementTable.ajax.reload(null, false);
            },
            error: function (data) {
            }
        });
    });

    $('#updateAbonnementForm').submit(function (event) {
        let path = 'adm-abonnement';
        event.preventDefault();
        let dataSender = {
            id: $(".updateId").val(),
            name: $(".updateName").val(),
            price: $(".updatePrice").val(),
            hour: $(".updateHour").val()
        };
        $.ajax({
            url: path,
            type: 'PUT',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function () {
                abonnementTable.ajax.reload(null, false);
            },
            error: function () {
            }
        });
    });

    $('#assignAbonnementForm').submit(function (event) {
        let path = 'adm-assign-abonnement';
        event.preventDefault();
        var dataSender = {
            userId: $('#selectUser').val(),
            abonnementId: $(".abonnementId").val()
        };
        $.ajax({
            url: path,
            type: 'POST',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function () {
                purchasedAbonnementsTable.ajax.reload(null, false);
            },
            error: function () {}
        });
    });

    // initing update Modal
    $(document.body).on('click', '.btn-edit', function () {
        let idAbonnement = getId(this);
        let path = 'adm-abonnement/' + idAbonnement;
        $.ajax({
            url: path,
            success: function (data) {
                $(".updateId").val(data.id);
                $(".updateName").val(data.name);
                $(".updatePrice").val(data.price);
                $(".updateHour").val(data.hour);
            },
            error: function () {
            }
        });
    });

    $(document.body).on('click', '.btn-assign', function() {
        $(".abonnementId").val(getId(this));
        selectAllUsers();
    });

    // update Abonnement active state
    $(document.body).on('change', '.activate', function () {
        let idAbonnement = getId(this);
        let path = 'adm-active-abonnement';
        let dataSender = {
            id: idAbonnement,
            active: this.checked
        };
        $.ajax({
            url: path,
            type: 'PUT',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function (data) {
            }
        });
    });

    // bind filter
    $(document.body).on('keyup', '.abonnement-datatable-wrapper .search-fields',
        $.debounce(400, function () {
        if (validatePrice()) {
            abonnementTable.ajax.reload(null, false);
        }
    }));

    $("#selectUser").select2();

    let list = 0;
    let userList;
    let selectAllUsers = function () {
        $.ajax({
            url: "restful/admin/discounts/personal/users",
            type: 'GET',
            success: function (result) {
                if (list != result.length) {
                    $('#selectUser').empty();
                    list = result.length;
                    userList = result;
                    $.each(userList, function (i, user) {
                        $('#selectUser').append($('<option>', {
                            value: user.id,
                            text: user.firstName + ' ' + user.lastName
                        }));
                    })
                    $('#selectUser').select2('val', ' ');
                } else {
                    $('#selectUser').select2('val', ' ');
                }
            }
        });
    }
};
