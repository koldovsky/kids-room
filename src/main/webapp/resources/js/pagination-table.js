/*
 * Accepts:
 * selector – html element where datatable will be injected ex: ".list-objects"
 * uri – url path to your PostMapping controller for pagination ex: "adm-all-abonnements"
 */

// table.ajax.reload(); in case ajax requests

var datatable = null;

function buildDataTable(selector, uri, columnsArrObj) {
    datatable = $(selector).DataTable({
        'processing': true,
        'bServerSide': true,
        'ajax': {
            'url': uri,
            'data': function (d) {
                console.log(d);
                var sendObj = {
                    pagination: {
                        currentPage: d.draw,
                        itemsPerPage: d.length
                    }
                    // sortings: {
                    //
                    // },
                    // searches: {
                    //     {
                    //
                    //     }
                    // }
                };
                return JSON.stringify(sendObj);
            },
            'contentType': 'application/json',
            'type': 'POST'
        },
        'columns': columnsArrObj
    });
}

function createObject(selector, formSelector, uri) {
    $(formSelector).submit(function (event) {
        event.preventDefault();
        var dataSender = getObjectFromForm($(formSelector));
        $.ajax({
            url: uri,
            type: 'POST',
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
}

function updateObject(selector, formSelector, uri) {
    $(selector).click(function (event) {
        event.preventDefault();
        var dataSender = getObjectFromForm(formSelector);
        /*dataSender.id = 1;*/
        $.ajax({
            url: uri,
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
}

function getObjectFromForm(tag) {
    return $(tag).serializeArray().reduce(function (obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}