/*
* Accepts:
* selector – html element where datatable will be injected ex: ".list-objects"
* uri – url path to your PostMapping controller for pagination ex: "adm-all-abonnements"
 */
var table = null;

function buildDataTable(selector, uri, columnsArrObj) {
    table = $(selector).DataTable({
        'processing': true,
        'bServerSide': true,
        'ajax': {
            'url': uri,
            'data': function(d) {
                return JSON.stringify(d);
            },
            'contentType': 'application/json',
            'type': 'POST'
        },
        'columns': columnsArrObj
    });
}