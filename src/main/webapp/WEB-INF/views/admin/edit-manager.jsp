<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/button-styles.css">


<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
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
            <td>${manager.email}</td>
            <td>${manager.firstName}</td>
            <td>${manager.lastName}</td>
            <td>${manager.phoneNumber}</td>
            <td>
                <c:if test="${manager.confirmed eq true}"><img src="resources/img/ok.png" class="img-size"></c:if>
                <c:if test="${manager.confirmed ne true}"><img src="resources/img/no.png" class="img-size"></c:if>
            </td>
            <td><a href="adm-update-manager?id=${manager.id}"><button class="button-size-default button edit"></button>
                </a></td>

            <td>
                <c:if test="${manager.active ne true}">
                    <c:url var="lockUrl" value="/adm-edit-manager?id=${manager.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                        <input id="manager" name="manager" type="hidden" value="${manager.id}" />
                        <button type="submit" value="lock"
                                onClick="return confirm('Are you sure you want to make the manager active?')"
                                class="button-size-default button delete"></button>
                    </form:form>
                </c:if>
                <c:if test="${manager.active eq true}">
                    <c:url var="lockUrl" value="/adm-edit-manager?id=${manager.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                        <input id="manager" name="manager" type="hidden" value="${manager.id}" />
                        <button type="submit" value="lock"
                                onClick="return confirm('Are you sure you want to make the manager inactive?')"
                                class="button-size-default button save"></button>
                    </form:form>
                </c:if>
            </td>
        </tr>
        </c:forEach>

        <tr></tr>
        <tr>
            <th colspan="7" class="hide-border set-standard-color">
                <a href="adm-add-manager"><input type="button" value=<spring:message code="administrator.add"/>
                   class="button-add button"/></a>
            </th>
        </tr>
    </table>
</div>
</body>