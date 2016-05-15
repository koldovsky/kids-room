<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit location</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-new-style.css">

</head>

<body>
    <div>
        <script src="resources/js/adm-edit-loc.js"></script>

        <legend class="for-table"><strong>Manager list</strong></legend>

        <table class="for-table">
            <tr>
                <td><strong>Manager email</strong></td>
                <td><strong>Manager First Name</strong></td>
                <td><strong>Manager Last Name</strong></td>
                <td><strong>Manager Phone number</strong></td>
            </tr>

            <c:forEach var="manager" items="${managerList}">
            <tr>
                <td>${manager.email}</td>
                <td>${manager.firstName}</td>
                <td>${manager.lastName}</td>
                <td>${manager.phoneNumber}</td>
                <td><a href="adm-update-manager?id=${manager.id}"><input type="button" value="Edit" class="for-button"/></a>

                    <c:url var="deleteUrl" value="/adm-edit-manager?id=${manager.id}"/>
                    <form:form id="${managerFormId}" action="${deleteUrl}" method="POST">
                          <input id="manager" name="manager" type="hidden" value="${manager.id}" />
                          <input type="submit" value="Delete" onClick="return confirm('sure?')" class="for-button"/>
                    </form:form>
                    </td>
            </tr>
            </c:forEach>
        </table>

            <a href="adm-add-manager"><input type="button" value="Add" class="for-button"/></a>

    </div>
</body>
</html>