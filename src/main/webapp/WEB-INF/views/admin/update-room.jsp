<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/bookings.css">


<div class="for-table">
   <table class="for-table-fields">
   <form:form modelAttribute="<%=AdminConstants.ATR_ROOM%>" action="adm-update-room" method="post">

       <tr><th>
           <legend class="for-field"><strong><spring:message code="administrator.updateRoom"/></strong></legend>
       </th></tr>

       <tr><td>
           <form:hidden path="id" value="${room.id}"/>
           <form:hidden path="rate" value="${room.rate}" id="rates-json"/>
           <form:hidden path="managers" value="${room.managers}" id="managers-json"/>
           <form:hidden path="active" value="${room.active}"/>

           <div class="form-group">
              <label for="name" class="required">
                 <spring:message code="administrator.room.name"/></label>
                 <form:input path="name" id="name" value="${room.name}" class="form-control" />
                 <form:errors path="name" cssClass="error"/>
           </div>

           <div class="form-group">
              <label for="address" class="required">
                 <spring:message code="administrator.room.address"/></label>
                 <form:input path="address" id="address" value="${room.address}" class="form-control"/>
                 <form:errors path="address" cssClass="error" />
           </div>

           <div class="form-group">
              <label for="city" class="required">
                 <spring:message code="administrator.room.city"/></label>
                 <form:input path="city" id="city" value="${room.city}" class="form-control"/>
                 <form:errors path="city" cssClass="error"/>
           </div>

           <div class="form-group">
              <label for="phoneNumber" class="required">
                 <spring:message code="administrator.phoneNumber"/></label>
                 <form:input path="phoneNumber" id="phoneNumber" value="${room.phoneNumber}" class="form-control"/>
                 <form:errors path="phoneNumber" cssClass="error"/>
           </div>

           <div class="form-group">
              <label for="capacity" class="required">
                 <spring:message code="administrator.room.capacity"/></label>
                 <form:input path="capacity" id="capacity" value="${room.capacity}" class="form-control"/>
                 <form:errors path="capacity" cssClass="error"/>
           </div>

           <div class="form-group">
              <label class="for-field1">
              <label for="workingHoursStart" class="required">
                 <spring:message code="administrator.room.workingHoursStart"/></label>
                 <form:input type="time" path="workingHoursStart" id="workingHoursStart" value="${room.workingHoursStart}"
                                                      class="form-control" required="required"/>
                 <form:errors path="workingHoursStart" cssClass="error"/>
              </label>

              <label class="for-field2">
              <label for="workingHoursEnd" class="required">
                 <spring:message code="administrator.room.workingHoursEnd"/></label>
                 <form:input type="time" path="workingHoursEnd" id="workingHoursEnd" value="${room.workingHoursEnd}"
                                                    class="form-control" required="required"/>
                 <form:errors path="workingHoursEnd" cssClass="error"/>
              </label>
           </div>

           <div ng-app="angularjs-starter" ng-controller="MainCtrl">
               <div class="form-group">
                   <label class="for-field">
                      <spring:message code="administrator.room.manager"/></label>
                   <fieldset  data-ng-repeat="manager in managers">
                       <label class="for-field">
                           <select name="managers" class="form-control" ng-model="manager.managerId" managerId="" placeholder="manager">
                              <c:forEach var="manager" items="${managerList}">
                                 <option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
                              </c:forEach>
                           </select>
                       </label>
                       <button class="remove" ng-show="$last" ng-click="removeManager()">-</button>
                   </fieldset>
                   <button type="button" class="addfields" ng-click="addNewManager()" >+</button>
               </div>

               <div class="form-group">
                  <label class="for-field">
                     <spring:message code="administrator.room.rate"/></label>
                  <fieldset  data-ng-repeat="rate in rates">
                     <label class="for-field1">
                        <input id="myText" type="text" ng-model="rate.hourRate" hourRate="" class="form-control"
                               placeholder=<spring:message code="administrator.room.rate.hourRate"/> />
                     </label>
                     <label class="for-field2">
                        <input id="myText" type="text" ng-model="rate.priceRate" priceRate="" class="form-control"
                               placeholder=<spring:message code="administrator.room.rate.priceRate"/> />
                     </label>
                        <button class="remove" ng-show="$last" ng-click="removeRate()">-</button>
                  </fieldset>

                  <button type="button" class="addfields" ng-click="addNewRate()" >+</button>
                  <form:errors path="rate" cssClass="error"/>
              </div>

               <div class="form-group">
                  <button type="submit" name="submit" class="btn btn-raised btn-info glyphicon glyphicon-ok"
                          ng-click="submit()"></button>
                  <button type="reset" name="reset" class="btn btn-raised btn-danger glyphicon glyphicon-remove"></button>
               </div>

               <script src="resources/js/room-update-for-rates.js"></script>
           </div>

       </td></tr>

   </form:form>
   </table>
</div>