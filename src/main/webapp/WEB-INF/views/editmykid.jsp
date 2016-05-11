<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel='stylesheet' href='resources/css/registerkid.css'>

<form:form class = "registerkid"  enctype="application/x-www-form-urlencoded"  modelAttribute="kidToEdit" action="editmykid" method="post">

      <h2>Edit Your kid&#39;s info</h2>

      <form:hidden path="id" />

      <div class="form-group">
             <label for="firstname" class="required">First Name</label>
              <form:input path="firstName" id="firstname" value="${kidToEdit.getFirstName()}" class="form-control" required="required"/>
      </div>
      <div class="form-group">
              <label for="lastname" class="required">Last Name</label>
              <form:input path="lastName" id="lastname" value="${kidToEdit.getLastName()}" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="date" class="required">Date of birth</label>
                    <form:input type="date" path="dateOfBirth" id="date" name="date" value="${kidToEdit.getDateOfBirth()}" class="form-control" required="required" />
      </div>

      <div class="form-group">
                    <label for="comment">Comments</label>
                    <form:textarea path="comment" id="comment" value="${kidToEdit.getComment()}" rows="7" cols="50" class="form-control"/>
      </div>

      <button type="submit" class="btn btn-default">Submit</button>
</form:form>