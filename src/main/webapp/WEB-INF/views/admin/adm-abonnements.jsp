<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<script type="text/javascript" src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap4.min.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/pagination-table.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/admin-abonnement.js'></script>


<body>
<div class="table-wrapper">
    <div class="hide-border">
        <th colspan="7" class="set-standard-color">
            <legend class="for-table"><strong class="title-font">
                <i class="glyphicon glyphicon-gift"></i>
                <spring:message code="administrator.abonnements"/></strong>
            </legend>
        </th>
    </div>

    <div class="datatable-wrapper">
        <div class="column-names">
            <span>name</span>
            <span>price</span>
            <span>hour</span>
        </div>
        <div class="search-fields">
            <div><b>Search:</b></div>
            <input type="text" class="form-control search-input search-input-first" placeholder="name"/>
            <input type="text" class="form-control search-input" placeholder="price"/>
            <input type="text" class="form-control search-input" placeholder="hour"/>
        </div>
        <table class="reg-form dt datatable ">
            <thead>
            <tr>
                <th style="display: none;"><strong>#</strong></th>
                <th><strong><spring:message code="administrator.abonnement.name"/></strong></th>
                <th><strong><spring:message code="administrator.abonnement.price"/></strong></th>
                <th><strong><spring:message code="administrator.abonnement.hour"/></strong></th>
                <th><strong><spring:message code="administrator.edit"/></strong></th>
                <th><strong><spring:message code="administrator.assign"/></strong></th>
                <th><strong><spring:message code="administrator.abonnement.active"/></strong></th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <!-- abonnements list bounds here -->
    <a tabindex="-1" class="create-object">
        <button type="button" class="btn btn-raised btn-primary btn-add-room"
                data-toggle="modal" data-target="#createAbonnement">
            <spring:message code="administrator.add"/>
        </button>
    </a>
</div>

<!-- create Abonnement dialog -->
<div id="createAbonnement" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <table class="col-sm-offset-4 col-sm-3 reg-form">
            <tr>
                <th>
                    <strong class="title-font">
                        <spring:message code="administrator.createAbonnement"/>
                    </strong>
                </th>
            </tr>

            <tr>
                <td>
                    <form id="createAbonnementForm" method="POST" action="/adm-create-abonnement">
                        <input type="text" name="id" class="id form-control"/>
                        <div class="form-group sizing-between">
                            <label for="name" class="required">
                                <spring:message code="administrator.abonnement.name"/>
                            </label>
                            <input type="text" name="name" class="name form-control">
                        </div>
                        <div class="form-group sizing-between">
                            <label for="price" class="required">
                                <spring:message code="administrator.abonnement.price"/>
                            </label>
                            <input type="text" name="price" class="price form-control">
                        </div>

                        <div class="form-group sizing-between">
                            <label for="hour" class="required">
                                <spring:message code="administrator.abonnement.hour"/>
                            </label>
                            <input type="text" name="hour" class="hour form-control">
                        </div>

                        <div class="form-group sizing-between">
                            <input type="submit" value="<spring:message code="administrator.save"/>"
                                   class="btn btn-raised btn-success"
                                   data-toggle="modal" data-target="#createAbonnement">
                            </input>
                            <button type="reset" class="btn btn-raised btn-danger" data-toggle="modal"
                                    data-target="#createAbonnement">
                                <spring:message code="administrator.canc"/>
                            </button>
                        </div>
                    </form>
                <td>
            </tr>
        </table>
    </div>
</div>

<!-- update Abonnement dialog -->
<div id="updateAbonnement" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <table class="col-sm-offset-4 col-sm-3 reg-form">
            <tr>
                <th>
                    <strong class="title-font">
                        <spring:message code="administrator.updateAbonnement"/>
                    </strong>
                </th>
            </tr>

            <tr>
                <td>
                    <form id="updateAbonnementForm" method="POST" action="/adm-update-abonnement">
                        <input type="text" name="id" class="id form-control"/>
                        <div class="form-group sizing-between">
                            <label for="name" class="required">
                                <spring:message code="administrator.abonnement.name"/>
                            </label>
                            <input id="name" type="text" name="name" class="name form-control">
                        </div>

                        <div class="form-group sizing-between">
                            <label for="price" class="required">
                                <spring:message code="administrator.abonnement.price"/>
                            </label>
                            <input id="price" type="text" name="price" class="price form-control">
                        </div>

                        <div class="form-group sizing-between">
                            <label for="hour" class="required">
                                <spring:message code="administrator.abonnement.hour"/>
                            </label>
                            <input id="hour" type="text" name="hour" class="hour form-control">
                        </div>

                        <div class="form-group sizing-between">
                            <input type="submit" value="<spring:message code="administrator.save"/>"
                                   class="btn btn-raised btn-success"
                                   data-toggle="modal" data-target="#updateAbonnement">
                            </input>
                            <button type="reset" class="btn btn-raised btn-danger" data-toggle="modal"
                                    data-target="#updateAbonnement">
                                <spring:message code="administrator.canc"/>
                            </button>
                        </div>
                    </form>
                <td>
            </tr>
        </table>
    </div>
</div>

<!-- activate dialog -->
<div id="confirmation-activate" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <div class="lead messageActive">
                    <p id="inactive-manager-span" hidden>
                        <span><spring:message code="administrator.abonnement.inactive.confirm"/></span></p>
                    <p id="active-manager-span" hidden>
                        <span><spring:message code="administrator.abonnement.active.confirm"/></span></p>
                </div>
                <button id="confirmYesEvent" class="btn  btn-success admWarningBtn" data-dismiss="modal">
                    <spring:message code="yes"/>
                </button>
                <button id="confirmNoEvent" class="btn btn-danger admWarningBtn" data-dismiss="modal">
                    <spring:message code="no"/>
                </button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>

<%--<script src='${pageContext.request.contextPath}/resources/js/admin-abonnements.js'></script>--%>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-abonnements.js"></script>
</body>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="${pageContext.request.contextPath}/resources/js/lib/datepicker-uk.js"></script>
</c:if>