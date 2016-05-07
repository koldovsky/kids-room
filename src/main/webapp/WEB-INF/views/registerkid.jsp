<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="registerKidsUrl" />

<div>
<form class="registerkid">
<h2 align="center">Registration Form</h2>

<label class="required">First name</label>
<label> <input type="text" required> </label>

<label class="required">Last name</label>
<label> <input type="text" required> </label>

<label class="required">Date of birth</label>
<label> <input type="date" required></label>

<label>Comments</label>
<textarea rows="7" cols="50">
</textarea>
</div>
<div style="text-align:center;">
<input id="submit" type="submit" value="Submit"></input>
</div>
<div id="foot">
   <p>* - mandatory fields</p>
</div>
</form>
</div>