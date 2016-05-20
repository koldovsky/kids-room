<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>

<div>
    <form  action="changePassword" method="post">
        <div class="form-group">
            <label for="password"><spring:message code="user.email" /></label>
            <input name="password" class="form-control" type="password"/>
            <form:errors path="password" cssClass="error"  />
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default"><spring:message code="user.save" /></button>
        </div>
    </form>
</div>

