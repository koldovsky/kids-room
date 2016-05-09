


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Edit location</title>
	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">

	<link rel='stylesheet' href='resources/css/default.css'>


</head>

<div class="rightsidebar">
	<form action="adm-add-manager" method="post" modelAttribute="user">
		<fieldset >
			<legend>General</legend>
			<label >
				E-mail
				<input type="email" name="email" required ></input>
			</label>

			<label >
				Password
				<input type="password" name="password" required></input>
			</label>

			<label>
				First Name
				<input type="text" name="firstName" required></input>
			</label>

			<label >
				Last Name
				<input type="text" name="lastName" required></input>
			</label>

			<label >
             	Phone number
                input type="number" name="phoneNumber" required></input>
            </label>

            <button type="submit" name="submit">Submit</button>
			<button type="reset" name="reset" >Cancel</button>

		</fieldset>

	</form>
</div>