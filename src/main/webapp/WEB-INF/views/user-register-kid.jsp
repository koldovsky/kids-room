<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="ua.softserveinc.tc.constants.ModelConstants.MyKidsConst" %>
<%@ page import="ua.softserveinc.tc.constants.ValidationConst" %>

<link rel='stylesheet' href='resources/css/registerkid.css'> 

<form:form enctype="application/x-www-form-urlencoded" action="registerkid"
method="post" modelAttribute="<%=MyKidsConst.KID_ATTRIBUTE %>">

      <h2>
        <spring:message code="kid.register" />
      </h2>
      <form:hidden path="id" />

      <div class="form-group">
             <label for="firstname" class="required">
             	<spring:message code="kid.firstname" />
             </label>

              <form:input path="firstName" id="firstname" class="form-control"
              required="required" pattern="<%=ValidationConst.NAME_REGEX %>"/>

              <form:errors path="firstName" cssClass="error"  />
      </div>
      <div class="form-group">
              <label for="lastname" class="required">
              	<spring:message code="kid.lastname" />
              </label>
              <form:input path="lastName" id="lastname" class="form-control"
              required="required" pattern="<%=ValidationConst.NAME_REGEX %>"/>
              <form:errors path="lastName" cssClass="error" />
      </div>

      <div class="form-group">
                    <label for="date" class="required">
                        <spring:message code="kid.date" />
                    </label>
                    <form:input path="dateOfBirth" type="date" id="date" name="date"
                    class="form-control" required="required" />
                    <form:errors path="dateOfBirth" cssClass="error" />
      </div>

      <div class="form-group">

        <form:radiobutton path="gender" value="MALE" label="Boy" />

        <form:radiobutton path="gender" value="FEMALE" label="Girl" />

      </div>


      <div class="form-group">
                    <label for="comment">
                    	<spring:message code="kid.comment" />
                    </label>
                    <form:textarea path="comment" id="comment" rows="7" cols="50" class="form-control textarea"/>
      </div>

      <button class="btn btn-raised btn-success" type="submit" name="action">
      <spring:message code="user.submit" />
      </button>

      <footer class="bottom-msg">
      <p class="explanation"> <spring:message code="requiredfield.explanation" /> </p>
      </footer>
</form:form>