'use strict';

$(function () {

  buildDataTable('.dayDiscountDataTable', 'restful/admin/discounts/getdays', DayColumns, dayDiscountButtonFunctions);

  buildDataTable('.personalDiscountDataTable', 'restful/admin/discounts/getpersonal', PersonalColumns, personalDiscountButtonFunctions);

  $("#addDiscount").click(onAddDiscountClick);

  $("#addPersonalDiscount").click(onAddPersonalDiscount);

  $("#selectUser").select2();

  $("#discountForm").on('submit', function(e){
    e.preventDefault();
    //Change behavior of save button
    if(onButtonAdd){
      addNewDiscount('POST',false);
    }else{
      addNewDiscount('PUT',true);
    }
    $('#addDiscountDiv').modal('toggle');
  });
});

let request,onButtonAdd,editId;

//Add functions for day discount datatable buttons
const dayDiscountButtonFunctions = function (){
  //Edit button
  $('.datatable tbody').on( 'click', '.editDayDiscount', function () {
    onEditDiscountClick($(this).attr("daydiscountid"));
  } );

};

const personalDiscountButtonFunctions = function (){

};

//Create new discount
function addNewDiscount(method,bool){
  request = "restful/admin/discounts/day";
  var inputData = {
    reason: $("#DReason").val(),
    value : $("#DValue").val(),
    startDate : $("#DStartDate").val(),
    endDate : $("#DEndDate").val(),
    startTime: $("#DStartTime").val(),
    endTime: $("#DEndTime").val(),
    active : true,
  };
  if(bool)inputData.id = editId;
  $.ajax({
    url: request,
    contentType: 'application/json; charset=UTF-8',
    data: JSON.stringify(inputData),
    type: method,
    success: function (result) {
      datatable.ajax.reload(null,false);
    }
  });
}

//Edit day discount  button
function onEditDiscountClick(id) {
  $("#dayDiscountModalTitle").text(messages.modal.discount.editDiscount);
  editId = id;
  request = "restful/admin/discounts/day/" + editId;
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
      $("#DStartTime").val(result.startTime);
      $("#DEndTime").val(result.endTime);
    }
  });
}

//Add day discount button
function onAddDiscountClick(){
  //Default date and time sets to current
  $("#dayDiscountModalTitle").text(messages.modal.discount.addDiscount);
  onButtonAdd = true;
}

function onAddPersonalDiscount(){
  //Default date and time sets to current
  $("#personalDiscountModalTitle").text(messages.modal.discount.addDiscount);
  onButtonAdd = true;
}

//Day discount columns creation
const DayColumns = [
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
    }
  },
  {
    'data': 'time',
    'render': function (data, type, full, meta) {
      return full.startTime + " - " + full.endTime;
    }
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
//Personal discount columns creation
const PersonalColumns = [
  {
    'data': 'username',
    'render': function (data, type, full, meta) {
      return full.user.firstName + " " + full.user.lastName;
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
    'data': 'time',
    'render': function (data, type, full, meta) {
      return full.startTime + " - " + full.endTime;
    }
  },
  {
    'data': 'id',
    'fnCreatedCell': function (nTd, sData) {
      $(nTd).html(
          "<span>" +
          "<button class='btn btn-raised btn-info editDayDiscount' " +
          "data-toggle='modal' data-target='#addPersonalDiscountDiv' personalDiscountId = "
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
          "<input type='checkbox' checked class='activate' personalDiscountId = "
          + sData +
          "><div class='slider round'></div>" +
          "</label></span>"
      );
    }
  }
];