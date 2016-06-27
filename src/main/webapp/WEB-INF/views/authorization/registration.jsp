
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-offset-4 col-sm-3" id="reg-form">
    <form:form id="userform" action="registration" method="post"  modelAttribute="user">
      <form:input path="role" type="hidden" value="USER" />

      <div class="form-group">
              <label for="firstname" class="required"><spring:message code="user.firstname" /></label>
              <form:input path="firstName"   class="form-control" style="text-transform: capitalize" />
              <form:errors path="firstName" cssClass="error"  />
      </div>
      <div class="form-group">
              <label for="lastname" class="required"><spring:message code="user.lastname" /></label>
              <form:input path="lastName"   class="form-control" style="text-transform: capitalize"   />
              <form:errors path="lastName" cssClass="error" />
      </div>
        <div class="form-group">
                  <label for="email" class="required"><spring:message code="user.email" /></label>
                  <form:input path="email" name="email" type="email" id="userEmail" class="form-control"
                                style="text-transform: lowercase" />
                  <form:errors path="email" cssClass="error"  />
      </div>

      <div class="form-group" >
                <label for="password" class="required"><spring:message code="user.password" /></label>
                <form:password path="password" name="password" id="userPassword" class="form-control"  />
                <form:errors path="password" cssClass="error" />
      </div>
      <div class="form-group">
              <label for="confirm" class="required"><spring:message code="user.confirm" /></label>
              <form:password path="confirm" name="confirm" class="form-control"  />
              <form:errors path="confirm" cssClass="error" />
      </div>
      <div class="form-group">
              <label for="phonenumber" class="required"><spring:message code="user.phone" /></label>
              <form:input path="phoneNumber" name="phoneNumber"  id="phonenumber" type="text" class="form-control"  />
              <form:errors path="phoneNumber" cssClass="error"  />
      </div>
         <div class="form-group">
              <spring:message code="user.prerules"/> <a data-toggle="modal" data-target="#rules"> <spring:message code="user.rules" /></a>
              <div id="reg-button">
                <button type="submit" id="button" class="btn btn-primary btn-lg"><spring:message code="user.register" /></button>
              </div>
      </div>
    </form:form>
</div>

    <div class="modal fade" id="rules" tabindex="-1" role="dialog"  aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><spring:message code="user.rule"/></h4>
                </div>
                <div class="modal-body">
                    <p><spring:message code="rules"/></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="user.close"/></button>
                </div>
            </div>
        </div>
    </div>


<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
    <script src="resources/js/validation-registration-ua.js"></script>
</c:if>
<script src="resources/js/validation-registration.js"></script>

