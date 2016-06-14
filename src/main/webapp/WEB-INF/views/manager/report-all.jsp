<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="allURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/disable-room-picker.css'>

<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input disabled type="date" value="${startDate}">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input disabled type="date" value="${endDate}">
        </div>
    </div>

    <div class="tableDiv">
        <table>

            <caption>
                <h2>
                    <spring:message code="report.allBookings" /> ${room}
                </h2>
            </caption>

            <tr>
                <th><spring:message code="report.parent" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.sum" /></th>
            </tr>

            <c:forEach var="user" items="${report.keySet()}">
            <tr>
                <td>${user}</td>
                <td>${user.getEmail()}</td>
                <td>${report.get(user)}</td>
            </tr>
            </c:forEach>

        </table>
    </div>

    <div>
        <button class="btn btn-raised btn-primary waves-effect waves-light">
        <spring:message code="report.download" /></button>
    </div>
</div>