<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div>
        <sec:authorize access="!isAuthenticated()">
            <p><a href="login" /> Enter</a></p>
        </sec:authorize>

        <sec:authorize access="isAuthenticated()">
            Your email <sec:authentication property="principal.username" /></p>
            <p><a href="logout" />Log out</a></p>
        </sec:authorize>

        <sec:authorize access="hasRole('USER')">
            <p> I am USER</p>
            <h2><a href="mykids">My Kids</a></h2>
        </sec:authorize>
        <sec:authorize access="hasRole('MANAGER')">
            <p> I am MANAGER</p>
        </sec:authorize>
        <sec:authorize access="hasRole('ADMINISTRATOR')">
             <p> I am ADMINISTRATOR</p>
        </sec:authorize>



</div>