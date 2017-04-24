'use strict';

$(function () {

  dayDiscountDataTable = buildDataTable('.dayDiscountDataTable',
      'restful/admin/discounts/day', DayColumns, dayDiscountButtonFunctions);

  personalDiscountDataTable = buildDataTable('.personalDiscountDataTable',
      'restful/admin/discounts/personal', PersonalColumns,
      personalDiscountButtonFunctions);

  //Add button functions
  $("#addDiscount").click(onAddDiscountClick);

  $("#addPersonalDiscount").click(onAddPersonalDiscount);

  //Selector for personal discounts
  $("#selectUser").select2();

  $('#selectUser').on('change', selectUserFunction);

  //Form submissions
  $("#discountForm").on('submit', function (e) {
    e.preventDefault();
    //Change behavior of save button
    if (onButtonAdd) {
      addNewDiscount('POST', false);
    } else {
      addNewDiscount('PUT', true);
    }
  });

  $("#discountPersonalForm").on('submit', function (e) {
    e.preventDefault();
    if (onButtonAdd) {
      addNewPersonalDiscount('POST', false);
    } else {
      addNewPersonalDiscount('PUT', true);
    }
  })

  //Full time period setter
  $("#changeDayPeriod").click(function () {
    fullDay = !fullDay;
    $("#dayDiscountTime").toggle();
  });

  $("#changePersonalPeriod").click(function () {
    fullDay = !fullDay;
    $("#personalDiscountTime").toggle();
  });

});

let request, onButtonAdd, editId, dayDiscountDataTable, personalDiscountDataTable;
let fullDay = true;

//Add functions for day discount datatable buttons
const dayDiscountButtonFunctions = function () {
  //Edit button
  $('.datatable tbody').on('click', '.editDayDiscount', function () {
    onEditDiscountClick($(this).attr("daydiscountid"));
  });
  //Change state button
  $('.datatable tbody').on('click', '.dayDiscountState', function () {
    changeDayDiscountState($(this),"restful/admin/discounts/day/state");
  });

};
//Add functions for personal discount datatable buttons
const personalDiscountButtonFunctions = function () {
  //Edit button
  $('.datatable tbody').on('click', '.editPersonalDiscount', function () {
    onEditPersonalDiscountClick($(this).attr("personalDiscountId"));
  });
  //Change state button
  $('.datatable tbody').on('click', '.personalDiscountState', function () {
    changeDayDiscountState($(this),"restful/admin/discounts/personal/state");
  });

};

// Day discount
function addNewDiscount(method, bool) {
  let isValidate = $("#discountForm").valid();
  if(isValidate){
    request = "restful/admin/discounts/day";
    var inputData = {
      reason: $("#DReason").val(),
      value: $("#DValue").val(),
      startDate: $("#DStartDate").val(),
      endDate: $("#DEndDate").val(),
      startTime: $("#DStartTime").val(),
      endTime: $("#DEndTime").val(),
      active: true,
    };
    if (bool) {
      inputData.id = editId;
    }
    if (fullDay) {
      inputData.startTime = "00:00";
      inputData.endTime = "23:59";
    }
    $.ajax({
      url: request,
      contentType: 'application/json; charset=UTF-8',
      data: JSON.stringify(inputData),
      type: method,
      success: function (result) {
        $('#addDiscountDiv').modal('toggle');
        dayDiscountDataTable.ajax.reload(null, false);
      },
      error: function (data, textStatus, xhr) {
        $(".danger-info").remove();
        let errors = data.responseJSON.userInputErrors;
        errors.forEach(function(item){
          $('#discountForm').append("<div class='danger-info'>" + item + "</div>");
        })
      }
    });
  }
}

