<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<header class="bg-primary">
    <div class="container">
    	<div class="row">
            <div class="col-md-6">
                 <h1><a href="/home">Kid&#39;s Room</a></h1>
            </div>
            <div class="col-md-6">
                <sec:authorize access="!isAuthenticated()">
                    <a href="<c:url value="/login" />">
                        <span class="glyphicon glyphicon-log-in" ></span>
                        Login
                    </a>
                    <a  href="<c:url value="/registration" />">
                        <span class="glyphicon glyphicon-pencil"></span>
                        Registration
                    </a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">


                    <div class="col-sm-8">

                     <sec:authorize access="hasRole('USER')">
                                 <a href="mykids">My Kids</a>

                      </sec:authorize>
                       <sec:authorize access="hasRole('MANAGER')">
                                  <a href="report">Generate a report</a>
                                  <a href="listChildren">List of Kids</a>
                      </sec:authorize>

                      <a href="logout">    Logout   </a>
                      </nav>

                </sec:authorize>
            </div>
    </div>
</header>