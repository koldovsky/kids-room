<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit room</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">

</head>

<body>
   <div class="for-table">
      <legend class="for-table center-position"><strong>Room list</strong></legend>

      <table class="for-table" >
         <tr>
            <td><strong>Room name</strong></td>
            <td><strong>Room address</strong></td>
            <td><strong>Room phone number</strong></td>
            <td><strong>Room capacity</strong></td>
            <td><strong>Room manager</strong></td>
            <td><strong>Room city</strong></td>
         </tr>

         <c:forEach var="room" items="${roomList}">
         <tr>
            <td>${room.name}</td>
            <td>${room.address}</td>
            <td>${room.phoneNumber}</td>
            <td>${room.capacity}</td>
            <td>${room.manager}</td>
            <td>${room.city.nameCity}</td>
            <td><a href="adm-update-room?id=${room.id}"><input type="button" value="Edit" class="for-button"/></a></td>
            <td><input type="button" value="Delete" class="for-button"/></td>
         </tr>
         </c:forEach>
      </table>

      <a href="adm-add-room"><input type="button" value="Add" class="for-button center-position"/></a>
   </div>
</body>
</html>

