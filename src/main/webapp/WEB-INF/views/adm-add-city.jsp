<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit city</title>
<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">

</head>

<body>
   <div class="for-table">
       <table class="for-table-fields">
            <form class="for-table" action="adm-add-city" method="post" modelAttribute="city">
            <tr><th>
                    <legend class="for-field"><strong>Add city</strong></legend>
            </th></tr>

            <tr><td>

                    <div class="form-group">
                        <label class="for-field"> City name <input type="text" name="nameCity" required class="form-control"/></label>
                    </div>

                    <div class="form-group">
                        <button class="glyphicon glyphicon-ok btn btn-info btn-lg" type="submit" name="submit"></button>
                        <button class="btn btn-info btn-lg glyphicon glyphicon-remove" type="reset" name="reset" ></button>
                    </div>
            </td></tr>
            </form>
       </table>
    </div>
</body>
</html>