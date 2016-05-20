
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>


<div>
    <form  action="resetPassword" method="post">
        <div class="form-group">
            <label for="password"><spring:message code="user.email" /></label>
            <input name="email" class="form-control" type="email"/>
            <form:errors path="email" cssClass="error"  />
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default"><spring:message code="user.send" /></button>
        </div>
    </form>
</div>

