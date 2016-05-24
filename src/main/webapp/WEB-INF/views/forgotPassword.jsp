
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>


<div class="col-sm-offset-4 col-xs-3">
    <spring:message code="user.resetPass" />
    <form  action="resetPassword" method="post">
        <div class="form-group">
            <label for="password"><spring:message code="user.email" /></label>
            <input name="email" class="form-control" type="email"/>
            <form:errors path="email" cssClass="error"  />
        </div>
        <c:if test="${message}">
        <div>
           <p class="error"><spring:message code="user.with" />${email}<spring:message code="user.notExist" /><p>
        </div>
        </c:if>
        <div class="col-sm-offset-4">
            <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.send" /></button>
        </div>
    </form>
</div>

