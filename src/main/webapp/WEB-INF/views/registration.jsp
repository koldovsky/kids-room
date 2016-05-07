<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-sm-2">
    <form:form action="registration" method="post" modelAttribute="user">

      <form:hidden path="id" />
      <form:input path="role" type="hidden" value="USER" />
      <div class="form-group">
        <label for="email">Email address</label>
        <form:input path="email" type="email" id="email" class="form-control" style="text-transform: lowercase" required="required" />
      </div>
      <div class="form-group">
              <label for="firstname">First Name</label>
              <form:input path="firstName"  id="firstname" class="form-control" required="required"/>
      </div>
      <div class="form-group">
              <label for="lastname">Last Name</label>
              <form:input path="lastName"  id="lastname" class="form-control" required="required" />
      </div>
      <div class="form-group">
              <label for="phonenumber">Phone number</label>
              <form:input path="phoneNumber"  id="phonenumber" type="number" class="form-control"  required="required"/>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <form:password path="password" id="password" class="form-control" required="required" />
      </div>
      <div class="form-group">
          <label for="confirm">Confirm password</label>
          <form:password path="" id="confirm" class="form-control" required="required" />
      </div>

      <button type="submit" class="btn btn-default">Submit</button>
    </form:form>
</div>