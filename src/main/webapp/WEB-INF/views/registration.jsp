<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-2">
    <form:form id="userform" action="registration" method="post" modelAttribute="user">
      <form:input path="role" type="hidden" value="USER" />

      <div class="form-group">
              <label for="firstname" class="required">First Name</label>
              <form:input path="firstName"  id="firstname" class="form-control" required="required"/>
              <form:errors path="firstName" cssClass="error"  />
      </div>
      <div class="form-group">
              <label for="lastname" class="required">Last Name</label>
              <form:input path="lastName"  id="lastname" class="form-control" required="required" />
              <form:errors path="lastName" cssClass="error" />
      </div>
      <div class="form-group">
                  <label for="email" class="required">Email address</label>
                  <form:input path="email" type="email" id="email" class="form-control" style="text-transform: lowercase" required="required" />
                  <form:errors path="email" cssClass="error"  />
                  <span id="validEmail"></span>
      </div>

      <div class="form-group" >
                <label for="password" class="required">Password</label>
                <form:password path="password" id="password" class="form-control" required="required" />
                <form:errors path="password" cssClass="error" />
      </div>
      <div class="form-group">
              <label for="confirm" class="required">Confirm password</label>
              <form:password path="" id="confirm" class="form-control" required="required" />
              <span id="validPassword"></span>
      </div>
      <div class="form-group">
              <label for="phonenumber" class="required">Phone number</label>
              <form:input path="phoneNumber"  id="phonenumber" type="number" class="form-control"  required="required"/>
              <form:errors path="phoneNumber" cssClass="error"  />
      </div>
      <button type="submit" id="button" class="btn btn-default">Submit</button>
    </form:form>
</div>
