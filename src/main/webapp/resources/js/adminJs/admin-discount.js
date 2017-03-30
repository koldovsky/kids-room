'use strict';

$(function () {
  // refreshDataTable();
  const columns = [
    {
      'data': 'reason',
      'fnCreatedCell': function (nTd, sData) {
        $(nTd).html(
            "<span class='reason'>" + sData + "</span>"
        );
      }
    },
    {
      'data': 'value',
      'fnCreatedCell': function (nTd, sData) {
        $(nTd).html(
            "<span class='value'>" + sData + "%</span>"
        );
      }
    },
    {
      'data': 'date',
      'render': function (data, type, full, meta) {
        return full.startDate + " - " + full.endDate;
      },
    },
    {
      'data': 'time',
      'render': function (data, type, full, meta) {
        return full.startTime + " - " + full.endTime;
      },
    },
    {
      'data': 'id',
      'fnCreatedCell': function (nTd, sData) {
        $(nTd).html(
            "<span>" +
            "<button class='btn btn-raised btn-info editDayDiscount' " +
            "data-toggle='modal' data-target='#addDiscountDiv' dayDiscountId = "
            + sData +
            "><i class='glyphicon glyphicon-pencil'></i></button></span>"
        );
      }
    },
    {
      'data': 'id',
      'fnCreatedCell': function (nTd, sData) {
        $(nTd).html(
            "<span><label class='switch'>" +
            "<input type='checkbox' checked class='activate' dayDiscountId = "
            + sData +
            "><div class='slider round'></div>" +
            "</label></span>"
        );
      }
    }
  ];

  buildDataTable('.datatable', 'restful/admin/discounts/getdays', columns, dayDiscountButtonFunctions);

});

//Add functions for day discount datatable buttons
const dayDiscountButtonFunctions = function (){
  //Edit button
  $('.datatable tbody').on( 'click', '.editDayDiscount', function () {
    onEditDiscountClick($(this).attr("daydiscountid"));
  } );

}

function onEditDiscountClick(id) {
  $("#dayDiscountModalTitle").text(messages.modal.discount.editDiscount);
  //editId = $(this).attr("discountid");
  request = "restful/admin/discounts/day/" + id;
  onButtonAdd = false;
  $.ajax({
    url: request,
    type: 'GET',
    success: function (result) {
      $("#DReason").val(result.reason);
      $("#DValue").val(result.value);
      //Date and time formation
      $("#DStartDate").val(result.startDate);
      $("#DEndDate").val(result.endDate);
      //$("#DStartTime").val(moment(result.startDate));
      //$("#DEndTime").val(moment(result.endDate));
    }
  });
}