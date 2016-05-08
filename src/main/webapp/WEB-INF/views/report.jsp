<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportUrl" />

<h2>All parents:</h2>
<form action="">
<c:forEach var="parent" items="${parents}">
    <div id="${parent.getEmail()}"">
        <input type="text" value="${parent.getFirstName()}" disabled>
        <input type="text" value="${parent.getLastName()}" disabled>
        <input type="text" value="${parent.getEmail()}" disabled>
        <input type="text" value="${parent.getPhoneNumber()}" disabled>
        <input type="checkbox" checked></br>
    </div>
</c:forEach>

<label><p>Choose start time: <input type="date" value="${dateThen}"></label></p>
<label><p>Choose end time: <input type="date" value="${dateNow}"></label></p>
<input type="submit" value="Generate!">
</form>