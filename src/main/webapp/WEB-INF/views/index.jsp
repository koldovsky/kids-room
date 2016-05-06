<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div>

        <sec:authorize access="!isAuthenticated()">
            <p><a href="login" /> Enter</a></p>

        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
        <p>Welcome</p>


        <p><a href="logout" />Log out</a></p>

        </sec:authorize>
        <sec:authorize access="hasRole('USER')">
            <p> I am User</p>
        </sec:authorize>

    </div>


</div>