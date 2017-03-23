<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>--%>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" href='${pageContext.request.contextPath}/resources/css/lib/jquery-ui.css'>

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/flow-form.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-add-manager.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">

<div class="loading" hidden>Loading&#8230;</div>
<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-user"></i>
                    <spring:message code="administrator.listManagers"/></strong></legend>
            </th>
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
                <c:if test="${manager.confirmed eq true}"><i
                        class="admin-icon-confirm glyphicon glyphicon-ok-circle"></i></c:if>
                <c:if test="${manager.confirmed ne true}"><i
                        class="admin-icon-notconfirm glyphicon glyphicon-remove-circle"></i></c:if>
            </td>
            <td>
                <a href="#" tabindex="-1">
                    <button id="adminManagerUpdateButton" type="button" class="btn btn-raised btn-info"
                            onclick="showDialogToUpdateManager('${manager.id}')">
                        <i class="glyphicon glyphicon-pencil"></i></button>
                </a>
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
                <a href="#" tabindex="-1">
                    <button type="button" class="btn btn-raised btn-primary btn-add-manager"
                            onclick="showDialogToAddManager()">
                        <spring:message code="administrator.add"/></button>
                </a>
            </th>
        </tr>
    </table>

    <%--confirmation-dialog--%>
    <div id="confirmation-activate" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body text-center">
                    <div class="lead messageActive">
                        <p id="inactive-manager-span" hidden>
                            <span><spring:message code="manager.inactive.confirm"/></span></p>
                        <p id="active-manager-span" hidden>
                            <span><spring:message code="manager.active.confirm"/></span></p>
                    </div>
                    <button id="confirmYesEvent" class="btn  btn-success admWarningBtn">
                        <spring:message code="yes"/>
                    </button>
                    <button id="confirmNoEvent" class="btn btn-danger admWarningBtn" data-dismiss="modal">
                        <spring:message code="no"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row">
    <div class="vertical-center-row">
        <div align="center">
            <div id="manager-dialog" class="dialog" hidden title="<spring:message code= "administrator.addManager"/>">
                <form action="adm-add-manager" method="post" modelAttribute="<%=AdminConstants.ATR_MANAGER%>"
                      id="managerForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <input path="role" type="hidden" value="MANAGER"/>
                    <input path="password" type="hidden" name="password"/>

                     <div class="form-group  sizing-between">
                        <label for="new-manager-email" class="required"><spring:message
                                code="administrator.manager.email"/></label>
                        <spring:message code="administrator.manager.emailPlaceHolder" var="emailPlaceHolder"/>
                        <input id="new-manager-email" name="email" path="email" type="email" class="form-control"
                               style="text-transform: lowercase" placeholder="${emailPlaceHolder}">
                        <errors path="email" cssClass="error"/>
                    </div>

                     <div class="form-group  sizing-between">
                        <label for="new-manager-firstName" class="required"><spring:message
                                code="administrator.manager.firstName"/></label>
                        <spring:message code="administrator.manager.firstNamePlaceHolder" var="firstNamePlaceHolder"/>
                        <input id="new-manager-firstName" name="firstName" path="firstName" class="form-control"
                               style="text-transform: capitalize" placeholder="${firstNamePlaceHolder}">
                        <errors path="firstName" cssClass="error"/>
                    </div>


                    <div class="form-group  sizing-between">
                        <label for="new-manager-lastName" class="required"><spring:message
                                code="administrator.manager.lastName"/></label>
                        <spring:message code="administrator.manager.lastNamePlaceHolder" var="lastNamePlaceHolder"/>
                        <input id="new-manager-lastName" name="lastName" path="lastName" class="form-control"
                               style="text-transform: capitalize" placeholder="${lastNamePlaceHolder}">
                        <errors path="lastName" cssClass="error"/>
                    </div>

                    <div class="form-group  sizing-between">
                        <label for="new-manager-phoneNumber" class="required"><spring:message
                                code="administrator.phoneNumber"/></label>
                        <spring:message code="administrator.phoneNumberPlaceHolder" var="phoneNumberPlaceHolder"/>
                        <input id="new-manager-phoneNumber" name="phoneNumber" path="phoneNumber" type="text" class="form-control"
                               placeholder="${phoneNumberPlaceHolder}">
                        <errors path="phoneNumber" cssClass="error"/>
                    </div>

                </form>
                <br>
                <div class="clearfix"></div>
                <div class="col-xs-3">
                    <button type="button" class="btn btn-success" id="update-recurrent-button"
                            hidden="true">
                        <spring:message code="administrator.edit"/>
                    </button>
                    <button type="submit" class="btn btn-success live" id="create-button">
                        <spring:message code="administrator.submit"/></button>
                </div>
                <div align="right" class="col-xs-9">
                    <button type="reset" class="btn btn-danger pull-right"  id="cancel">
                        <spring:message code="administrator.canc"/></button>

                </div>
                <br>
                <div class="col-xs-12">
                    <div id="resultVind"></div>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="vertical-center-row">
        <div align="center">
            <div id="updating-dialog" class="dialog" hidden
                 title="<spring:message code= "administrator.updateManager"/>">
                <form id="managerUpdateForm" modelAttribute="<%=AdminConstants.ATR_MANAGER%>">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


                    <input path="role" type="hidden" value="MANAGER"/>
                    <input path="id" id="manager-id" type="hidden" name="password"/>

                    <div class="form-group">
                        <label for="new-manager-emailUpdate"><spring:message
                                code="administrator.manager.email"/></label>
                        <spring:message code="administrator.manager.emailPlaceHolder" var="emailPlaceHolder"/>
                        <input id="new-manager-emailUpdate" name="email" path="email" type="email" class="form-control"
                               style="text-transform: lowercase" placeholder="${emailPlaceHolder}" readonly="true">
                        <errors path="email" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label for="new-manager-firstNameUpdate"><spring:message
                                code="administrator.manager.firstName"/></label>
                        <spring:message code="administrator.manager.firstNamePlaceHolder" var="firstNamePlaceHolder"/>
                        <input id="new-manager-firstNameUpdate" name="firstName" path="firstName" class="form-control"
                               style="text-transform: capitalize" placeholder="${firstNamePlaceHolder}">
                        <errors path="firstName" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="new-manager-lastNameUpdate"><spring:message
                                code="administrator.manager.lastName"/></label>
                        <spring:message code="administrator.manager.lastNamePlaceHolder" var="lastNamePlaceHolder"/>
                        <input id="new-manager-lastNameUpdate" name="lastName" path="lastName" class="form-control"
                               style="text-transform: capitalize" placeholder="${lastNamePlaceHolder}">
                        <errors path="lastName" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="new-manager-phoneNumberUpdate"><spring:message
                                code="administrator.phoneNumber"/></label>
                        <spring:message code="administrator.phoneNumberPlaceHolder" var="phoneNumberPlaceHolder"/>
                        <input id="new-manager-phoneNumberUpdate" name="phoneNumber" path="phoneNumber" type="text" class="form-control"
                               placeholder="${phoneNumberPlaceHolder}">
                        <errors path="phoneNumber" cssClass="error"/>
                    </div>


                    <br>
                    <div class="clearfix"></div>
                    <div class="col-xs-3">
                        <button type="button"  class="btn btn-success live" id="updatingButton"
                                hidden="true">
                            <spring:message code="administrator.submit"/>
                        </button>
                    </div>
                    <div align="right" class="col-xs-9">
                        <button type="button" class="btn btn-danger pull-right" id="cancel-update">
                            <spring:message code="administrator.canc"/></button>

                    </div>
                    <br>
                    <div class="col-xs-12">
                        <div id="resultVindUpdate"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>

<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-manager.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/admin-edit-manager.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/admin-add-manager.js'></script>

</body>
