$(function () {
  refreshDaysDiscounts();
});

function refreshDaysDiscounts() {
  var request = "restful/admin/discounts/";
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
              + '<td>' + moment(discount.startDate).format("YYYY-MM-DD")+' - '+ moment(discount.endDate).format("YYYY-MM-DD")+ '</td>'
              + '<td>' + moment(discount.startDate).format("HH-MM")+' - '+ moment(discount.endDate).format("HH-MM") + '</td>'
              + '<td><a href="#" tabindex="-1"><button id="btn-edit"'
              +'class="btn btn-raised btn-info"><i class="glyphicon glyphicon-pencil"></i></button></a>'
              +'</td>'
              + '<td>'+discount.active+'</td><tr>';
        });

        $("#firstLoader").hide();
        $("#dayDiscounts").append(tr).append(addButtonForDayDiscount());

      }
    });
}

function dayDiscountsLoading(){
  $("#firstLoader").show();
  $(".discountData").remove();
}

function addButtonForDayDiscount() {
  return '<tr><th colspan="6" class="hide-border set-standard-color">'
      + '<button type="button" class="btn btn-raised btn-primary btn-add-room" '
      + 'id="addDiscount" data-toggle="modal" data-target="#updateAbonnement">'
      + messages.preloader.buttons.add
      + '</button>'
      + '</th>'
      + '</tr>';
}