//Edit day discount  button
function onEditDiscountClick(id) {
  $("#dayDiscountModalTitle").text(messages.modal.discount.editDiscount);
  editId = id;
  $("#discountForm").validate().resetForm();
  $('#discountForm input').removeClass('error');
  let isValidate = $("#discountForm").valid();
  if(isValidate){
    request = "restful/admin/discounts/day/" + editId;
    onButtonAdd = false;
    $(".danger-info").remove();
    $.ajax({
      url: request,
      type: 'GET',
      success: function (result) {
        $("#DReason").val(result.reason);
        $("#DValue").val(result.value);
        //Date and time formation
        $("#DStartDate").val(result.startDate);
        $("#DEndDate").val(result.endDate);
        if (result.startTime == "00:00" && result.endTime == "23:59") {
          fullDay = true;
          $("#dayDiscountTime").hide();
        } else {
          $("#dayDiscountTime").show();
          fullDay = false;
          $("#changeDayPeriod").prop('checked', false);
          $("#DStartTime").val(result.startTime);
          $("#DEndTime").val(result.endTime);
        }
      }
    });
  }
}
//Add personal discounts
function addNewPersonalDiscount(method, bool) {
  let isValidate = $("#discountPersonalForm").valid();
  if(isValidate){
    request = "restful/admin/discounts/personal/" + user;
    var inputData = {
      value: $("#PValue").val(),
      startTime: $("#PStartTime").val(),
      endTime: $("#PEndTime").val(),
      active: true,
    };
    if (bool) {
      inputData.id = editId;
    }
    if (fullDay) {
      inputData.startTime = "00:00";
      inputData.endTime = "23:59";
    }
    $.ajax({
      url: request,
      contentType: 'application/json; charset=UTF-8',
      data: JSON.stringify(inputData),
      type: method,
      success: function (result) {
        $('#addPersonalDiscountDiv').modal('toggle');
        personalDiscountDataTable.ajax.reload(null, false);
      },error: function (data, textStatus, xhr) {
        $(".danger-info").remove();
        if(xhr=='Bad Request'){
          $('#discountPersonalForm').append("<div class='danger-info'>" + messages.modal.discount.noUser + "</div>");
        }else{
          let errors = data.responseJSON.userInputErrors;
          errors.forEach(function(item){
            $('#discountPersonalForm').append("<div class='danger-info'>" + item + "</div>");
          })
        }
      }
    });
  }
}

//Edit personal discount
function onEditPersonalDiscountClick(id) {
  $("#personalDiscountModalTitle").text(messages.modal.discount.editDiscount);
  $("#discountPersonalForm").validate().resetForm();
  $('#discountPersonalForm input').removeClass('error');
  let isValidate = $("#discountPersonalForm").valid();
  if(isValidate) {
    editId = id;
    request = "restful/admin/discounts/personal/" + editId;
    onButtonAdd = false;
    $(".danger-info").remove();
    $("#selectUserDiv").hide();
    $("#selectUserStaticDiv").show();
    //Add data to the modal window
    $.ajax({
      url: request,
      type: 'GET',
      success: function (result) {
        $("#PValue").val(result.value);
        $("#selectUserStatic").text(
            result.user.firstName + " " + result.user.lastName);
        user = result.user.id;
        //Manipulation with time
        if (result.startTime == "00:00" && result.endTime == "23:59") {
          $("#personalDiscountTime").hide();
          $("#changePersonalPeriod").prop('checked', true);
        } else {
          fullDay = false;
          $("#changePersonalPeriod").prop('checked', false);
          $("#personalDiscountTime").show();
          $("#PStartTime").val(result.startTime);
          $("#PEndTime").val(result.endTime);
        }
      }
    });
  }
}

//Add discount functions
function onAddDiscountClick() {
  $("#dayDiscountModalTitle").text(messages.modal.discount.addDiscount);
  $("#discountForm").validate().resetForm();
  $('#discountForm input').removeClass('error');
  $("#dayDiscountTime").hide();
  $(".danger-info").remove();
  $("#changeDayPeriod").prop('checked', true);
  onButtonAdd = true;
  fullDay = true;
}

