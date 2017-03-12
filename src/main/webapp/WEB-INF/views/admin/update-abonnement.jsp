<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<div class="for-table">
    <table class="col-sm-offset-4 col-sm-3 reg-form">

        <tr>
            <th>
                <strong class="title-font"><spring:message code="administrator.updateAbonnement"/></strong>
            </th>
        </tr>

        <tr>
            <td>
                <form:form modelAttribute="<%=AdminConstants.ATR_ABONNEMENT%>" action="adm-update-abonnement" method="post"
                           id="managerForm">

                <div class="form-group sizing-between">
                    <label for="name" class="required"><spring:message code="administrator.abonnement.name"/></label>
                    <form:input path="name" value="${abonnement.name}" class="form-control"/>
                    <form:errors path="name" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="price" class="required"><spring:message
                            code="administrator.abonnement.price"/></label>
                    <form:input path="price" value="${abonnement.price}" class="form-control"/>
                    <form:errors path="price" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="hour" class="required"><spring:message
                            code="administrator.abonnement.hour"/></label>
                    <form:input path="hour" value="${abonnement.hour}" class="form-control"/>
                    <form:errors path="hour" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="startDate" class="required">
                        <spring:message code="administrator.abonnement.startDate"/></label>
                    <form:input path="startDate" value="${abonnement.startDate}" class="form-control"/>
                    <form:errors path="startDate" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="endDate" class="required">
                        <spring:message code="administrator.abonnement.startDate"/></label>
                    <form:input path="endDate" value="${abonnement.endDate}" class="form-control"/>
                    <form:errors path="endDate" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <button type="submit" class="btn btn-raised btn-success"><a href="adm-abonnements"><spring:message
                            code="administrator.save"/></a></button>
                    <button type="reset" class="btn btn-raised btn-danger"
                            onclick="window.location.href='adm-abonnements'"><spring:message
                            code="administrator.canc"/></button>
                </div>
                </form:form>
            </th></tr>

    </table>
</div>