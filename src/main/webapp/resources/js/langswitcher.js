$(document).ready(function(){
    $(".langitem").click(function(){
        var locale = $(this).attr("id").toLowerCase();
        var path = window.location.href.toString();
        var newpath;
        if(path.indexOf("language=") > -1){
        	newpath = updateQueryStringParameter(path, "language", locale);
        }
        else{
	        if (path.indexOf('?') > -1){
	            newpath = path + "&language=" + locale;
	        }
	        else{
	            newpath = path + "?language=" + locale;
	        }
    }
        window.location.href = newpath;
    });

});

function updateQueryStringParameter(uri, key, value) {
  var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
  var separator = uri.indexOf('?') !== -1 ? "&" : "?";
  if (uri.match(re)) {
    return uri.replace(re, '$1' + key + "=" + value + '$2');
  }
  else {
    return uri + separator + key + "=" + value;
  }
}