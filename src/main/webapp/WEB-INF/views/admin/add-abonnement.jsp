<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<body>
<div class="for-table">
    <table class="col-sm-offset-4 col-sm-3 reg-form">
        <tr>
            <th>
                <strong class="title-font"><spring:message code="administrator.addAbonnement"/></strong>
            </th>
        </tr>

        <tr>
            <td>
                <form:form modelAttribute="<%=AdminConstants.CREATE_ABONNEMENT%>" action="adm-add-abonnement"
                           method="post"
                           id="managerForm">

                    <div class="form-group sizing-between">
                        <label for="name" class="required"><spring:message code="administrator.abonnement.name"/></label>
                        <form:input path="name" class="form-control"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="price" class="required"><spring:message
                                code="administrator.abonnement.price"/></label>
                        <form:input path="price" class="form-control"/>
                        <form:errors path="price" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="hour" class="required"><spring:message
                                code="administrator.abonnement.hour"/></label>
                        <form:input path="hour" class="form-control"/>
                        <form:errors path="hour" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="startDate" class="required">
                            <spring:message code="administrator.abonnement.startDate"/></label>
                        <form:input path="startDate" class="form-control"/>
                        <form:errors path="startDate" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="endDate" class="required">
                            <spring:message code="administrator.abonnement.startDate"/></label>
                        <form:input path="endDate" class="form-control"/>
                        <form:errors path="endDate" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <button type="submit" class="btn btn-raised btn-success"
                                onclick="window.location.href='adm-abonnements'"><spring:message
                                code="administrator.save"/></button>
                        <button type="reset" class="btn btn-raised btn-danger"
                                onclick="window.location.href='adm-abonnements'"><spring:message
                                code="administrator.canc"/></button>
                    </div>
                </form:form>
            </td>
        </tr>
    </table>
</div>
</body>

<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-manager.js"></script>