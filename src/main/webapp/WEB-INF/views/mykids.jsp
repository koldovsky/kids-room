<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="myKidsUrl" />

<div>
<h2 class="list">Here are your kids:</h2>
<c:forEach var="kid" items="${kids}">
    <h3 class="list">${kid.getFirstName()}</h3>
</c:forEach>

<form action="registerkid" class="list">
    <input class="btn-primary" type="submit" value="Add">
</form>

</div>