$(document).ready(function() {
    $('#userConfirm').keyup(function(){
        var p1 = $('#userPassword').val();
        var p2 = $('#userConfirm').val();
        if(p1!=p2){
            $('#validPassword').text('This password does not match with the password in "Password" field. Please enter the same password');
            $('button').prop('disabled', true);
        }else{
            $('#validPassword').empty();
            $('button').prop('disabled', false);
        }
    });

    $('#userEmail').keyup(function(){
        var p = $('#userEmail').val();
        var exp = /^(\w){1,60}[@][s]oft[s]erveinc[.]com$/;
        if(!exp.test(p)) {
            $('#validEmail').text('Please enter your email with "softserveinc.com" domain');
            $('button').prop('disabled', true);
        }else{
            $('#validEmail').empty();
            $('button').prop('disabled', false);
        }
    });

    $('#phonenumber').keyup(function(){
        var p = $('#phonenumber').val();
        var exp = /^\W*\d\W*\d\W*\d\W*\d\W*\d\W*\d\W*\d\W*\d\W*\d\W*\d\W*$/

        if(!exp.test(p)){
            $('#validPhone').text('You entered incorrect phone number');
            $('button').prop('disabled', true);
        }else{
            $('#validPhone').empty();
            $('button').prop('disabled', false);
        }
    });

    $('#userEmail').focus(function(){
          $('#validEmail').text('Allowed email only with softserve.inc domain');
    });

    $('#phonenumber').focus(function(){
          $('#validPhone').text('Example: 098 123 12 12');
    });

});