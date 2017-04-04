'use strict';
let table = null;
let managers = {};
$(function () {

    $("#roomForm").focusout(function () {
        if ($("#roomForm").valid())
            $('#btnSubmitNewRoom').prop('disabled', false);
    });

    $('#room-dialog').dialog({
        modal: true,
        width: 550,
        resizable: false,
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 250
        },
        hide: {
            effect: 'drop',
            duration: 250
        }
    });

    getAllRooms();

    $.ajax({
        type: 'GET',
        encoding: 'UTF-8',
        url: 'restful/admin/rooms/list-manager',
        success: function (result) {
            let obj = jQuery.parseJSON(JSON.stringify(result));
            $.each(obj, function (key, value) {
                managers[value.id] = value.firstName + ' ' + value.lastName;
                $('#man').append("<option value='" + value.id + "'>" + value.firstName + ' ' + value.lastName + "</option>");
            });
        }
    });


    $('#addManager').click(function () {
        var listManager = "<li><select name='manager[]' form='roomForm' class='form-control manager'>";
        $.each(managers, function (i, value) {
            listManager = listManager + "<option value='" + i + "'>" + value + "</option>";
        });
        listManager = listManager + "</select></li";
        $('#listManager').append(listManager);

    });

    $('#removeRate').click(function () {
        if ($('#tableRates tr').length > 1)
            $('#tableRates tr:last').remove();
    });

    $('#removeManager').click(function () {
        $('#listManager li:last').remove();
    });

    $('#showAddRoom').click(function () {
        clearForm();
        $('#btnSubmitUpdateRoom').hide();
        $('#btnSubmitNewRoom').show();
        $('#room-dialog').dialog('option', 'title', messages.room.info.newRoom);
        $('#room-dialog').dialog('open');
    });

    $('#close-add-dialod').click(function () {
        clearForm();
        $('#room-dialog').dialog('close');
    });

    $('#addRate').click(function () {
        $('#tableRates').append('<tr><td><input type="number" name="hour" id="hour" class="form-control"></td><td><input type="number" name="rate" id="rate" class="form-control"></td></tr>');
    });

    $('#btnSubmitNewRoom').click(function () {
        let isValidate = $("#roomForm").valid();
        $(".danger-info").remove();
        if (isValidate) {
            let dataSender = getObjectFromForm($('#roomForm'));

            $.ajax({
                type: 'POST',
                encoding: 'UTF-8',
                contentType: 'application/json; charset=UTF-8',
                url: 'restful/admin/rooms/',
                data: JSON.stringify(dataSender),
                success: function (result) {
                    result = JSON.parse(result);
                    if (result.length == 0) {
                        $('#room-dialog').dialog('close');
                        getAllRooms();
                    }
                    else result.forEach(function (item, i, arr) {
                        $('#roomForm').append("<div class='danger-info'>" + item + "</div>");
                    });
                }
            });
        }
    });

    $('#btnSubmitUpdateRoom').click(function () {
        let isValidate = $("#roomForm").valid();
        $(".danger-info").remove();
        if (isValidate) {
            var dataSender = getObjectFromForm($('#roomForm'));
            $.ajax({
                type: 'PUT',
                encoding: 'UTF-8',
                contentType: 'application/json; charset=UTF-8',
                url: 'restful/admin/rooms/',
                data: JSON.stringify(dataSender),
                success: function (result) {
                    result = JSON.parse(result);
                    if (result.length == 0) {
                        $('#room-dialog').dialog('close');
                        getAllRooms();
                    }
                    else result.forEach(function (item, i, arr) {
                        $('#roomForm').append("<div class='danger-info'>" + item + "</div>");
                    });
                }
            });
        }
    });
    function getObjectFromForm(tag) {
        let managers = "";
        let rates = [];
        $('#listManager').find(':input').each(function (index, element) {
            managers = $(this).val() + "," + managers;
        });

        $('#tableRates').find('tr').each(function (index, element) {
            var hour = $(this).find("td:eq(0) :input").val();
            var rate = $(this).find("td:eq(1) :input").val();

            if (hour !== undefined && rate !== undefined) {
                var info = {};
                info['hourRate'] = hour;
                info['priceRate'] = rate;
                rates.push(info);
            }

        });

        var ret = $(tag).serializeArray().reduce(function (obj, item) {
            if (item.name != "manager[]" && item.name != "rate" && item.name != "hour")
                obj[item.name] = item.value;
            return obj;
        }, {});
        ret['managers'] = managers.substring(0, managers.length - 1);
        ret['rate'] = JSON.stringify(rates);
        return ret;
    }
});
function showDialogToUpdateRoom(id) {
    $('#btnSubmitUpdateRoom').show();
    $('#btnSubmitNewRoom').hide();
    $.ajax({
        type: 'GET',
        encoding: 'UTF-8',
        contentType: 'application/json; charset=UTF-8',
        url: 'restful/admin/rooms/' + id,
        success: function (result) {
            $('#tableRates tr').remove();

            $('#listManager').empty();
            $('#btnSubmitNewRoom').removeClass('insert').addClass('update');

            $('#room-dialog #id').val(result.id);
            $('#room-dialog #active').val(result.active);
            $('#room-dialog #address').val(result.address);
            $('#room-dialog #capacity').val(result.capacity);
            $('#room-dialog #city').val(result.city);
            $('#room-dialog #name').val(result.name);
            $('#room-dialog #phoneNumber').val(result.phoneNumber);
            $('#room-dialog #workingHoursStart').val(result.workingHoursStart);
            $('#room-dialog #workingHoursEnd').val(result.workingHoursEnd);

            let inputManagers = jQuery.parseJSON(result.managers);
            $.each(inputManagers, function (i, element) {
                let listManager = "<li><select name='manager[]' form='roomForm' class='form-control manager'>";
                $.each(managers, function (j, value) {
                    if (element.id == j)
                        listManager = listManager + "<option selected value='" + j + "'>" + value + "</option>";
                    else listManager = listManager + "<option  value='" + j + "'>" + value + "</option>";
                });
                listManager = listManager + "</select></li";
                $('#listManager').append(listManager);

            });

            let inputRates = jQuery.parseJSON(result.rate);

            if (inputRates.length > 0) {
                $.each(inputRates, function (i, element) {
                    $('#tableRates').append('<tr><td><input type="number" name="hour" id="hour" class="form-control" value="' + element.hourRate + '"></td>' +
                        '<td><input type="number" name="rate" id="rate" class="form-control" value="' + element.priceRate + '"></td></tr>');
                });

            } else {
                $('#tableRates').append('<tr><td><input type="number" name="hour" id="hour" class="form-control"></td><td><input type="number" name="rate" id="rate" class="form-control"></td></tr>');
            }


            $('#room-dialog').dialog('option', 'title', messages.room.info.updateRoom);
            $('#room-dialog').dialog('open');
        }
    });
}
function deleteManager(btn) {
    $(btn).closest('tr').remove();
}
function clearForm() {
    $("#roomForm").validate().resetForm();
    $('.danger-info').remove();
    $("#roomForm").trigger("reset");
    $("#listManager").empty();
    $("#tableRates tr").remove();
    $('#tableRates').append('<tr><td><input type="number" name="hour" id="hour" class="form-control"></td><td><input type="number" name="rate" id="rate" class="form-control"></td></tr>');
}
function getAllRooms() {
    $.ajax({
        url: 'restful/admin/rooms/',
        encoding: 'UTF-8',
        contentType: 'application/json',
        datatype: 'json',
        success: function (data) {
            refreshTable(data);
        }
    });

}

