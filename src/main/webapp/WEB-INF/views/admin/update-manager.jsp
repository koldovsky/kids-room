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
            <strong class="title-font"><spring:message code="administrator.updateManager"/></strong>
        </th></tr>

        <tr><td>
        <form:form modelAttribute="<%=AdminConstants.ATR_MANAGER%>" action="adm-update-manager" method="post"
                   id="managerForm">

            <form:hidden path="id" />
            <form:hidden path="password" value="${manager.password}"/>
            <form:hidden path="active" value="${manager.active}"/>
            <form:hidden path="confirmed" value="${manager.confirmed}"/>

            <div class="form-group sizing-between">
               <label for="email" class="required">
                  <spring:message code="administrator.manager.email"/></label>
                  <form:input path="email" value="${manager.email}" class="form-control" readonly="true"/>
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
                <button type="submit" class="button button-confirm">OK</button>
                <button type="reset" class="button button-cancel"
                        onclick="window.location.href='adm-edit-manager'">Cancel</button>
            </div>

         </form:form>
        </th></tr>

    </table>
</div>

<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
</c:if>
<script src="resources/js/validation-manager.js"></script>