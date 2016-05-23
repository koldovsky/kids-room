<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url value="/j_spring_security_check" var="loginUrl" />
<div class="col-sm-offset-4 col-xs-3">
    <form  action="${loginUrl}" method="post">
        <div class="form-group">
            <label for="email" ><spring:message code="user.email" /></label>
            <input type="email" name="j_username"  class="form-control"  value="manager@softserveinc.com" required/>
        </div>
        <div class="form-group">
            <label for="password" ><spring:message code="user.password" /></label>
            <input type="password" name="j_password" class="form-control"  required value="manager"/>
            <div id="right">
            <a href="<c:url value="/resetPassword"/>" > <spring:message code="password.reset" /></a>
            </div>
        </div>
        <div>
            <label>
                <input type="checkbox" name="remember-me"><spring:message code="user.remember" />
            </label>
        </div>
        <div class="col-sm-offset-4">
            <c:if test="${param.error != null}">
                <div class ="error">
                    <spring:message code="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                </div>
            </c:if>

            <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.login" /></button>
        </div>
    </form>
</div>
