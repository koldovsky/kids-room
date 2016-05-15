<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Add manager</title>

	<link rel="stylesheet" type="text/css" href="resources/css/admin-new-style.css">
</head>

<body>
    <div class="for-table">
        <form action="adm-add-manager" method="post" modelAttribute="user">

                <legend class="for-field"><strong>Add manager</strong></legend>

                <div class="form-group">
                    <label class="for-field"> E-mail <input type="email" name="email" required class="form-control"/></label>
                </div>

                <div class="form-group">
                    <label class="for-field"> Password <input type="password" name="password" required class="form-control"/></label>
                </div>

                <div class="form-group">
                    <label class="for-field"> First Name <input type="text" name="firstName" required class="form-control"/></label>
                </div>

                <div class="form-group">
                    <label class="for-field"> Last Name <input type="text" name="lastName" required class="form-control"/></label>
                </div>

                <div class="form-group">
                    <label class="for-field"> Phone number <input type="number" name="phoneNumber" pattern="^[\d]{10,13}$" required class="form-control"/></label>
                </div>

                <div class="form-group">
                    <button class="for-button" type="submit" name="submit">Submit</button>
                    <button class="for-button" type="reset" name="reset" >Cancel</button>
                </div>

        </form>
    </div>
</body>
</html>