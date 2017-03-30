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

<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src ='${pageContext.request.contextPath}/resources/js/adminJs/adm-discount.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/adminJs/admin-discount.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/pagination-table.js'></script>
<script src="${pageContext.request.contextPath}/resources/js/pickers.js"></script>

<body>
<div class="table-wrapper">
    <div class="hide-border">
        <th colspan="6" class="set-standard-color">
            <legend class="for-table"><strong class="title-font">
                <i class="glyphicon glyphicon-calendar"></i>
                <spring:message code="administrator.dayDiscount"/></strong>
            </legend>
        </th>
    </div>
    <table class="reg-form datatable">
        <thead>
        <tr>
            <th><strong><spring:message code="administrator.dayDiscount.reason"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.value"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.startDate"/></strong></th>
            <th><strong><spring:message code="administrator.dayDiscount.endDate"/></strong></th>
            <th><strong><spring:message code="administrator.edit"/></strong></th>
            <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <a tabindex="-1" class="create-object">
        <button type="button" class="btn btn-raised btn-primary btn-add-room"
                data-toggle="modal" data-target="#createAbonnement">
            <spring:message code="administrator.add"/>
        </button>
    </a>
</div>
<!-- <div class="for-table table-responsive">
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
</div> -->

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

<!-- Modal window fro day discount -->
<div id="addDiscountDiv" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <table class="col-sm-offset-4 col-sm-3 reg-form">
            <tr>
                <th>
                    <strong class="title-font" id="dayDiscountModalTitle"></strong>
                </th>
            </tr>
            <tr>
                <td>
                    <form id="discountForm">
                        <div class="form-group sizing-between">
                            <label for="DReason" class="required">
                                <spring:message code="administrator.dayDiscount.reason"/>
                            </label>
                            <input id="DReason" type="text" name="reason" class="name form-control">
                        </div>
                        <div class="form-group sizing-between">
                            <label for="DValue" class="required">
                                <spring:message code="administrator.dayDiscount.value"/>
                            </label>
                            <input id="DValue" type="text" name="value" class="name form-control">
                        </div>

                        <!-- Date pickers -->
                        <div class="form-group sizing-between">
                                <label class="required">
                                    <spring:message code="administrator.dayDiscount.startDate"/>
                                </label>
                            <br/>
                            <div class="picker-style">
                                <input id="DStartDate" type="text" name="startDate" class="form-control datepickers">
                            </div>
                            <div class="picker-style">
                                <input id="DEndDate" type="text" name="endDate" class="form-control datepickers">
                            </div>
                        </div>

                        <!-- Time pickers -->
                        <div class="form-group sizing-between">
                            <label class="required">
                                <spring:message code="administrator.dayDiscount.endDate"/>
                            </label>
                            <br/>
                            <div class="picker-style">
                                <input id="DStartTime" type="time" name="startTime" class="form-control">
                            </div>
                            <div class="picker-style">
                                <input id="DEndTime" type="time" name="endTime" class="form-control">
                            </div>
                        </div>
                        <!-- -->
                        <div class="form-group sizing-between">
                         <button class="btn btn-raised btn-success submitter button-style">
                             <spring:message code="administrator.save"/>
                         </button>
                        <button type="reset" class="btn btn-raised btn-danger button-style" data-toggle="modal"
                                data-target="#addDiscountDiv">
                            <spring:message code="administrator.canc"/>
                        </button>
                        </div>
                    </form>
                </td>
            </tr>
        </table>

    </div>
</div>

</body>


