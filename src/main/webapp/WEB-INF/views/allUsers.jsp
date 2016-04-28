<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users</title>
</head>
		<body>
			<div align="center">
				<h1>Users List</h1>
				<h2>
					<a href="new">New User</a>
				</h2>
				<div>
					<div>
						<table>

							<tr class="success">
								<th>Email</th>
								<th>Role</th>
								<th>Enable</th>
								<th>Password</th>
							</tr>

							<c:forEach var="user" items="${userList}" >
								<form action="change" method="post">
									<tr>
										<td>${user.email}</td>
										<td> <select name="userRole">
												<c:forEach var="role" items="${roleList}">
                                                    <option value="${role}" <c:if test="${ role eq user.role }">selected</c:if> >${role}</option>
                                                </c:forEach>
										     </select>
										</td>
										<td><c:choose>
												<c:when test="${user.enable}">
													<input type="checkbox" checked name="enable" />
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="enable" />
												</c:otherwise>
											</c:choose></td>
                                        <td>${user.password}</td>
										<td><input type="hidden" name="id" value="${user.id}">
											<button type="submit">Save</button></td>
									</tr>
								</form>
							</c:forEach>

						</table>
					</div>
				</div>



			</div>


		</body>


</html>