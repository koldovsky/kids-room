<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit city</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-new-style.css">

</head>

<body>
    <div class="for-table">

            <form class="for-table" action="adm-add-city" method="post" modelAttribute="city">

                    <legend class="for-field"><strong>Add city</strong></legend>

                    <div class="form-group">
                        <label class="for-field"> City name <input type="text" name="nameCity" required class="form-control"/></label>
                    </div>

                    <div class="form-group">
                        <button class="for-button" type="submit" name="submit">Submit</button>
                        <button class="for-button" type="reset" name="reset" >Cancel</button>
                    </div>
            <form>
    </div>
</body>
</html>