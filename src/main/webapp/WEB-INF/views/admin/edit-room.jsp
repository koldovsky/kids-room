<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/bookings.css">


<body>
  <div class="tableDiv  for-table">
      <table class="for-table">
         <tr class="hide-border">
            <th colspan="10" class="set-standard-color">
               <legend class="for-table"><strong><spring:message code="administrator.editRooms"/></strong></legend>
            </th>
         </tr>
         <tr></tr>
         <tr>
            <th><strong><spring:message code="administrator.room.name"/></strong></th>
            <th><strong><spring:message code="administrator.room.address"/></strong></th>
            <th><strong><spring:message code="administrator.room.city"/></strong></th>
            <th><strong><spring:message code="administrator.phoneNumber"/></strong></th>
            <th><strong><spring:message code="administrator.room.capacity"/></strong></th>
            <th><strong><spring:message code="administrator.room.roomWorkTime"/></strong></th>
            <th><strong><spring:message code="administrator.room.manager"/></strong></th>
            <th><strong><spring:message code="administrator.room.rate"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.block"/></strong></th>
         </tr>

         <c:forEach var="room" items="${roomList}">
         <tr>
            <td>${room.name}</td>
            <td>${room.address}</td>
            <td>${room.city}</td>
            <td>${room.phoneNumber}</td>
            <td>${room.capacity}</td>
            <td>${room.workingHoursStart} ${room.workingHoursEnd}</td>
            <td class="td-full">
               <table class="block-link hide-border">
                  <c:forEach var="manager" items="${room.managers}">
                     <tr><td >${manager.firstName} ${manager.lastName}</td></tr>
                  </c:forEach>
               </table>
            </td>
            <td class="td-full">
                <table class="block-link hide-border">
                    <tr>
                        <td><spring:message code="administrator.room.rate.hourRate"/></td>
                        <td><spring:message code="administrator.room.rate.priceRate"/></td>
                    </tr>
                    <c:forEach var="rate" items="${room.rates}">
                    <tr>
                        <td>${rate.hourRate}</td>
                        <td>${rate.priceRate}</td>
                    </tr>
                    </c:forEach>
                </table>
            </td>
            <td><a href="adm-update-room?id=${room.id}"><button class="btn btn-raised btn-info glyphicon glyphicon-pencil">
                                                        </button></a></td>

            <td><button class="btn btn-raised btn-danger glyphicon glyphicon-trash"/></td>
         </tr>
         </c:forEach>

         <tr></tr>

         <tr>
            <th colspan="10" class="hide-border set-standard-color">
                <a href="adm-add-room"><input type="button" value=<spring:message code="administrator.add"/>
                                        class="btn btn-raised btn-primary waves-effect waves-light hide-border"/></a>
            </th>
         </tr>
       </table>
    </div>
</body>