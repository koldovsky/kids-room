<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="ua.softserveinc.tc.constants.ChildConstants" %>
<%@ page import="ua.softserveinc.tc.constants.ValidationConstants" %>

<link rel='stylesheet' href='resources/css/registerkid.css'>
<script src="resources/js/comment-box.js"></script>
<script src="resources/js/edit-my-kid.js"></script>
<script scr="resources/js/jquery.min.js"></script>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />


<form:form class = "registerkid"  enctype="application/x-www-form-urlencoded" id = "editkidform"
modelAttribute="<%=ChildConstants.View.KID_ATTRIBUTE %>" action="editmykid" method="post">

      <img class="icon-png" src="resources/img/edit.png" />
      <h2><spring:message code="editkid.title" /></h2>

      <form:hidden path="id" />
<div class="form-group-material-blue-400">
      <div class="form-group">
             <label for="firstName" class="required"><spring:message code="kid.firstname" /></label>
              <form:input path="firstName" id="firstName" value="${kid.getFirstName()}"
              class="form-control" required="required"/>
              <form:errors path="firstName" cssClass="error"  />
      </div>
      <div class="form-group">
              <label for="lastName" class="required"><spring:message code="kid.lastname" /></label>
              <form:input path="lastName" id="lastName" value="${kid.getLastName()}" class="form-control" required="required" />
              <form:errors path="lastName" cssClass="error" />
      </div>

      <div class="form-group">
                    <label for="date" class="required"><spring:message code="kid.date" /></label>
                    <form:input type="date" path="dateOfBirth" id="date" name="date"
                    value="${kid.getDateOfBirth()}" class="form-control" required="required" />
                     <div class="err-msg">
                              <form:errors path="dateOfBirth" cssClass="error">
                                    <spring:message code="registration.kid.date"
                                    arguments="${config.kidsMinAge}, ${config.kidsMaxAge}" />
                              </form:errors>
                      </div>
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
                    <form:textarea path="comment"
                    value="${kid.getComment()}" rows="1" class="form-control common comment"/>
          <div class="err-msg">
              <form:errors path="comment" cssClass="error">
                  <spring:message code="registration.kid.comment" />
              </form:errors>
          </div>
      </div>

      <button class="btn btn-raised btn-success" type="submit" name="action">
        <spring:message code="user.submit" />
      </button>
      <button class="btn btn-raised btn-danger" type="reset" id="edit-kid-cancel">
        <spring:message code="cancel" />
      </button>


    <footer class="removekidmsg">
      <p class="explanation"> <spring:message code="requiredfield.explanation" /> </p>
       <spring:message code="editkid.deactivatekid1" />
      <div id="removeKids" style="text-decoration: underline">
          <spring:message code="editkid.deactivatekid2"/>
       </div>
      </footer>
    </div>
</form:form>

    <%--confirmation-dialog--%>
    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="confirmation-dialog-div" class="ui-dialog"  title=<spring:message code= "editkid.confirmationtitle" /> hidden>
                    <form id="confirm-your-choice" class="confirm-form">
                        <div class= "confirmDelete">
                            <p><span style="text-align:center;" >
                                <spring:message code="editkid.confirmdeactivatekid1"/> </span> </p>
                            <p><span style="text-align:center;" >
                                <spring:message code="editkid.confirmdeactivatekid2"/> </span> </p>
                        </div>
                        <div class="col-xs-12">
                            <div class="col-xs-6" style = "text-align: center">
                                <button type="button"  class="btn btn-success" id="confirmYes">
                                    <spring:message code= "editkid.deactivateYes"/>
                                </button>
                            </div>
                            <div class = col-xs-6" style = "text-align: center">
                                <button type="button" class="btn btn-danger" id="confirmNo">
                                    <spring:message code= "editkid.deactivateNo"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script src="resources/js/validation-edit-kid.js"></script>
<c:choose>
    <c:when test="${pageContext.response.locale=='ua'}">
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
        <script src="resources/js/lib/messages-ua.js"></script>
    </c:when>
    <c:when test="${pageContext.response.locale!= 'ua'}">
        <script src="resources/js/lib/messages.js"></script>
    </c:when>
</c:choose>
