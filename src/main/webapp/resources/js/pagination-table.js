/*
 * Accepts:
 * selector – html element where datatable will be injected ex: ".list-objects"
 * uri – url path to your PostMapping controller for pagination ex: "adm-all-abonnements"
 */

// table.ajax.reload(); in case ajax requests

function buildDataTable(selector, uri, columnsArrObj,addFunctions) {
    let datatable =  $(selector).DataTable({
        language: {
            processing: messages.dateTable.processing,
            search: messages.dateTable.search,
            lengthMenu: messages.dateTable.lengthMenu,
            info: messages.dateTable.info,
            infoEmpty: messages.dateTable.infoEmpty,
            infoFiltered: messages.dateTable.infoFiltered,
            loadingRecords: messages.dateTable.loadingRecords,
            zeroRecords: messages.dateTable.zeroRecords,
            emptyTable: messages.dateTable.emptyTable,
            paginate: {
                first: messages.dateTable.paginate.first,
                previous: messages.dateTable.paginate.previous,
                next: messages.dateTable.paginate.next,
                last: messages.dateTable.paginate.last
            },
            aria: {
                sortAscending: messages.dateTable.aria.sortAscending,
                sortDescending: messages.dateTable.aria.sortDescending
            }
        },
        'processing': true,
        'bServerSide': true,
        'ordering': true,
        'searching': false,
        'columnDefs': [{
            'searchable': false,
            'orderable': false,
            'targets': 0
        }],
        rowId: 'id',
        'ajax': {
            'url': uri,
            'data': function (datatableObject) {
                let sendObj = {
                    pagination: {
                        start: datatableObject.start,
                        itemsPerPage: datatableObject.length
                    }
                };
                sendObj.sortings = [];
                sendObj.searches = [];
                datatableObject.order.forEach(function (dtOrder) {
                    let column = $(selector + "-wrapper").find(".column-names").children()[dtOrder.column];
                    dtOrder.dir = defineOrder(dtOrder.dir);
                    sendObj.sortings.push({
                            direction: dtOrder.dir,
                            column: $(column).text()
                        }
                    );
                });
                // sendObj.searches[0] = {
                //     value: "t",
                //     column: "name"
                // };
                return "parameters=" + encodeURIComponent(JSON.stringify(sendObj));
            },
            'contentType': 'application/json',
            'type': 'GET'
        },
        'columns': columnsArrObj
    });

    addFunctions();
    return datatable;
}

function defineOrder(str) {
    return str == 'asc' ? 1 : 0;
}

function getObjectFromForm(tag) {
    return $(tag).serializeArray().reduce(function (obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}