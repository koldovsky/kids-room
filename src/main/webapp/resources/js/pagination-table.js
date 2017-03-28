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
        'searching': false,
        'sort': true,
        'columnDefs': [{
            'searchable': false,
            'orderable': false,
            'targets': 0
        }],
        'ajax': {
            'url': uri,
            'data': function (d) {
                let sendObj = {
                    pagination: {
                        start: d.start,
                        itemsPerPage: d.length
                    },
                    sortings: [],
                    searches: []
                };

                d.order.forEach(function (item) {
                    let column = $(selector + "-wrapper").find(".column-names").children()[item.column];
                    item.dir == 'asc' ? item.dir = 1 : item.dir = 0;
                    sendObj.sortings.push(
                        {
                            direction: item.dir,
                            column: $(column).text()
                        }
                    );
                });
                console.log(sendObj);
                return JSON.stringify(sendObj);
            },
            'contentType': 'application/json',
            'type': 'POST'
        },
        'columns': columnsArrObj
    });
}


function createObject(formSelector, uri) {
    $(formSelector).submit(function (event) {
        event.preventDefault();
        let dataSender = getObjectFromForm($(formSelector));
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
        let dataSender = getObjectFromForm(formSelector);
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