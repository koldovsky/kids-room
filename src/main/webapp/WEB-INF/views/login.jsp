<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




<div>
<c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2 >Please sign in</h2>

        <input type="text"  name="j_username" placeholder="Email address" required autofocus value="user@gmail.com">
        <input type="password"  name="j_password" placeholder="Password" required value="user">
        <button  type="submit">Submit</button>
    </form>
</div>
