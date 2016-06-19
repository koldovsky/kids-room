<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<div class="for-table">
    <table class="for-table-fields">
    <form:form modelAttribute="<%=AdminConstants.ATR_MANAGER%>" action="adm-update-manager" method="post">

        <tr><th>
            <legend class="for-field"><strong><spring:message code="administrator.updateManager"/></strong></legend>
        </th></tr>

        <tr><td>
            <form:hidden path="id" />
            <form:hidden path="password" value="${manager.password}"/>
            <form:hidden path="active" value="${manager.active}"/>
            <form:hidden path="confirmed" value="${manager.confirmed}"/>

            <div class="form-group sizing-between">
               <label for="email" class="required">
                  <spring:message code="administrator.manager.email"/></label>
                  <form:input path="email" value="${manager.email}" class="form-control" />
                  <form:errors path="email" cssClass="error"/>
            </div>

            <div class="form-group sizing-between">
               <label for="firstName" class="required">
                  <spring:message code="administrator.manager.firstName"/></label>
                  <form:input path="firstName" value="${manager.firstName}" class="form-control"/>
                  <form:errors path="firstName" cssClass="error"/>
            </div>

            <div class="form-group sizing-between">
               <label for="lastName" class="required">
                  <spring:message code="administrator.manager.lastName"/></label>
                  <form:input path="lastName" value="${manager.lastName}" class="form-control"/>
                  <form:errors path="lastName" cssClass="error" />
            </div>

            <div class="form-group sizing-between">
               <label for="phoneNumber" class="required">
                  <spring:message code="administrator.phoneNumber"/></label>
                  <form:input path="phoneNumber" value="${manager.phoneNumber}"
                              pattern="^(\+38|8|)(\W*\d){10}\W*$" class="form-control"/>
                  <form:errors path="phoneNumber" cssClass="error"  />
            </div>

            <div class="form-group sizing-between">
                <button type="submit" class="btn btn-raised btn-info glyphicon glyphicon-ok active"></button>
                <button type="reset" class="btn btn-raised btn-danger glyphicon glyphicon-remove active"
                        onclick="window.location.href='/home/adm-edit-manager'"></button>
            </div>
        </th></tr>

    </form:form>
    </table>
</div>
