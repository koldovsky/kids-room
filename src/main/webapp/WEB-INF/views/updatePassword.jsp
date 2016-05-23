<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>

<div class="col-sm-offset-4 col-xs-4">
    <form  action="changePassword" method="post">
        <div class="form-group">
            <label for="password"><spring:message code="user.email" /></label>
            <input name="password" class="form-control" type="password"/>
            <form:errors path="password" cssClass="error"  />
        </div>
        <div class="col-sm-offset-4">
            <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.save" /></button>
        </div>
    </form>
</div>

