<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New or Edit User</title>
</head>
<body>
	<div align="center">
		<h1>New/Edit User</h1>
		<table>
			<form:form action="save" method="post" modelAttribute="user">
				<form:hidden path="id" />
				
				<tr>
					<td>Email:</td>
					<td><form:input path="email" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:password path="password" /></td>
				</tr>

				<tr>
					<td>Role:</td>
					<td>
					<form:select path="role">
							<c:forEach var="role" items="${roleList}">
								<form:option value="${role}">${role}</form:option> 
							 </c:forEach> 
					</form:select>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save"></td>
				</tr>
			</form:form>
		</table>
	</div>

</body>
</html>