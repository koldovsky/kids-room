/*
 * Accepts:
 * selector – html element where datatable will be injected ex: ".list-objects"
 * uri – url path to your PostMapping controller for pagination ex: "adm-all-abonnements"
 */

// table.ajax.reload(); in case ajax requests
let iterator;

function getIndex() {
    console.log($(".input-sm"));
}
function buildDataTable(selector, uri, columnsArrObj, addFunctions) {
    let datatable = $(selector).DataTable({
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
        rowId: 'id',
        'ajax': {
            'url': uri,
            'data': function (datatableObject) {
                let sendObj = {};
                sendObj.pagination = {
                    start: datatableObject.start,
                    itemsPerPage: datatableObject.length
                };
                sendObj.sortings = [];
                let wrapper = $(selector + "-wrapper");
                if (wrapper) {
                    datatableObject.order.forEach(function (dtOrder) {
                        let indOder = 0;
                        if (dtOrder.column != 0) {
                            indOder = dtOrder.column - 1;
                        }
                        let column = $(wrapper).find(".column-names").children()[indOder];
                        dtOrder.dir = defineOrder(dtOrder.dir);
                        sendObj.sortings.push({
                                direction: dtOrder.dir,
                                column: $(column).text()
                            }
                        );
                    });
                }
                sendObj.searches = [];
                let datatableSearches = $(wrapper).find(".search-fields");
                if (datatableSearches) {
                    let datatableChildren = datatableSearches.children();
                    for (var i = 1; i < datatableChildren.length; i++) {
                        let searchVal = $(datatableChildren[i]).val();
                        if (searchVal.length) {
                            sendObj.searches.push({
                                value: searchVal,
                                column: $(datatableChildren[i]).attr('placeholder')
                            });
                        }
                    }
                }
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

function getId(object) {
    return $(object).closest('tr').attr("id");
}

function defineOrder(str) {
    return str == 'asc' ? 1 : 0;
}