'use strict';

$(function (){
  
  $('#changeToPersonal').click(function () {
    $('#dayDiscountFull').hide();
    $('#personalDiscountFull').show();
  });

  $('#changeToDaily').click(function () {
    $('#personalDiscountFull').hide();
    $('#dayDiscountFull').show();
  });

});