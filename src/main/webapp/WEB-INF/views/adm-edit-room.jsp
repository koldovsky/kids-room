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
      <legend class="for-table"><strong>Room list</strong></legend>

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
            <td><a href="adm-update-room?id=${room.id}"><button class="btn btn-raised btn-info glyphicon glyphicon-pencil">
                                                        </button></a></td>

            <td><button class="btn btn-info btn-lg glyphicon glyphicon-trash waves-light"/></td>
         </tr>
         </c:forEach>
      </table>

      <a href="adm-add-room"><input type="button" value="Add" class="btn btn-raised btn-primary waves-effect waves-light"/></a>
   </div>
</body>
</html>

