<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Edit location</title>
	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
	<link rel='stylesheet' href='resources/css/default.css'>
</head>

<body>
    <div class="rightsidebar" >
        <form action="adm-add-location" method="post" modelAttribute="city">
            <fieldset >
                <legend>General</legend>
                <label>
                    Room name
                    <input type="text" name="name" required ></input>
                </label>

                <button type="submit" name="submit">Submit</button>
                <button type="reset" name="reset" >Cancel</button>
            </fieldset>
        </form>
    </div>
</body>

