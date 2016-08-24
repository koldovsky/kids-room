$(function () {
    $('#confirmation-dialog-div').dialog({
        // title: 'Confirmation',
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    });
    $('#removeKids').click(function () {
        confirmation(takingKidOff,"","","")
        // $('#confirmation-dialog').dialog("open")
    });
    $('#removeKids').hover(function(){
            $(this).css('color','red');
            $(this).css('cursor','pointer ');
    }, function(){
        $(this).css("color", "black");
    });

});
function takingKidOff() {
    // var kidId = kid.getId();
    var parameters = location.search.substring(1).split("&");
    var kidMap = parameters[0].split("=");
    var kidId = kidMap[1];
    var kidredirect="/home/mykids";
    // jQuery.get( "removemykid?id="+kidId )
    $.ajax({
            url: 'removemykid?id=' + kidId,
            success: function(responseText) {
            },
            error: function(xhr,status,error){
                alert("Error!" + err+"  status = "+status);
            }
    })
$(location).attr('href',kidredirect);
}

// function confirmation (func1, param1, func2,param2){
//     var myDialog = $('#confirmation-dialog-div');
//     myDialog.dialog('open');
//     $('#confirmYes').off('click').on('click', function func1a(){
//         if (func1 !== undefined && func1 !== null && func1 !== "") {
//             func1(param1);
//         }
//         myDialog.dialog('close');
//     });
//     $('#confirmNo').off('click').on('click', function func2a(){
//         if (func2 !== undefined && func2 !== null && func2 !== "") {
//             func2(param2);
//         }
//         myDialog.dialog('close');
//     });
// }