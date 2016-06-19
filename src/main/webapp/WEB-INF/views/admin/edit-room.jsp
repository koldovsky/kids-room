<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<body>
  <div class="tableDiv for-table">
      <table class="for-table">
         <tr class="hide-border">
            <th colspan="11" class="set-standard-color">
               <legend class="for-table"><strong><spring:message code="administrator.listRooms"/></strong></legend>
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
            <th><strong><spring:message code="administrator.room.active"/></strong></th>
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
            <td class="td-full">
                <table class="block-link hide-border">
                    <tr><td class="hide-border">Start:</td></tr>
                    <tr><td>${room.workingHoursStart}</td></tr>
                    <tr><td>End:</td></tr>
                    <tr><td class="hide-border">${room.workingHoursEnd}</td></tr>
                </table>
             </td>
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
            <td>
                <c:if test="${room.isActive() eq true}">
                    <img src="resources/img/ok.png" class="img-size">
                </c:if>
                <c:if test="${room.isActive() ne true}">
                   <img src="resources/img/no.png" class="img-size">
                </c:if>
            </td>
            <td><a href="adm-update-room?id=${room.id}">
                   <button class="btn btn-raised btn-info glyphicon glyphicon-pencil active">
                   </button></a></td>
            <td>
                <c:url var="lockUrl" value="/adm-edit-room?id=${room.id}"/>
                <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                   <input id="room" name="room" type="hidden" value="${room.id}" />
                   <button type="submit" value="lock" onClick="return confirm('sure?')"
                           class="btn btn-raised btn-warning glyphicon glyphicon-sort active for-lock-button active">
                           </button>
                </form:form>
            </td>
         </tr>
         </c:forEach>

         <tr></tr>

         <tr>
            <th colspan="11" class="hide-border set-standard-color">
                <a href="adm-add-room"><input type="button" value=<spring:message code="administrator.add"/>
                                        class="btn btn-raised btn-primary waves-effect waves-light active hide-border"/></a>
            </th>
         </tr>
       </table>
    </div>
</body>