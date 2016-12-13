<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/button-styles.css">


<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 85%;">
        <tr class="hide-border">
            <th colspan="10" class="set-standard-color">
                <strong class="title-font"><spring:message code="administrator.listRooms"/></strong>
            </th>
        </tr>

        <tr>
            <th class="th-size"><strong><spring:message code="administrator.room.name"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.address"/></strong></th>
            <th class="th-size"><input type="text" id="search" style="color:#827878;" placeholder="<spring:message code="administrator.room.city"/> "></th>
            <th class="th-size"><strong><spring:message code="administrator.phoneNumber"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.capacity"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.roomWorkTime"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.manager"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.rate"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.edit"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.block"/></strong></th>
        </tr>

        <c:forEach var="room" items="${roomList}">
        <c:if test="${room.active eq true}"><tr class="room"></c:if>
        <c:if test="${room.active ne true}"><tr class="tr-not-active"></c:if>
            <td>${room.name}</td>
            <td>${room.address}</td>
            <td class="city">${room.city}</td>
            <td>${room.phoneNumber}</td>
            <td>${room.capacity}</td>
            <td>
                <table class="hide-border">
                    <tr><td><spring:message code="administrator.room.startTime"/> ${room.workingHoursStart}</td></tr>
                    <tr><td><spring:message code="administrator.room.endTime"/> ${room.workingHoursEnd}</td></tr>
                </table>
            </td>
            <td>
                <table class="hide-border">
                    <c:forEach var="manager" items="${room.managers}">
                        <tr><td >${manager.firstName} ${manager.lastName}</td></tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <table class="hide-border">
                    <tr>
                        <td><spring:message code="administrator.room.rate.hourRate"/></td>
                        <td><spring:message code="administrator.room.rate.priceRate"/></td>
                    </tr>
                    <c:forEach var="rate" items="${room.rates}">
                    <tr>
                        <td>${rate.hourRate}</td>
                        <td>${rate.priceRate}</td>
                    </tr>
                    </c:forEach>
                </table>
            </td>
            <td><a href="adm-update-room?id=${room.id}" tabindex="-1"><button class="button button-size-default edit"></button></a></td>
            <td>
                <c:if test="${room.active ne true}">
                    <c:url var="lockUrl" value="/adm-edit-room?id=${room.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                        <input id="room" name="manager" type="hidden" value="${room.id}" />
                        <button id = "activateButton" type="button" value="lock"
                                class="button button-size-default delete"></button>
                    </form:form>
                </c:if>
                <c:if test="${room.active eq true}">
                    <c:url var="lockUrl" value="/adm-edit-room?id=${room.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                       <input id="room" name="room" type="hidden" value="${room.id}" />
                       <button id = "deactivateButton" type="button"
                               class="button button-size-default save"></button>
                    </form:form>
                </c:if>
            </td>
         </tr>
         </c:forEach>

        <tr>
            <th colspan="10" class="hide-border set-standard-color">
                <a href="adm-add-room" tabindex="-1">
                    <button type="button" class="button-add button">
                        <spring:message code="administrator.add"/>
                    </button>
                </a>
            </th>
        </tr>
    </table>
</div>
<div id="deactivateModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <br>
                <h2><spring:message code="room.deactivate"/></h2>
                <p class="cancelName ">
                    <spring:message code="booking.confirmCancelQuestion2"/>
                </p>
                <button id="deactivateYesButton" class="btn  btn-success">
                    <spring:message code="room.yes_button" />
                </button>
                <button id="deactivateNoButton" class="btn btn-danger" data-dismiss="modal">
                    <spring:message code="room.no_button"/>
                </button>
            </div>
        </div>
    </div>
</div>
<div id="activateModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <br>
                <h2><spring:message code="room.activate"/></h2>
                <button id="activateYesButton" class="btn  btn-success">
                    <spring:message code="room.ok_button" />
                </button>
            </div>
        </div>
    </div>
</div>
<div id="positiveResultModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <br>
                <h2><spring:message code="room.deactivatePosResult"/></h2>
                <button id="positiveYesButton" class="btn  btn-success">
                    <spring:message code="room.ok_button" />
                </button>
            </div>
        </div>
    </div>
</div>
<div id="negativeResultModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <br>
                <h2><spring:message code="room.deactivateNegResult"/></h2>
                <button id="negativeYesButton" class="btn  btn-success">
                    <spring:message code="room.ok_button" />
                </button>
            </div>
        </div>
    </div>
</div>

<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script src='resources/js/adminRoomSearch.js'></script>
<script src='resources/js/admin-deactivate-room-button.js'></script>

<c:choose>
    <c:when test="${pageContext.response.locale=='ua'}">
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
        <script src="resources/js/lib/messages-ua.js"></script>
    </c:when>
    <c:when test="${pageContext.response.locale!= 'ua'}">
        <script src="resources/js/lib/messages.js"></script>
    </c:when>
</c:choose>

</body>
