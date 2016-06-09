<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="ua.softserveinc.tc.constants.ModelConstants.MyKidsConst" %>
<%@ page import="ua.softserveinc.tc.constants.ValidationConst" %>

<link rel='stylesheet' href='resources/css/registerkid.css'>

<form:form class = "registerkid"  enctype="application/x-www-form-urlencoded"
modelAttribute="<%=MyKidsConst.KID_ATTRIBUTE %>" action="editmykid" method="post">

      <h2><spring:message code="editkid.title" /></h2>

      <form:hidden path="id" />

      <div class="form-group">
             <label for="firstname" class="required"><spring:message code="kid.firstname" /></label>
              <form:input path="firstName" id="firstname" value="${kid.getFirstName()}"
              class="primary-color form-control" required="required"/>
              <form:errors path="firstName" cssClass="error"  />
      </div>
      <div class="form-group">
              <label for="lastname" class="required"><spring:message code="kid.lastname" /></label>
              <form:input path="lastName" id="lastname" value="${kid.getLastName()}"
              class="form-control" required="required" />
              <form:errors path="lastName" cssClass="error" />
      </div>

      <div class="form-group">
                    <label for="date" class="required"><spring:message code="kid.date" /></label>
                    <form:input type="date" path="dateOfBirth" id="date" name="date"
                    value="${kid.getDateOfBirth()}" class="form-control" required="required" />
                    <form:errors path="dateOfBirth" cssClass="error" />
      </div>

      <div class="form-group">
              <label class="required"><spring:message code="kid.gender" /></label>
              <table>
              <tr>
              <td>
              <form:radiobutton required="required" name="Boy" path="gender" value="MALE"/><spring:message code="kid.boy" /></td>
              <td>
              <form:radiobutton required="required" path="gender" value="FEMALE" /><spring:message code="kid.girl" /></td>
              </tr>
              </table>

      </div>

      <div class="form-group">
                    <label for="comment"><spring:message code="kid.comment" /></label>
                    <form:textarea path="comment" id="comment"
                    value="${kid.getComment()}" rows="7" cols="50" class="form-control"/>
      </div>

      <button class="btn btn-raised btn-success" type="submit" name="action">
        <spring:message code="user.submit" />
      </button>

      <footer class="removekidmsg">
      <spring:message code="editkid.footmsg1" />
      <a href="removemykid?id=${kid.getId()}">
      <spring:message code="editkid.footmsg2" />
      </a>
      </footer>

</form:form>