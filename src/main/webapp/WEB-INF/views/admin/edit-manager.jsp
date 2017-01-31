<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<link rel='stylesheet' href='resources/css/flow-form.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-user"></i>
                    <spring:message code="administrator.listManagers"/></strong></legend></th>
        </tr>
        <tr></tr>

        <tr>
            <th><strong><spring:message code="administrator.manager.email"/></strong></th>
            <th><strong><spring:message code="administrator.manager.firstName"/></strong></th>
            <th><strong><spring:message code="administrator.manager.lastName"/></strong></th>
            <th><strong><spring:message code="administrator.phoneNumber"/></strong></th>
            <th><strong><spring:message code="administrator.manager.confirmedAccount"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.block"/></strong></th>
        </tr>

        <c:forEach var="manager" items="${managerList}">

            <c:if test="${manager.active eq true}"><tr></c:if>
            <c:if test="${manager.active ne true}"><tr class="tr-not-active"></c:if>
            <td class="hidden">${manager.id}</td>
            <td>${manager.email}</td>
            <td>${manager.firstName}</td>
            <td>${manager.lastName}</td>
            <td>${manager.phoneNumber}</td>
            <td>
                <c:if test="${manager.confirmed eq true}"><i class="admin-icon-confirm glyphicon glyphicon-ok-circle"></i></c:if>
                <c:if test="${manager.confirmed ne true}"><i class="admin-icon-notconfirm glyphicon glyphicon-remove-circle"></i></c:if>
            </td>
            <td><a href="adm-update-manager?id=${manager.id}" tabindex="-1"><button id="btn-edit"
                    class="btn btn-raised btn-info"><i class="glyphicon glyphicon-pencil"></i></button></a>
            </td>

            <td>
                <c:if test="${manager.active ne true}">
                    <button class="btn btn-raised btn-default delete activate activateButton"></button>
                </c:if>
                <c:if test="${manager.active eq true}">
                    <button class="btn btn-raised btn-default save activate deactivateButton"></button>
                </c:if>
            </td>
        </tr>
        </c:forEach>

        <tr></tr>
        <tr>
            <th colspan="7" class="hide-border set-standard-color">
                <a href="adm-add-manager" tabindex="-1">
                    <button type="button" class="btn btn-raised btn-primary btn-add-manager">
                            <spring:message code="administrator.add"/></button></a>
            </th>
        </tr>
    </table>

    <%--confirmation-dialog--%>
    <div class="row hidden">
            <div align="center">
                <div id="confirmation-dialog-event-div" class="ui-dialog" title=<spring:message
                        code="event.confirmTitle" /> >
                    <form id="confirm-your-choice-event">
                        <div class= "confirmManager">
                            <p id="inactive-manager-span" hidden><span style="text-align:center; color:red;" >
                                <spring:message code= "manager.inactive.confirm"/></span></p>
                            <p id="active-manager-span" hidden><span style="text-align:center; color:red;" >
                                <spring:message code= "manager.active.confirm"/></span></p>
                        </div>
                        <div class="col-xs-12">
                            <div class="col-xs-6" style="text-align:center;">
                                <button type="button" class="btn btn-success btn-block" id="confirmYesEvent">
                                    <spring:message code="event.confirmYes"/></button>
                            </div>
                            <div class="col-xs-6" style="text-align:center;">
                                <button type="button" class="btn btn-danger btn-block" id="confirmNoEvent">
                                    <spring:message code="event.confirmNo"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
    </div>
</div>

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/edit-manager-confirmation.js'></script>

</body>
