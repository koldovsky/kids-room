<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit city</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">

</head>

<body>
    <div class="for-table">

        <legend class="for-table"><strong>City list</strong></legend>

        <table class="for-field" >
            <tr> <td><strong>City name</strong></td> </tr>
            <c:forEach var="city" items="${cityList}">
            <tr>
                <td>${city.nameCity}</td>
                <td>
                    <c:url var="deleteUrl" value="/adm-edit-city?id=${city.idCity}"/>
                    <form:form id="${cityFormId}" action="${deleteUrl}" method="post">
                       <input id="city" name="city" type="hidden" value="${city.idCity}" />
                       <input type="submit" value="Delete" onClick="return confirm('sure?')" class="for-button" />
                    </form:form>
                </td>
            </tr>
            </c:forEach>
        </table>

        <a href="adm-add-city"><input type="button" value="Add" class="for-button"/></a>

    </div>
</body>
</html>