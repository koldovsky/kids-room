<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="myKidsUrl" />

<link rel='stylesheet' href='resources/css/mykidslist.css'>

<script src="resources/js/mykidslist.js">
</script>

<div style="text-align:center;">
<div class="list">
<h2>Here are Your kids:</h2>
<c:forEach var="kid" items="${kids}">
<div class="kidslistblock">
    <div class="kidslistitem">
    <h3>${kid.getFullName()}</h3>
    </div>

    <button class="btn glyphicon glyphicon-pencil">&nbsp;Edit</button>
    <div class="kidinfo" data-id="${kid.getId()}">
            <p>Date of birth: ${kid.getDateOfBirth()}</p>
            <p>Comment: ${kid.getComment()}</p>
    </div>
</div>


</c:forEach>

</div>
<form action="registerkid">
    <input class="btn-primary" type="submit" value="Add">
</form>
</div>