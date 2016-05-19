<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Confirm page</title>
	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<body>
<div class="for-table">
    <table class="for-table-fields">
    <form:form class = "confirm-manager" modelAttribute="manager" action="confirm-manager" method="post">

        <tr><th>
           <strong>${manager.email}<<strong><br>
           Enter password to activate your account.
        </th></tr>

        <form:hidden path="id" />
        <form:hidden path="email" id="email" value="${manager.email}"/>
        <form:hidden path="firstName" id="firstName" value="${manager.firstName}"/>
        <form:hidden path="lastName" id="lastName" value="${manager.lastName}"/>
        <form:hidden path="phoneNumber" id="phoneNumber" value="${manager.phoneNumber}" />
        <form:hidden path="role" id="role" value="${manager.role}" />

        <tr><td>
            <div class="form-group">
                 <label for="password" class="for-field">Password
                 <form:input path="password" id="password" value="" class="form-control" required="required"/>
                 </label>
            </div>
        </td></tr>

        <tr></tr>
        <tr><td class="hide-border">
            <div class="form-group">
                <button type="submit" name="submit" class="btn btn-raised btn-primary btn-lg glyphicon glyphicon-ok"></button>
            </div>
        </td></tr>

    </form:form>
    </table>
</div>
</body>
</html>