<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<body>
    <div class="for-table">
        <table class="for-table-fields">
        <form:form class="for-table" action="adm-add-room" method="post" modelAttribute="<%=AdminConstants.ATR_ROOM%>">

           <tr><th>
               <legend class="for-field"><strong><spring:message code="administrator.addRoom"/></strong></legend>
           </th></tr>

           <tr><td>
               <form:hidden path="rate" name="rate" id="rates-json"/>
               <form:hidden path="managers" name="managers" id="managers-json"/>


               <div class="form-group sizing-between">
                  <label for="name" class="required">
                     <spring:message code="administrator.room.name"/></label>
                     <form:input path="name" class="form-control" />
                     <form:errors path="name" cssClass="error"/>
               </div>

               <div class="form-group sizing-between">
                  <label for="address" class="required">
                     <spring:message code="administrator.room.address"/></label>
                     <form:input path="address" class="form-control" />
                     <form:errors path="address" cssClass="error" />
               </div>

               <div class="form-group sizing-between">
                  <label for="city" class="required">
                     <spring:message code="administrator.room.city"/></label>
                     <form:input path="city" class="form-control"/>
                     <form:errors path="city" cssClass="error"/>
               </div>

               <div class="form-group sizing-between">
                  <label for="phoneNumber" class="required">
                     <spring:message code="administrator.phoneNumber"/></label>
                     <form:input path="phoneNumber" class="form-control"/>
                     <form:errors path="phoneNumber" cssClass="error"/>
               </div>

               <div class="form-group sizing-between">
                  <label for="capacity" class="required">
                     <spring:message code="administrator.room.capacity"/></label>
                     <form:input path="capacity" type="number" class="form-control"/>
                     <form:errors path="capacity" cssClass="error"/>
               </div>

               <div class="form-group sizing-between">
                   <label class="for-field1">
                   <label for="workingHoursStart" class="required">
                       <spring:message code="administrator.room.workingHoursStart"/></label>
                       <form:input type="time" path="workingHoursStart" class="form-control"/>
                       <form:errors path="workingHoursStart" cssClass="error"/>
                   </label>

                   <label class="for-field2">
                   <label for="workingHoursEnd" class="required">
                      <spring:message code="administrator.room.workingHoursEnd"/></label>
                      <form:input type="time" path="workingHoursEnd" class="form-control"/>
                      <form:errors path="workingHoursEnd" cssClass="error"/>
                   </label>
               </div>

               <div ng-app="angularjs-starter" ng-controller="MainCtrl">
                   <div class="form-group sizing-between">
                       <label class="for-field">
                          <spring:message code="administrator.room.manager"/></label>
                       <fieldset  data-ng-repeat="manager in managers">
                           <label class="for-field">
                               <select name="managers" class="form-control" ng-model="manager.id" managerId=""
                                       placeholder="manager">
                                   <c:forEach var="manager" items="${managerList}">
                                      <option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
                                   </c:forEach>
                               </select>
                           </label>
                           <button class="remove" ng-show="$last" ng-click="removeManager()">-</button>
                       </fieldset>

                       <button type="button" class="addfields" ng-click="addNewManager()" >+</button>
                   </div>

                   <div class="form-group sizing-between">
                       <label class="for-field">
                          <spring:message code="administrator.room.rate"/></label>
                       <fieldset  data-ng-repeat="rate in rates">
                          <label class="for-field1">
                             <input type="text" ng-model="rate.hourRate" hourRate="" class="form-control"
                                    placeholder=<spring:message code="administrator.room.rate.hourRate"/> />
                          </label>
                          <label class="for-field2">
                             <input type="text" ng-model="rate.priceRate" priceRate="" class="form-control"
                                    placeholder=<spring:message code="administrator.room.rate.priceRate" /> />
                          </label>
                              <button class="remove" ng-show="$last" ng-click="removeRate()">-</button>
                       </fieldset>

                       <button type="button" class="addfields" ng-click="addNewRate()" >+</button>
                       <form:errors path="rate" cssClass="error"/>
                   </div>

                   <div class="form-group">
                      <button type="submit" class="btn btn-raised btn-info glyphicon glyphicon-ok active"
                              ng-click="submit()"></button>
                      <button type="reset" class="btn btn-raised btn-danger glyphicon glyphicon-remove active"
                              onclick="window.location.href='/home/adm-edit-room'"></button>
                   </div>

                   <script src="resources/js/room-add-for-rates.js"></script>
               </div>

            </td></tr>
        </form:form>
        </table>
    </div>
</body>