function refreshTable(data) {
    if (table != null)
        table.destroy();

    table = $('#rooms-table').DataTable({
        processing: true,
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
        data: data,
        rowId: 'id',
        columnDefs: [{
            'searchable': false,
            'orderable': false,
            'targets': 0
        }],
        columns: [
            {
                'data': 'name'
            },
            {
                'data': 'city'
            },
            {
                'data': 'address'
            },
            {
                'data': 'phoneNumber'
            },
            {
                'data': 'capacity'
            },
            {
                'data': 'workingHoursStart',
                'fnCreatedCell': function (nTd, sData, oData) {
                    $(nTd).html(sData + '<br>' + oData['workingHoursEnd']);
                }
            },
            {
                'data': 'managers',
                render: function (data, type, row) {
                    var td = '';
                    var obj = jQuery.parseJSON(data);
                    $.each(obj, function (key, value) {
                        if (value !== undefined || value != null) {
                            td = td + '<br>' + value.firstName + ' ' + value.lastName;
                        }
                    });
                    return td;
                }
            },
            {
                'data': 'rate',
                render: function (data, type, row) {
                    var td = '<table id="miniRate">';
                    var obj = jQuery.parseJSON(data);
                    $.each(obj, function (key, value) {
                        if (value !== undefined || value != null)
                            td = td + '<tr><td>' + value.hourRate + '</td><td>' + value.priceRate + '</td></tr>';
                    });
                    td = td + '</table>';
                    return td;
                }
            },
            {
                'data': 'id',
                render: function (data, type, row) {
                    var td = '<a href="#" tabindex="-1"> <button id="admRoomEdit" type="button" class="btn btn-raised btn-info"';
                    td = td + 'onclick="showDialogToUpdateRoom(' + data + ')"><i class="glyphicon glyphicon-pencil"></i></button></a>';
                    return td;
                }
            },
            {
                'data': 'active',
                render: function (data, type, row) {
                    var td;

                    if (data)
                        td = '<label class="switch"><input type="checkbox" checked class="activate"><div class="slider round"></div></label>';
                    else td = '<label class="switch"><input type="checkbox" class="activate"><div class="slider round"></div></label>';

                    return td;
                }
            }
        ],
        'pagingType': 'simple_numbers',
        "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            var sDirectionClass;
            if (aData.active)
                sDirectionClass = "room";
            else
                sDirectionClass = "tr-not-active";

            $(nRow).addClass(sDirectionClass);
            return nRow;
        }
    });


}
