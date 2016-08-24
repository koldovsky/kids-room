function confirmation (func1, param1, func2,param2){
    var myDialog = $('#confirmation-dialog-div');
    myDialog.dialog('open');
    $('#confirmYes').off('click').on('click', function func1a(){
        if (func1 !== undefined && func1 !== null && func1 !== "") {
            func1(param1);
        }
        myDialog.dialog('close');
    });
    $('#confirmNo').off('click').on('click', function func2a(){
        if (func2 !== undefined && func2 !== null && func2 !== "") {
            func2(param2);
        }
        myDialog.dialog('close');
    });
}