<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/bookings.css">


<div class="col-sm-offset-4 col-sm-3">
    <form:form action="confirm-manager" method="post" modelAttribute="user">

      <div class="form-group" >
                <label for="password" class="required"><spring:message code="user.password" /></label>
                <form:password path="password" id="userPassword" class="form-control" />
                <form:errors path="password" cssClass="error" />
      </div>
      <div class="form-group">
              <label for="confirm" class="required"><spring:message code="user.confirm" /></label>
              <form:password path="confirm"  class="form-control" />
              <form:errors path="confirm" cssClass="error" />
      </div>

                <button type="submit" id="button" class="btn btn-primary btn-lg"><spring:message code="user.register" /></button>
    </form:form>
</div>