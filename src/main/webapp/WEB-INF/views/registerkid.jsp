<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form:form class = "registerkid" enctype="application/x-www-form-urlencoded" action="registerkid" method="post" modelAttribute="child">

      <h2>Child Registration</h2>
      <form:hidden path="id" />

      <div class="form-group">
             <label for="firstname" class="required">First Name</label>
              <form:input path="firstName" id="firstname" class="form-control" required="required"/>
      </div>
      <div class="form-group">
              <label for="lastname" class="required">Last Name</label>
              <form:input path="lastName" id="lastname" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="date" class="required">Date of birth</label>
                    <form:input path="dateOfBirth" id="date" name="date" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="comment">Comments</label>
                    <form:textarea path="comment" id="comment" rows="7" cols="50" class="form-control"/>
      </div>

      <button type="submit" class="btn btn-default">Submit</button>
</form:form>