$(document).ready(function () {

  $.validator.addMethod("requiredWithEmptySpace", function (value) {
    return value.trim().length != 0;
  }, messages.room.errors.requiredWithEmptySpace);
  $.validator.addMethod("discountValue", function(value, element, regexpr){
    return regexpr.test(value);
  }, messages.room.errors.wrongDiscountValue);

  $('#discountForm').validate({
    rules: {
      reason: {
        minlength: 3,
        maxlength: 255,
        requiredWithEmptySpace: true
      },
      value: {
        requiredWithEmptySpace: true,
        discountValue: constants.regex.discountValueRegex
      },
      startDate: {
        required: true
      },
      endDate: {
        required: true
      },
      startTime: {
        required: true
      },
      endTime: {
        required: true
      }
    }
  });

  $("#discountPersonalForm").validate({
    rules: {
      select: {
        required: true
      },
      value: {
        requiredWithEmptySpace: true,
        discountValue: constants.regex.discountValueRegex
      },
      startTime: {
        required: true
      },
      endTime: {
        required: true
      }
    }
  })
});
