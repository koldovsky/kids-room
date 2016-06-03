
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>


<div class="col-sm-offset-4 col-xs-4">
    <spring:message code="user.resetPass" />
    <form:form  action="resendConfirmation" method="post" modelAttribute="user">
        <div class="form-group">
            <label for="email"><spring:message code="user.email" /></label>
            <form:input path="email" class="form-control" type="email" />
            <form:errors path="email" cssClass="error"  />
        </div>
        <div class="col-sm-offset-4">
            <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.resend" /></button>
        </div>
    </form:form>
</div>

