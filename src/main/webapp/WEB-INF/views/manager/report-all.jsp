<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="allURL" />

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/report.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/disable-room-picker.css'>

<script src="${pageContext.request.contextPath}/resources/js/pagination.js"></script>

<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input disabled type="date" value="${startDate}" id="startDate">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input disabled type="date" value="${endDate}" id="endDate">
        </div>
    </div>

    <div class="tableDiv">
        <table id="bookings">

            <caption>
                <h2>
                    <spring:message code="report.allBookings" /> ${room}
                </h2>
            </caption>
            <thead>
                <tr>
                    <th><spring:message code="report.parent" /></th>
                    <th><spring:message code="report.email" /></th>
                    <th><spring:message code="report.sum" /></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="user" items="${report.keySet()}">
                <tr>
                    <td>${user}</td>
                    <td>${user.getEmail()}</td>
                    <td><fmt:formatNumber var="userSum" value="${report.get(user) / 100}" maxFractionDigits="2" minFractionDigits="2" />${userSum}</td>
                </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3" class="captionBottom">
                    <h2>
                        <spring:message code="report.sumTotal" />
                        <fmt:formatNumber var="totalSum" value="${sumTotal / 100}" maxFractionDigits="2" minFractionDigits="2" />
                        ${totalSum}
                        <spring:message code="report.currencySymbol" />
                    </h2>
                </td>
            </tr>
            </tfoot>

        </table>
    </div>

    <input id="itemsPerPage" type="hidden" value="10"></input>
    <a id="dlink"  style="display:none;"></a>
    <button id="export" class="btn btn-raised btn-success waves-effectwaves-light exportButton glyphicon glyphicon-download-alt">
        &nbsp; <spring:message code="report.download" /> Excel
    </button>
</div>