<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url value="/j_spring_security_check" var="loginUrl" />
<div class="form-group-material-blue-400">
    <div class="col-sm-offset-4 col-xs-3" id="login-form">
        <form:form method="post" id="loginForm" >

            <div class ="successconfirmation">${confirmMessage}</div>

            <div class="form-group">
                <span class="glyphicon glyphicon-envelope"></span>
                <label for="email" ><spring:message code="user.email" /></label>
                <input id="email" type="email" name="j_username"  class="form-control"  value="manager@softserveinc.com"  />
            </div>
            <div class="form-group">
                <span class="glyphicon glyphicon-lock"></span>
                <label for="password" ><spring:message code="user.password"/></label>
                <input id="password" type="password" name="j_password" class="form-control"  value="manager"/>

            </div>
            <div class="clearfix">
                <a href="<c:url value="/resetPassword"/>" class="pull-right">
                    <spring:message code="password.reset" /></a>
            </div>
            <div>
                <c:if test="${param.error != null}">
                    <div class ="error">
                        <spring:message code="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                    </div>
                </c:if>
            </div>
            <div class="col-sm-offset-4">
                <button formaction="${loginUrl}" type="submit" class="btn btn-success btn-lg">
                    <spring:message code="user.login" /></button>
            </div>

        </form:form>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-login.js"></script>