'use strict';

$(function () {

  dayDiscountDataTable = buildDataTable('.dayDiscountDataTable', 'restful/admin/discounts/day', DayColumns, dayDiscountButtonFunctions);

  buildDataTable('.personalDiscountDataTable', 'restful/admin/discounts/personal', PersonalColumns, personalDiscountButtonFunctions);

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

  $("#changeDayPeriod").click(function () {
    fullDay = !fullDay;
    $("#dayDiscountTime").toggle();
  });

});

let request,onButtonAdd,editId,dayDiscountDataTable;
let fullDay = true;

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
  if(fullDay){
    inputData.startTime = "00:00";
    inputData.endTime = "23:59";
  }
  $.ajax({
    url: request,
    contentType: 'application/json; charset=UTF-8',
    data: JSON.stringify(inputData),
    type: method,
    success: function (result) {
      dayDiscountDataTable.ajax.reload(null,false);
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
      if(result.startTime == "00:00"&&result.endTime == "23:59"){
        fullDay = true;
        $("#dayDiscountTime").hide();
      }else{
        $("#dayDiscountTime").show();
        fullDay = false;
        $("#changeDayPeriod").prop('checked', false);
        $("#DStartTime").val(result.startTime);
        $("#DEndTime").val(result.endTime);
      }
    }
  });
}

//Add day discount button
function onAddDiscountClick(){
  $("#dayDiscountModalTitle").text(messages.modal.discount.addDiscount);
  fullDay = true;
  $("#dayDiscountTime").hide();
  onButtonAdd = true;
}

function onAddPersonalDiscount(){
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
      if(full.startTime == "00:00"&&full.endTime == "23:59"){
        return messages.booking.allDayDiscount;
      }else{
        return full.startTime + " - " + full.endTime;
      }
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
      if(full.startTime == "00:00"&&full.endTime == "23:59"){
        return messages.booking.allDayDiscount;
      }else{
        return full.startTime + " - " + full.endTime;
      }
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