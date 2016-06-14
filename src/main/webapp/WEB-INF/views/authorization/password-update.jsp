<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ua.softserveinc.tc.constants.UserConstants" %>

<div class="col-sm-offset-4 col-xs-4">
    <form:form action="changePassword" method="post" modelAttribute="<%=UserConstants.Entity.USER %>">
        <div class="form-group">
            <label for="password" class="required"><spring:message code="user.password" /></label>
            <form:password path="password" class="form-control"/>
            <form:errors path="password" cssClass="error"  />
        </div>
        <div class="form-group">
            <label for="confirm" class="required"><spring:message code="user.confirm" /></label>
            <form:password path="confirm"  class="form-control" />
            <form:errors path="confirm" cssClass="error" />
        </div>
        <div class="col-sm-offset-4">
            <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.save" /></button>
        </div>
    </form:form>
</div>

