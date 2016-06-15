<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<div class="for-table">
    <table class="for-table-fields">
    <form:form modelAttribute="<%=AdminConstants.ATR_MANAGER%>" action="confirm-manager" method="post">

        <tr><th>
           <strong>${manager.email}<strong><br>
           <spring:message code="administrator.manager.activationAccountMessage"/>
        </th></tr>

        <form:hidden path="id"/>
        <form:hidden path="email" value="${manager.email}"/>
        <form:hidden path="firstName" value="${manager.firstName}"/>
        <form:hidden path="lastName" value="${manager.lastName}"/>
        <form:hidden path="phoneNumber" value="${manager.phoneNumber}"/>
        <form:hidden path="role" value="${manager.role}"/>
        <form:hidden path="active" value="${manager.active}"/>

        <tr><td>
            <div class="form-group">
                <label for="password" class="required">
                <spring:message code="user.password" /></label>
                <form:password path="password" id="password" class="form-control" required="required"/>
                <form:errors path="password" cssClass="error"/>
            </div>

            <div class="form-group">
                <label for="confirm" class="required">
                <spring:message code="user.confirm" /></label>
                <form:password path="confirm" class="form-control" />
                <form:errors path="confirm" cssClass="error" />
            </div>
        </td></tr>

        <tr></tr>
        <tr><td class="hide-border">
            <div class="form-group">
                <button type="submit" name="submit" class="btn btn-raised btn-primary btn-lg glyphicon glyphicon-ok"></button>
            </div>
        </td></tr>

    </form:form>
    </table>
</div>