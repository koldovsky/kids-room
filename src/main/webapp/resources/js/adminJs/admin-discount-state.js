//const stateRequest = 'restful/admin/discounts/day/state';

function changeDayDiscountState(button,stateRequest) {
  let state = button.attr('dayDiscountState');
  let id = button.attr('DiscountId');
  if (state == 'true') {
    button.parents('tr').addClass('tr-not-active');
    button.attr('dayDiscountState',false);
    state = false;
  } else {
    (button).parents('tr').removeClass('tr-not-active');
    button.attr('dayDiscountState',true);
    state = true;
  }

  let inputData = {
    id : id,
    active : state
  }

  $.ajax({
    url: stateRequest,
    contentType: 'application/json; charset=UTF-8',
    data: JSON.stringify(inputData),
    type: 'PUT',
    error: function () {
      console.log("State changed failed with id = "+id);
    }
  });
}
