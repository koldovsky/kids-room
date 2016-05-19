<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit location</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">

</head>

<body>
   <div class="for-table">
       <table class="for-table">
          <tr>
             <th colspan="7">
                <legend class="for-table"><strong>Manager list</strong></legend>
             </th>
          </tr>
          <tr>
             <td><strong>Email</strong></td>
             <td><strong>First Name</strong></td>
             <td><strong>Last Name</strong></td>
             <td><strong>Phone number</strong></td>
             <td><strong>Active account</strong></td>
             <td><strong>EDIT</strong></td>
             <td><strong>DELETE</strong></td>
          </tr>

          <c:forEach var="manager" items="${managerList}">
          <tr>
             <td>${manager.email}</td>
             <td>${manager.firstName}</td>
             <td>${manager.lastName}</td>
             <td>${manager.phoneNumber}</td>
             <td>${manager.enabled}</td>
             <td><a href="adm-update-manager?id=${manager.id}">
                    <button class="btn btn-raised btn-info glyphicon glyphicon-pencil"></button></a></td>

             <td><c:url var="deleteUrl" value="/adm-edit-manager?id=${manager.id}"/>
                <form:form id="${managerFormId}" action="${deleteUrl}" method="POST">
                   <input id="manager" name="manager" type="hidden" value="${manager.id}" />
                   <button type="submit" value="Delete" onClick="return confirm('sure?')"
                            class="btn btn-info btn-lg glyphicon glyphicon-trash"></button>
                </form:form>
             </td>
          </tr>
          </c:forEach>
          <tr></tr>
          <tr>
             <td colspan="7" class="hide-border">
                <a href="adm-add-manager"><input type="button" value="Add"
                                           class="btn btn-raised btn-primary waves-effect waves-light"/></a>
             </td>
          </tr>
       </table>
    </div>
</body>
</html>