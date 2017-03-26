'use strict';

$(function () {
    getAllAbonnements();

    function getAllAbonnements() {
        var path = "adm-all-abonnements";
        $.ajax({
            url: path,
            contentType: 'application/json',
            datatype: 'json',
            success: function (data) {
                data.forEach(function (item) {
                    appendAbonnementsList(item);
                })
            },
            error: function () {
            }
        });
    }

    // Send update form
    $(".submitter").click(function (event) {
        event.preventDefault();
        var path = "adm-update-abonnement";
        var dataSender = getObjectFromForm('#updateAbonnementForm');
        $.ajax({
            url: path,
            type: 'PUT',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function (data) {
                var target;
                $(".listId").each(function () {
                    // update actual Abonnements list
                    if ($(this).text() == data.id) {
                        target = $(this);
                        target.siblings(".name").text(data.name);
                        target.siblings(".price").text(data.price);
                        target.siblings(".hour").text(data.hour);
                    }
                });
            },
            error: function () {
            }
        });
    });

    // $(".datepickers").datepicker({
    //     dateFormat: constants.parameters.dateFormat,
    //     setDate: moment().format(constants.parameters.dateFormatUpperCase)
    // });

    // getting object from Abonnements list
    $(document.body).on('click', '.btn-edit', function() {
            var node;
            if (event.target.nodeName == 'I') {
                node = $(event.target).parent().parent().parent().parent();
            } else {
                node = $(event.target).parent().parent().parent();
            }
            var getChild = function (node, str) {
                return $(node).children(str).text();
            };
            var objInit = {
                id: getChild(node, ".id"),
                name: getChild(node, ".name"),
                price: getChild(node, ".price"),
                hour: getChild(node, ".hour")
            };
            initUpdateModal(objInit);
    });

    function initUpdateModal(obj) {
        var abonnFormId = "#updateAbonnementForm ";
        var initModal = function (name, setVal) {
            $(abonnFormId + name).val(setVal);
        };
        for (var prop in obj) {
            initModal("." + prop, obj[prop]);
        }
        $(abonnFormId + ".id").text(obj.id);
    }

    // Create dialog
    $("#createAbonnementForm").submit(function (event) {
        event.preventDefault();
        var path = "adm-create-abonnement";
        var dataSender = getObjectFromForm($('#createAbonnementForm'));
        $.ajax({
            url: path,
            type: 'POST',
            contentType: 'application/json',
            datatype: 'json',
            data: JSON.stringify(dataSender),
            success: function (data) {
                data.id = parseInt($(".listId").last().text()) + 1;
                appendAbonnementsList(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    function appendAbonnementsList(abonnement) {
        var before = $(".create-object");
        var tr = document.createElement("tr");
        var editButton = "<td><a tabindex='-1'>" +
            "<button class='btn btn-raised btn-info btn-edit' " +
            "data-toggle='modal' data-target='#updateAbonnement'>" +
            "<i class='glyphicon glyphicon-pencil'></i></button></a></td>";
        var toggleButton = "<td><label class='switch'>" +
            "<input type='checkbox' checked class='activate'>" +
            "<div class='slider round'></div>" +
            "</label></td>";
        var elementsToAppend = [
            "<td class='id listId' hidden>" + abonnement.id + "</td>",
            "<td class='name'>" + abonnement.name + "</td>",
            "<td class='price'>" + abonnement.price + "</td>",
            "<td class='hour'>" + abonnement.hour + "</td>",
            editButton,
            "<td class='assign'><button class='btn btn-success btn-responsive pull-center'>" +
            "<span class='glyphicon glyphicon-user'>" +
            "</button></td>",
            toggleButton
        ];
        elementsToAppend.forEach(function (item) {
            $(tr).append(item);
        });
        $(tr).insertBefore(before);
    }

    function getObjectFromForm(tag) {
        return $(tag).serializeArray().reduce(function (obj, item) {
            obj[item.name] = item.value;
            return obj;
        }, {});
    }

    // Open toggle dialog
    var btn;
    var abonnementId;
    var dialog;

    $(document.body).on('change', '.activate', function() {
        btn = this;
        abonnementId = getAbonnementId();
        if (btn.checked) {
            $(btn).parents('tr').removeClass('tr-not-active').addClass('room');
        } else {
            $(btn).parents('tr').addClass('tr-not-active').addClass('room');
        }
        changeActiveState(abonnementId, btn.checked);
        // dialog.modal('show');
    });

    function getAbonnementId() {
        return $(btn).parents().parents().parents("tr").children(".listId").text();
    }

    // $(".modal-fader").click(function () {
    //     var btn = this;
    //     var input = $(this).children("label").children("input");
    //     var checked = input[0].checked;
    //     changeActiveStateDOM(input, btn);
    //     if (checked) {
    //         $("#active-manager-span").hide();
    //         $("#inactive-manager-span").show();
    //     } else {
    //         $("#active-manager-span").show();
    //         $("#inactive-manager-span").hide();
    //     }
    //     var id = $(this).parent().parent().children(".listId").text();
    //
    //     // send Ajax update active state
    //     $("#confirmYesEvent").click(function() {
    //         changeActiveState(input, id);
    //     });
    //
    //     $("#confirmNoEvent").click(function() {
    //         changeActiveStateDOM(input, btn);
    //     });
    // });

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
