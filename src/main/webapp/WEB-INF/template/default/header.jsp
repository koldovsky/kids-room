<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<header class="bg-primary">
    <div class="container">
    	<div class="row">
            <div class="col-md-4">
                 <h1><a href="/home">Kid&#39;s Room</a></h1>
            </div>
            <div class="col-md-8">
                <sec:authorize access="!isAuthenticated()">
                    <a href="<c:url value="/login" />">
                        <span class="glyphicon glyphicon-log-in" ></span>
                         <spring:message code="user.login" />
                    </a>
                    <a  href="<c:url value="/registration" />">
                        <span class="glyphicon glyphicon-pencil"></span>
                         <spring:message code="user.registration" />
                    </a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                <div class="col-sm-12">

                     <sec:authorize access="hasRole('USER')">
                                 <a href="mykids"><spring:message code="user.myKids" /></a>

                      </sec:authorize>
                       <sec:authorize access="hasRole('MANAGER')">
                                  <a href="report"><spring:message code="manager.report" /></a>
                                  <a href="listChildren"><spring:message code="manager.listOfKids" /></a>
                      </sec:authorize>

                     <sec:authorize access="hasRole('ADMINISTRATOR')">
                         <a href="adm-edit-location"><spring:message code="administrator.editLocations" /></a>
                         <a href="adm-edit-manager"><spring:message code="administrator.editManagers" /></a>
                     </sec:authorize>

                      <a href="logout">     <spring:message code="user.logout" />   </a>
                      </nav>

                </sec:authorize>
                <a href="?language=en">EN</a>|<a href="?language=ua">UA</a>
            </div>
    </div>
</header>