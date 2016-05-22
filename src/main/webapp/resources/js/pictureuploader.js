/*
(function previewFile(){
       var preview = document.querySelector('img');
       var file    = document.querySelector('input[type=file]').files[0];
       var reader  = new FileReader();

       reader.onloadend = function () {
           preview.src = reader.result;
       }

       if (file) {
           reader.readAsDataURL(file);
       } else {
           preview.src = "";
       }
  })()*/


document.querySelector('#image').addEventListener('change', function(){
    var reader = new FileReader();
    reader.onload = function(){
        var binaryString = this.result;
        document.querySelector('#result').innerHTML = binaryString;
        }
    reader.readAsArrayBuffer(this.files[0]);
  }, false);
