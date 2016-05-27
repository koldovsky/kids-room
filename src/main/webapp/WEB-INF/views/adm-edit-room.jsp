<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/bookings.css">


<body>
   <div class="tableDiv for-table">
      <table class="for-table">
         <tr class="hide-border">
            <th colspan="8" class="set-standard-color"><legend class="for-table"><strong>Room list</strong></legend></th>
         </tr>
         <tr>
            <th><strong>Room name</strong></th>
            <th><strong>Room address</strong></th>
            <th><strong>Room phone number</strong></th>
            <th><strong>Room capacity</strong></th>
            <th><strong>Room manager</strong></th>
            <th><strong>City</strong></th>
            <th><strong>EDIT</strong></th>
            <th><strong>DELETE</strong></th>
         </tr>

         <c:forEach var="room" items="${roomList}">
         <tr>
            <td>${room.name}</td>
            <td>${room.address}</td>
            <td>${room.phoneNumber}</td>
            <td>${room.capacity}</td>
            <td>${room.manager}</td>
            <td>${room.city}</td>
            <td><a href="adm-update-room?id=${room.id}"><button class="btn btn-raised btn-primary glyphicon glyphicon-pencil">
                                                        </button></a></td>

            <td><button class="btn btn-raised btn-primary btn-lg glyphicon glyphicon-trash waves-light"/></td>
         </tr>
         </c:forEach>

        <tr></tr>

         <tr>
            <td colspan="8" class="hide-border set-standard-color">
                <a href="adm-add-room"><input type="button" value="Add"
                                        class="btn btn-raised btn-primary waves-effect waves-light hide-border"/></a>
            </td>
         </tr>
      </table>

   </div>
</body>