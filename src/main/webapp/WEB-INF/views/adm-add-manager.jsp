<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Add manager</title>

	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<body>
   <div class="for-table">
       <form action="adm-add-manager" method="post" modelAttribute="user">

           <legend class="for-field"><strong>Add manager</strong></legend>

           <div class="form-group">
              <label class="for-field"> E-mail
                 <input type="email" name="email" required class="form-control" pattern="^(\w){1,60}[@][s]oft[s]erveinc[.]com$"
                                                   title="Allow only emails with 'softserveinc.com' domain"/>
              </label>
           </div>

           <div class="form-group">
              <label class="for-field"> Password
                 <input type="password" name="password" pattern="^(\S){8,}$" title="Eight or more characters" required
                                                        class="form-control"/>
              </label>
           </div>

           <div class="form-group">
              <label class="for-field"> First Name <input type="text" name="firstName" required class="form-control"/></label>
           </div>

           <div class="form-group">
              <label class="for-field"> Last Name <input type="text" name="lastName" required class="form-control"/></label>
           </div>

           <div class="form-group">
              <label class="for-field"> Phone number <input type="text" name="phoneNumber" pattern="^(\+38|8|)(\W*\d){10}\W*$"
                                                            title="number not valid" required class="form-control"/></label>
           </div>

           <div class="form-group">
              <button class="glyphicon glyphicon-ok btn btn-info btn-lg" type="submit" name="submit">Submit</button>
              <button class="btn btn-info btn-lg glyphicon glyphicon-remove" type="reset" name="reset" >Cancel</button>
           </div>

        </form>
    </div>
</body>
</html>