<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url value="/j_spring_security_check" var="loginUrl" />
<div class="col-sm-offset-4 col-xs-3">
    <form method="post" >
        <div class="form-group">
            <label for="email" ><spring:message code="user.email" /></label>
            <input type="email" name="j_username"  class="form-control"  value="manager@softserveinc.com" required  />
        </div>
        <div class="form-group">
            <label for="password" ><spring:message code="user.password"/></label>
            <input type="password" name="j_password" class="form-control" required value="manager"/>

        </div>
        <div class="clearfix">
            <label class="pull-left">
                <input type="checkbox" name="remember-me"><spring:message code="user.remember" />
            </label>
            <a href="<c:url value="/resetPassword"/>" class="pull-right"> <spring:message code="password.reset" /></a>
        </div>
        <div>
            <c:if test="${param.error != null}">
                <div class ="error">
                    <spring:message code="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                </div>
            </c:if>
        </div>
        <div class="col-sm-offset-4">
            <button formaction="${loginUrl}" type="submit" class="btn btn-primary btn-lg"><spring:message code="user.login" /></button>
        </div>
    </form>

</div>


