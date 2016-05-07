<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<header class="bg-primary">
    <div class="container">
            <div class="col-md-9">
                <h1>
                    KIDS ROOM
                </h1>
            </div>
            <div class="col-md-3 text-right user-control-wrapper">
                <sec:authorize access="!isAuthenticated()">
                    <a href="<c:url value="/login" />">
                        <span class="glyphicon glyphicon-log-in" ></span>
                        Login
                    </a>
                    <a  href="<c:url value="/registration" />">
                        <span class="glyphicon glyphicon-log-out"></span>
                        Registration
                    </a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <span>
                        Hello,
                    </span>
                    <sec:authentication
                            property="principal.username" />
                    </a>

                </sec:authorize>
            </div>
    </div>
    </header>