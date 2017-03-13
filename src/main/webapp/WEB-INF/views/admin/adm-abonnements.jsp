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
                    <i class="glyphicon glyphicon-gift"></i>
                    <spring:message code="administrator.abonnements"/></strong>
                </legend>
            </th>
        </tr>
        <tr></tr>
        <tr>
            <th><strong><spring:message code="administrator.abonnement.name"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.price"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.hour"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.startDate"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.endDate"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>
        <c:forEach var="abonnement" items="${abonnementsList}">
            <tr>
            <c:if test="${abonnement.active eq true}"><tr></c:if>
            <c:if test="${abonnement.active ne true}"><tr class="tr-not-active"></c:if>
            <td class="hidden">${abonnement.id}</td>
            <td>${abonnement.name}</td>
            <td>${abonnement.price}</td>
            <td>${abonnement.hour}</td>
            <td>${abonnement.startDate}</td>
            <td>${abonnement.endDate}</td>
            <td><a href="adm-update-abonnement?id=${abonnement.id}" tabindex="-1"><button id="btn-edit"
                                                                                    class="btn btn-raised btn-info"><i class="glyphicon glyphicon-pencil"></i></button></a>
            </td>
            <td>
                <c:if test="${abonnement.active ne true}">
                    <button class="btn btn-raised btn-default delete activate activateButton"></button>
                </c:if>
                <c:if test="${abonnement.active eq true}">
                    <button class="btn btn-raised btn-default save activate deactivateButton"></button>
                </c:if>
            </td>
            </tr>
        </c:forEach>
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

<script src='${pageContext.request.contextPath}/resources/js/admin-abonnements.js'></script>
</body>
