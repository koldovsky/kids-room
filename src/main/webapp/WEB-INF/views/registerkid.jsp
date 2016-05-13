<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel='stylesheet' href='resources/css/registerkid.css'>

<form:form class = "registerkid" enctype="application/x-www-form-urlencoded" action="registerkid" method="post" modelAttribute="child">

      <h2>
        <spring:message code="kid.register" />
      </h2>
      <form:hidden path="id" />

      <div class="form-group">
             <label for="firstname" class="required">
             	<spring:message code="kid.firstname" />
             </label>
              <form:input path="firstName" id="firstname" class="form-control" required="required" pattern="^[A-Z].*"/>
      </div>
      <div class="form-group">
              <label for="lastname" class="required">
              	<spring:message code="kid.lastname" />
              </label>
              <form:input path="lastName" id="lastname" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="date" class="required">
                        <spring:message code="kid.date" />
                    </label>
                    <form:input path="dateOfBirth" type="date" id="date" name="date" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="comment">
                    	<spring:message code="kid.comment" />
                    </label>
                    <form:textarea path="comment" id="comment" rows="7" cols="50" class="form-control"/>
      </div>

      <button type="submit" class="btn btn-default"><spring:message code="user.submit" /></button>

      <footer class="bottom-msg">
      <p class="explanation"> <spring:message code="requiredfield.explanation" /> </p>
      </footer>
</form:form>