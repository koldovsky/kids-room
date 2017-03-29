$(function () {
  refreshDaysDiscounts();
});

var request, today, time, onButtonAdd,editId;

function refreshDaysDiscounts() {
  request = "restful/admin/discounts/";
    $.ajax({
      url: request,
      type: 'GET',
      ajaxStart: dayDiscountsLoading(),
      success: function (result) {
        var users = result;

        var tr = "";

        $.each(users, function (i, discount) {
          tr += '<tr class="discountData"><td>' + discount.reason + '</td>'
              + '<td>' + discount.value + '</td>'
              + '<td>' + discount.startDate+' - '+ discount.endDate + '</td>'
              + '<td>' + discount.startTime+' - '+discount.endTime + '</td>'
              + '<td><button id="btn-edit"'
              +'class="btn btn-raised btn-info editDayDiscount" data-toggle="modal" data-target="#addDiscountDiv" '
              + 'discountid="'+discount.id+'">'
              + '<i class="glyphicon glyphicon-pencil"></i></button>'
              +'</td>'
              + '<td>'+discount.active+'</td><tr>';
        });
        //Hide loader and append new data to the table
        $("#firstLoader").hide();
        $("#dayDiscounts").append(tr).append(addButtonForDayDiscount());
        
        //Add event's to the elements
        $("#addDiscount").click(onAddDiscountClick);
        $(".editDayDiscount").click(onEditDiscountClick);

      }
    });
}

function dayDiscountsLoading(){
  $(".discountData").remove();
  $("#addDiscountTR").remove();
  $("#firstLoader").show();
}

function addButtonForDayDiscount() {
  return '<tr id="addDiscountTR"><th colspan="6" class="hide-border set-standard-color">'
      + '<button type="button" class="btn btn-raised btn-primary btn-add-room" '
      + 'id="addDiscount" data-toggle="modal" data-target="#addDiscountDiv">'
      + messages.preloader.buttons.add
      + '</button>'
      + '</th>'
      + '</tr>';
}

$("#discountForm").on('submit', function(e){
    //Change behavior of save button
    if(onButtonAdd){
      addNewDiscount('POST',false);
    }else{
      addNewDiscount('PUT',true);
    }
    $('#addDiscountDiv').modal('toggle');
    e.preventDefault();
})

//Create new discount
function addNewDiscount(method,bool){
  //Get start date and time
  var startDate = new Date($("#DStartDate").val()+":"+$("#DStartTime").val()+":00");
  //Get start date and time
  var endDate = new Date($("#DEndDate").val()+":"+$("#DEndTime").val()+":00");

  request = "restful/admin/discounts/";
  var inputData = {
    reason: $("#DReason").val(),
    value : $("#DValue").val(),
    startDate : "23-03-2017",
    endDate : "23-03-2017",
    startTime: "11:00",
    endTime: "23:00",
    active : true,
  };
  if(bool)inputData.id = editId;
  $.ajax({
    url: request,
    contentType: 'application/json; charset=UTF-8',
    data: JSON.stringify(inputData),
    type: method,
    success: function (result) {
      refreshDaysDiscounts();
    }
  });
}


function onAddDiscountClick(){
  //Default date and time sets to current
  today = moment().format("YYYY-MM-DD");
  time = moment().format("HH:mm");
  $("#dayDiscountModalTitle").text(messages.modal.discount.addDiscount);
  $("#DStartDate").val(today);
  $("#DEndDate").val(today);
  $("#DStartTime").val(time);
  $("#DEndTime").val(time);
  onButtonAdd = true;
}

function onEditDiscountClick() {
  $("#dayDiscountModalTitle").text(messages.modal.discount.editDiscount);
  editId = $(this).attr("discountid");
  request = "restful/admin/discounts/"+editId;
  onButtonAdd = false;
  $.ajax({
    url: request,
    type: 'GET',
    success: function (result) {
      $("#DReason").val(result.reason);
      $("#DValue").val(result.value);
      //Date and time formation
      $("#DStartDate").val(moment(result.startDate).format("YYYY-MM-DD"));
      $("#DEndDate").val(moment(result.endDate).format("YYYY-MM-DD"));
      $("#DStartTime").val(moment(result.startDate).format("HH:mm"));
      $("#DEndTime").val(moment(result.endDate).format("HH:mm"));
    }
  });

}

