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
<link href="${pageContext.request.contextPath}/resources/css/lib/select2.min.css" rel="stylesheet"/>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/select2.min.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/adminJs/admin-discount.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/pagination-table.js'></script>
<script src="${pageContext.request.contextPath}/resources/js/pickers.js"></script>

<body>
<!-- Day discount datatable -->
<div class="table-wrapper">
    <div class="hide-border">
        <th colspan="6" class="set-standard-color">
            <legend class="for-table"><strong class="title-font">
                <i class="glyphicon glyphicon-calendar"></i>
                <spring:message code="administrator.dayDiscount"/></strong>
            </legend>
        </th>
    </div>
    <table class="reg-form datatable dayDiscountDataTable">
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
                id="addDiscount" data-toggle="modal" data-target="#addDiscountDiv">
            <spring:message code="administrator.add"/>
        </button>
    </a>
</div>

<br/>
<!-- Personal discount datatable -->
<div class="table-wrapper">
    <div class="hide-border">
        <th colspan="6" class="set-standard-color">
            <legend class="for-table"><strong class="title-font">
                <i class="glyphicon glyphicon-user"></i>
                <spring:message code="administrator.personalDiscount"/></strong>
            </legend>
        </th>
    </div>
    <table class="reg-form datatable personalDiscountDataTable">
        <thead>
        <tr>
            <th><strong><spring:message code="administrator.personalDiscount.username"/></strong></th>
            <th><strong><spring:message code="administrator.personalDiscount.value"/></strong></th>
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
                id="addPersonalDiscount" data-toggle="modal" data-target="#addPersonalDiscountDiv">
            <spring:message code="administrator.add"/>
        </button>
    </a>
</div>

<!-- Modal window for day discount -->
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
                            <input id="DReason" type="text" name="reason" class="name form-control" placeholder="<spring:message code="administrator.dayDiscount.reason"/>">
                        </div>
                        <div class="form-group sizing-between">
                            <label for="DValue" class="required">
                                <spring:message code="administrator.dayDiscount.value"/>
                            </label>
                            <input id="DValue" type="text" name="value" class="name form-control" placeholder="<spring:message code="administrator.dayDiscount.value"/>">
                        </div>

                        <!-- Date pickers -->
                        <div class="form-group sizing-between">
                                <label class="required">
                                    <spring:message code="administrator.dayDiscount.startDate"/>
                                </label>
                            <br/>
                            <div class="picker-style">
                                <input id="DStartDate" type="text" name="startDate" class="form-control datepickers" placeholder="<spring:message code="administrator.discountModal.startDate"/>">
                            </div>
                            <div class="picker-style">
                                <input id="DEndDate" type="text" name="endDate" class="form-control datepickers" placeholder="<spring:message code="administrator.discountModal.endDate"/>">
                            </div>
                        </div>

                        <!-- Time pickers -->
                        <div class="form-group sizing-between">
                            <label class="required">
                                <spring:message code="administrator.dayDiscount.endDate"/>
                            </label>
                            <br/>
                            <div class="picker-style">
                                <input id="DStartTime" type="text" name="startTime" class="form-control picker" placeholder="<spring:message code="administrator.discountModal.startTime"/>">
                            </div>
                            <div class="picker-style">
                                <input id="DEndTime" type="text" name="endTime" class="form-control picker" placeholder="<spring:message code="administrator.discountModal.endTime"/>">
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

<div id="addPersonalDiscountDiv" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <table class="col-sm-offset-4 col-sm-3 reg-form">
            <tr>
                <th>
                    <strong class="title-font" id="personalDiscountModalTitle"></strong>
                </th>
            </tr>
            <tr>
                <td>
                    <form id="discountPersonalForm">
                        <div class="form-group sizing-between">
                        <label class="required">
                            <spring:message code="administrator.personalDiscount.username"/>
                        </label>
                        <br/>
                        <select id="selectUser" name="select" style="width: 100%">
                            <option value=" " disabled selected hidden></option>
                            <option value="1">lol1</option>
                            <option value="2">lol2</option>
                            <option value="3">lol3</option>
                        </select>
                        </div>
                        <!-- Value of discount -->
                        <div class="form-group sizing-between">
                            <label for="PValue" class="required">
                                <spring:message code="administrator.dayDiscount.value"/>
                            </label>
                            <input id="PValue" type="text" name="value" class="name form-control" placeholder="<spring:message code="administrator.dayDiscount.value"/>">
                        </div>
                        <!-- Full or specific period -->
                        <div class="form-group sizing-between">
                            <label>
                                <spring:message code="administrator.dayDiscount.value"/>
                            </label>
                            <br/>
                            <label class="switch">
                                <input type="checkbox" checked class='activate'>
                                <div class='slider round'></div>
                            </label>

                        </div>
                        <!-- Time pickers -->
                        <div class="form-group sizing-between">
                            <label class="required">
                                <spring:message code="administrator.dayDiscount.endDate"/>
                            </label>
                            <br/>
                            <div class="picker-style">
                                <input id="PStartTime" type="text" name="startTime" class="form-control picker" placeholder="<spring:message code="administrator.discountModal.startTime"/>">
                            </div>
                            <div class="picker-style">
                                <input id="PEndTime" type="text" name="endTime" class="form-control picker" placeholder="<spring:message code="administrator.discountModal.endTime"/>">
                            </div>
                        </div>
                        <!-- -->
                        <div class="form-group sizing-between">
                            <button class="btn btn-raised btn-success submitter button-style">
                                <spring:message code="administrator.save"/>
                            </button>
                            <button type="reset" class="btn btn-raised btn-danger button-style" data-toggle="modal"
                                    data-target="#addPersonalDiscountDiv">
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


