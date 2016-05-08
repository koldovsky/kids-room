<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportUrl" />

<h2>All parents:</h2>
<table id="parents">
<c:forEach var="parent" items="${parents}">
    <tr>
    <td>${parent.getFirstName()}</td>
    <td>${parent.getLastName()}</td>
    <td>${parent.getEmail()}</td>
    <td>${parent.getPhoneNumber()}</td>
    </tr>
</c:forEach>
</table>