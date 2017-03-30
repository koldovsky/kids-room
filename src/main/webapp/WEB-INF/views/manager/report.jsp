<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/report.css'>

<script src="${pageContext.request.contextPath}/resources/js/report.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pagination.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/header-manager.js"></script>

<script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js' type="text/javascript"></script>
<link href='${pageContext.request.contextPath}/resources/css/lib/jquery-ui.css' rel="stylesheet"/>
<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input type="text" value="${startDate}" id="startDate" class="datepickers">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input type="text" value="${endDate}" id="endDate" class="datepickers">
        </div>
    </div>

    <div id="errorDate"></div>

    <div class="tableDiv">
        <table id="bookings">

            <caption>
                <h2>
                    <spring:message code="report.activeParents" /></br>
                </h2>
            </caption>

            <tr id="header">
                <th><spring:message code="report.name" /></th>
                <th><spring:message code="report.surname" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.phone" /></th>
                <th><spring:message code="report.bookings" /></th>
            </tr>

        </table>
    </div>

    <div>
        <button id="generate" class="btn btn-raised btn-primary waves-effect waves-light">
        <spring:message code="report.generate" /></button>
    </div>
    <input type="hidden" id="localizedDetails" value=<spring:message code="report.details"/> />
    <input id="itemsPerPage" type="hidden" value="10" />
    <a id="dlink"  style="display:none;"></a>
</div>
<script src="${pageContext.request.contextPath}/resources/js/pickers.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="${pageContext.request.contextPath}/resources/js/lib/datepicker-uk.js"></script>
</c:if>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-manager-report.js"></script>