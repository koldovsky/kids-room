<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<body>
   <div class="for-table">
       <table class="for-table-fields">
           <tr><th>
               <legend class="for-field"><strong><spring:message code="administrator.addManager"/></strong></legend>
           </th></tr>

           <tr><td>
               <form:form action="adm-add-manager" method="post" modelAttribute="<%=AdminConstants.ATR_MANAGER%>">

                  <form:input path="role" type="hidden" value="MANAGER" />
                  <form:input path="password" type="hidden" name="password"/>

                  <div class="form-group sizing-between">
                      <label for="email" class="required">
                      <spring:message code="administrator.manager.email"/></label>
                      <form:input path="email" type="email" class="form-control" style="text-transform: lowercase" />
                      <form:errors path="email" cssClass="error"/>
                  </div>

                  <div class="form-group sizing-between">
                      <label for="firstName" class="required">
                      <spring:message code="administrator.manager.firstName"/></label>
                      <form:input path="firstName" class="form-control" style="text-transform: capitalize"/>
                      <form:errors path="firstName" cssClass="error"/>
                  </div>

                  <div class="form-group sizing-between">
                      <label for="lastname" class="required">
                      <spring:message code="administrator.manager.lastName" /></label>
                      <form:input path="lastName" class="form-control" style="text-transform: capitalize"/>
                      <form:errors path="lastName" cssClass="error"/>
                  </div>

                  <div class="form-group sizing-between">
                      <label for="phoneNumber" class="required">
                      <spring:message code="administrator.phoneNumber"/></label>
                      <form:input path="phoneNumber" type="text" class="form-control"/>
                      <form:errors path="phoneNumber" cssClass="error"/>
                  </div>

                  <div class="form-group">
                      <button type="submit" class="btn btn-raised btn-info glyphicon glyphicon-ok active"></button>
                      <button type="reset" class="btn btn-raised btn-danger glyphicon glyphicon-remove active"
                              onclick="window.location.href='/home/adm-edit-manager'"></button>
                  </div>

                </form:form>
           </td></tr>

       </table>
    </div>
</body>