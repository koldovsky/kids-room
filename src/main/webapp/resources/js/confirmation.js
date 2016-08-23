function confirmashion (title, text, func, func2){
    var dialog = $('#confirm-dialog');
    dialog.show();
    $('button.cancel').off('click').on('click', cancelCallback);
    $('button.confirm').off('click').on('click', confirmCallback);
}