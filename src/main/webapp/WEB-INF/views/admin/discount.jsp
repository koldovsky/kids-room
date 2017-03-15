<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-calendar"></i>
                    <spring:message code="administrator.dayDiscount"/></strong>
                </legend>
            </th>
        </tr>
        <tr></tr>
        <tr>
            <th><strong><spring:message code="administrator.dayDiscount.reason"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.value"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.startDate"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.endDate"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>

        <tr>
            <th colspan="10" class="hide-border set-standard-color">
                <a href="adm-add-abonnement" tabindex="-1">
                    <button type="button" class="btn btn-raised btn-primary btn-add-room">
                        <spring:message code="administrator.add"/>
                    </button>
                </a>
            </th>
        </tr>
    </table>
</div>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-user"></i>
                    <spring:message code="administrator.personalDiscount"/></strong>
                </legend>
            </th>
        </tr>
        <tr></tr>
        <tr>
            <th><strong><spring:message code="administrator.personalDiscount.username"/></strong></th>
            <th><strong><spring:message code="administrator.personalDiscount.value"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>

        <tr>
            <th colspan="10" class="hide-border set-standard-color">
                <a href="adm-add-abonnement" tabindex="-1">
                    <button type="button" class="btn btn-raised btn-primary btn-add-room">
                        <spring:message code="administrator.add"/>
                    </button>
                </a>
            </th>
        </tr>
    </table>
</div>

</body>