//Add personal discount functions
let list = 0;
let userList;
function onAddPersonalDiscount() {
  $("#discountPersonalForm").validate().resetForm();
  $('#discountPersonalForm input').removeClass('error');
  $.ajax({
    url: "restful/admin/discounts/personal/users",
    type: 'GET',
    success: function (result) {
      if (list != result.length) {
        $('#selectUser').empty();
        list = result.length;
        userList = result;
        $.each(userList, function (i, user) {
          $('#selectUser').append($('<option>', {
            value: user.id,
            text: user.firstName + ' ' + user.lastName
          }));
        })
        $('#selectUser').select2('val', ' ');
      } else {
        $('#selectUser').select2('val', ' ');
      }
    }
  });

  $("#personalDiscountModalTitle").text(messages.modal.discount.addDiscount);
  $("#personalDiscountTime").hide();
  $("#selectUserDiv").show();
  $("#selectUserStaticDiv").hide();
  $("#changePersonalPeriod").prop('checked', true);

  onButtonAdd = true;

}

let user;
const selectUserFunction = function () {
  user = $("#selectUser").val();
};

//Day discount columns creation
const DayColumns = [
  {
    'data': 'reason',
    'fnCreatedCell': function (nTd, sData) {
      $(nTd).html(
          "<div class='reason'>" + sData + "</div>"
      );
    }
  },
  {
    'data': 'value',
    'fnCreatedCell': function (nTd, sData) {
      $(nTd).html(
          "<div class='value'>" + sData + "%</div>"
      );
    }
  },
  {
    'data': 'date',
    'render': function (data, type, full, meta) {
      return "<span class='date'>"+ full.startDate + " - " + full.endDate+ "</span>";
    }
  },
  {
    'data': 'time',
    'orderable': false,
    'render': function (data, type, full, meta) {
      if (full.startTime == "00:00" && full.endTime == "23:59") {
        return messages.booking.allDayDiscount;
      } else {
        return full.startTime + " - " + full.endTime;
      }
    }
  },
  {
    'data': 'id',
    'orderable': false,
    'fnCreatedCell': function (nTd, sData) {
      $(nTd).html(
          "<span>" +
          "<button class='btn btn-raised btn-info editDayDiscount' " +
          "data-toggle='modal' data-target='#addDiscountDiv' dayDiscountId = "
          + sData +
          "><i class='glyphicon glyphicon-pencil'></i></button></span>"
      )
    }
  },
  {
    'data': 'id',
    'orderable': false,
    'render': function (data, type, full,row) {
      let stateBut = `<span><label class='switch'>
          <input type='checkbox' `;

      if (full.active) {
        stateBut += `checked `;
      }

      stateBut += `class='activate dayDiscountState' discountId = ` + full.id
          +
          ` dayDiscountState = ` + full.active +
          ` ><div class='slider round'></div></label></span>`
      return stateBut;
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
    'orderable': false,
    'render': function (data, type, full, meta) {
      if (full.startTime == "00:00" && full.endTime == "23:59") {
        return messages.booking.allDayDiscount;
      } else {
        return full.startTime + " - " + full.endTime;
      }
    }
  },
  {
    'data': 'id',
    'orderable': false,
    'fnCreatedCell': function (nTd, sData) {
      $(nTd).html(
          "<span>" +
          "<button class='btn btn-raised btn-info editPersonalDiscount' " +
          "data-toggle='modal' data-target='#addPersonalDiscountDiv' personalDiscountId = "
          + sData +
          "><i class='glyphicon glyphicon-pencil'></i></button></span>"
      );
    }
  },
  {
    'data': 'id',
    'orderable': false,
    'render' : function(data, type, full,row){
      let stateBut = `<span><label class='switch'>
          <input type='checkbox' `;

      if (full.active) {
        stateBut += `checked `;
      }

      stateBut += `class='activate personalDiscountState' discountId = ` + full.id
          +
          ` dayDiscountState = ` + full.active +
          ` ><div class='slider round'></div></label></span>`
      return stateBut;
    }
  }
];