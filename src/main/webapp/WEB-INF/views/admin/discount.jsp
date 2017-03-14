<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/preloader.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel='stylesheet' href='resources/css/flow-form.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-add-manager.css">

<body>
<div class="for-table table-responsive">
    <table id="dayDiscounts" class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-calendar"></i>
                    <spring:message code="administrator.dayDiscount"/></strong>
                </legend>
            </th>
        </tr>
        <tr></tr>
        <tr>
            <th><strong><spring:message code="administrator.dayDiscount.reason"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.value"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.startDate"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.endDate"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>
        <tr id="firstLoader" style="display: none">
            <td colspan="6">
                <div class="loader"></div>
            </td>
        </tr>
    </table>
</div>

<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 70%;">
        <tr class="hide-border">
            <th colspan="7" class="set-standard-color">
                <legend class="for-table"><strong class="title-font">
                    <i class="glyphicon glyphicon-user"></i>
                    <spring:message code="administrator.personalDiscount"/></strong>
                </legend>
            </th>
        </tr>
        <tr></tr>
        <tr>
            <th><strong><spring:message code="administrator.personalDiscount.username"/></strong></th>
            <th><strong><spring:message code="administrator.personalDiscount.value"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>
        <tr>
            <td colspan="6" style="display: none">
                <div class="loader"></div>
            </td>
        </tr>

        <tr>
            <th colspan="10" class="hide-border set-standard-color">
                    <button type="button" class="btn btn-raised btn-primary btn-add-room" data-toggle="modal" data-target="#updateAbonnement">
                        <spring:message code="administrator.add"/>
                    </button>
            </th>
        </tr>
    </table>
</div>

<!-- Modal window -->
<div id="updateAbonnement" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <table class="col-sm-offset-4 col-sm-3 reg-form">
            <tr>
                <th>
                    <strong class="title-font">
                        Add discount
                    </strong>
                </th>
            </tr>
            <tr>
                <td>
                    <form id="discountForm" method="POST" action="/adm-update-abonnement">
                        <div class="form-group sizing-between">
                            <label for="reason" class="required">
                                Reason of discount
                            </label>
                            <input id="reason" type="text" name="name" class="name form-control">
                        </div>
                        <div class="form-group sizing-between">
                            <label for="value" class="required">
                                Value of discount
                            </label>
                            <input id="value" type="text" name="name" class="name form-control">
                        </div>

                        <!-- Date pickers -->
                        <div class="form-group sizing-between">
                                <label class="required">
                                    Period of discount
                                </label>
                            <br/>
                            <div class="picker-style">
                                <input id="startDate" type="date" name="name" class="form-control">
                            </div>
                            <div class="picker-style">
                                <input id="endDate" type="date" name="name" class="form-control">
                            </div>
                        </div>

                        <!-- Time pickers -->
                        <div class="form-group sizing-between">
                            <label for="startDate" class="required">
                                Time of discount
                            </label>
                            <br/>
                            <div class="picker-style">
                                <input id="startTime" type="time" name="name" class="form-control">
                            </div>
                            <div class="picker-style">
                                <input id="endTime" type="time" name="name" class="form-control">
                            </div>
                        </div>
                    </form>
                </td>
            </tr>
        </table>

    </div>
</div>

</body>

<script type="text/javascript" src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src ='${pageContext.request.contextPath}/resources/js/adminJs/adm-discount.js'></script>
