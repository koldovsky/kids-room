<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>



<div>


<form  action="savePassword" method="post">
    <label>Password</label>
    <input id="pass" name="password" type="password" value="" />

    <button type="submit">
       Save
    </button>
</form>
</div>





<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function resetPass(){
    var email = $("#email").val();
    $.post("<c:url value="/user/resetPassword"></c:url>",{email: email}, function(data){
            window.location.href =
              "<c:url value="/login.html"></c:url>" + "?message=" + data.message;
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("MailError") > -1) {
            window.location.href =
              "<c:url value="/emailError.html"></c:url>";
        }
        else {
            window.location.href =
              "<c:url value="/login.html"></c:url>" + "?message=" + data.responseJSON.message;
        }
    });
}
</script>
