<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/button-styles.css">


<div class="for-table">
   <table class="col-sm-offset-4 col-sm-3 reg-form">


       <tr><th>
           <legend class="for-field"><strong><spring:message code="administrator.updateRoom"/></strong></legend>
       </th></tr>

       <tr><td>
       <form:form modelAttribute="<%=AdminConstants.ATR_ROOM%>" action="adm-update-room" method="post" id="roomForm">

           <form:hidden path="id" value="${room.id}"/>
           <form:hidden path="rate" value="${room.rate}" id="rates-json"/>
           <form:hidden path="managers" value="${room.managers}" id="managers-json"/>
           <form:hidden path="active" value="${room.active}"/>

           <div class="form-group sizing-between">
              <label for="name" class="required">
                 <spring:message code="administrator.room.name"/></label>
                 <form:input path="name" value="${room.name}" class="form-control" />
                 <form:errors path="name" cssClass="error"/>
           </div>

           <div class="form-group sizing-between">
              <label for="address" class="required">
                 <spring:message code="administrator.room.address"/></label>
                 <form:input path="address" value="${room.address}" class="form-control"/>
                 <form:errors path="address" cssClass="error" />
           </div>

           <div class="form-group sizing-between">
              <label for="city" class="required">
                 <spring:message code="administrator.room.city"/></label>
                 <form:input path="city" value="${room.city}" class="form-control"/>
                 <form:errors path="city" cssClass="error"/>
           </div>

           <div class="form-group sizing-between">
              <label for="phoneNumber" class="required">
                 <spring:message code="administrator.phoneNumber"/></label>
                 <form:input path="phoneNumber" value="${room.phoneNumber}" class="form-control"/>
                 <form:errors path="phoneNumber" cssClass="error"/>
           </div>

           <div class="form-group sizing-between">
              <label for="capacity" class="required">
                 <spring:message code="administrator.room.capacity"/></label>
                 <form:input path="capacity" value="${room.capacity}" class="form-control"/>
                 <form:errors path="capacity" cssClass="error"/>
           </div>

           <div class="form-group sizing-between">
              <label class="for-field1">
              <label for="workingHoursStart" class="required">
                 <spring:message code="administrator.room.workingHoursStart"/></label>
                 <form:input type="time" path="workingHoursStart" value="${room.workingHoursStart}"
                                         class="form-control" required="required"/>
                 <form:errors path="workingHoursStart" cssClass="error"/>
              </label>

              <label class="for-field2">
              <label for="workingHoursEnd" class="required">
                 <spring:message code="administrator.room.workingHoursEnd"/></label>
                 <form:input type="time" path="workingHoursEnd" value="${room.workingHoursEnd}"
                                         class="form-control" required="required"/>
                 <form:errors path="workingHoursEnd" cssClass="error"/>
              </label>
           </div>

           <div ng-app="angularjs-starter" ng-controller="MainCtrl">
               <div class="form-group sizing-between">
                   <label class="for-field">
                       <spring:message code="administrator.room.manager"/></label>
                   <fieldset data-ng-repeat="manager in managers">
                       <label class="for-field">
                           <select class="form-control" ng-model="manager.id" managerId=""
                                   placeholder="manager">
                              <c:forEach var="manager" items="${managerList}">
                                 <option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
                              </c:forEach>
                           </select>
                       </label>
                   </fieldset>
                   <br>
                   <button type="button" ng-click="removeManager()">-</button>
                   <button type="button" ng-click="addNewManager()" >+</button>
                   <br>
                   <form:errors path="managers" cssClass="error"/>
               </div>

               <div class="form-group sizing-between">
                  <label class="for-field">
                     <spring:message code="administrator.room.rate"/></label>
                     <label class="for-field1">Hour</label>
                     <label class="for-field2">Price</label>
                  <fieldset data-ng-repeat="rate in rates">
                     <label class="for-field1">
                        <input id="myText" type="text" ng-model="rate.hourRate" hourRate="" class="form-control"
                               placeholder=<spring:message code="administrator.room.rate.hourRate"/> />
                     </label>
                     <label class="for-field2">
                        <input id="myText" type="text" ng-model="rate.priceRate" priceRate="" class="form-control"
                               placeholder=<spring:message code="administrator.room.rate.priceRate"/> />
                     </label>
                  </fieldset>
                      <br>
                      <button type="button" ng-click="removeRate()">-</button>
                      <button type="button" ng-click="addNewRate()">+</button>
                      <br>
                  <form:errors path="rate" cssClass="error"/>
               </div>

               <div class="form-group">
                  <button type="submit" class="button button-confirm" ng-click="submit()">OK</button>
                  <button type="reset" class="button button-cancel"
                          onclick="window.location.href='adm-edit-room'">Cancel</button>
               </div>

               <script src="resources/js/room-dynamic-managers-rates.js"></script>
           </div>

       </form:form>
       </td></tr>

   </table>
</div>

<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
</c:if>
<script src="resources/js/validation-room.js"></script>