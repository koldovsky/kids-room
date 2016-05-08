<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="col-sm-2">
    <form:form action="registerkid" method="post" modelAttribute="child">

      <form:hidden path="id" />
      <div class="form-group">

      <div class="form-group">
             <label for="firstname" class="required">First Name</label>
              <form:input path="firstName" id="firstname" class="form-control" required="required"/>
      </div>
      <div class="form-group">
              <label for="lastname" class="required">Last Name</label>
              <form:input path="lastName" id="lastname" class="form-control" required="required" />
      </div>

      <button type="submit" class="btn btn-default">Submit</button>
</form:form>
</div>