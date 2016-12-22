
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="ua.softserveinc.tc.constants.UserConstants" %>

<div class="form-group-material-blue-400">
    <div class="col-sm-offset-4 col-xs-3" id="login-form">
        <form:form action="changePassword" method="post" modelAttribute="<%=UserConstants.Entity.USER %>">
            <div class="form-group">
                <span class="glyphicon glyphicon-lock"></span>
                <label for="password" class="required"><spring:message code="user.password" /></label>
                <form:password path="password" class="form-control"/>
                <form:errors path="password" cssClass="error"  />
            </div>
            <div class="form-group">
                <span class="glyphicon glyphicon-lock"></span>
                <label for="confirm" class="required"><spring:message code="user.confirm" /></label>
                <form:password path="confirm"  class="form-control" />
                <form:errors path="confirm" cssClass="error" />
            </div>
            <div class="block-center">
                <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.save" /></button>
            </div>
        </form:form>
    </div>
</div>
