<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/report.css'>
<link rel="stylesheet" href='${pageContext.request.contextPath}/resources/css/lib/jquery-ui.css'>

<script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js' type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/statistics.js"></script>
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

    <div class="tableDiv">
        <table id="statistics">

            <caption>
                <h3>
                    <spring:message code="statistics.rooms" />
                </h3>
            </caption>

            <tr id="header">
                <th><spring:message code="statistics.name" /></th>
                <th><spring:message code="statistics.city" /></th>
                <th><spring:message code="statistics.address" /></th>
                <th><spring:message code="statistics.managers" /></th>
                <th><spring:message code="statistics.sum" /></th>
            </tr>

        </table>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/pickers.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="${pageContext.request.contextPath}/resources/js/lib/datepicker-uk.js"></script>
</c:if>