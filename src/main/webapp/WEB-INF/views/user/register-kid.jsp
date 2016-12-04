<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ page import="ua.softserveinc.tc.constants.ChildConstants" %>
<%@ page import="ua.softserveinc.tc.constants.ValidationConstants" %>

<link rel='stylesheet' href='resources/css/registerkid.css'>
<script src="resources/js/comment-box.js"></script>
<script src="resources/js/kid-registration.js"></script>
<script src="resources/js/jquery.min.js"></script>


<form:form id="kidregistrform" enctype="application/x-www-form-urlencoded" action="registerkid"
           method="post" modelAttribute="<%=ChildConstants.View.KID_ATTRIBUTE %>">

    <img class="icon-png" src="resources/img/edit.png"/>
    <h2>
        <spring:message code="kid.register"/>
    </h2>
    <form:hidden path="id"/>
    <div class="form-group-material-blue-400">
        <div class="form-group">
            <label for="firstName" class="required">
                <spring:message code="kid.firstname"/>
            </label>

            <form:input path="firstName" type="text" id="firstName" class="form-control"
                        required="required"/>
            <div class="error_form" id="firstname_error_message"></div>
            <form:errors path="firstName" cssClass="error"/>
        </div>
        <div class="form-group">
            <label for="lastName" class="required">
                <spring:message code="kid.lastname"/>
            </label>
            <form:input path="lastName" type="text" id="lastName" class="form-control"
                        required="required"/>
            <span class="error_form" id="lastname_error_message"></span>
            <div class="err-msg">
                <form:errors path="lastName" cssClass="error">
                    <spring:message code="registration.kid.lastName"/>
                </form:errors>
            </div>

        </div>


        <div class="form-group">
            <label for="date" class="required">
                <spring:message code="kid.date"/>
            </label>
            <form:input path="dateOfBirth" type="date" id="date" name="date"
                        class="form-control" required="required"/>
            <div class="err-msg">
                <form:errors path="dateOfBirth" cssClass="error">
                    <spring:message code="registration.kid.date"
                                    arguments="${config.kidsMinAge}, ${config.kidsMaxAge}"/>
                </form:errors>
            </div>
        </div>


        <div class="form-group">
            <label class="required"><spring:message code="kid.gender"/></label>
            <table>
                <tr>
                    <td>
                        <form:radiobutton required="required" name="Boy" path="gender" value="MALE"/><spring:message
                            code="kid.boy"/>
                    </td>
                    <td>
                        <form:radiobutton required="required" path="gender" value="FEMALE"/><spring:message
                            code="kid.girl"/></td>
                </tr>
            </table>

        </div>


        <div class="form-group">
            <label for="comment">
                <spring:message code="kid.comment"/>
            </label>
            <form:textarea path="comment" rows="1" class="form-control common comment"/>
            <div class="err-msg">
                <form:errors path="comment" cssClass="error">
                    <spring:message code="registration.kid.comment"/>
                </form:errors>
            </div>
        </div>


        <button class="btn btn-raised btn-success" type="submit" name="action">
            <spring:message code="user.submit"/>
        </button>
        <button class="btn btn-raised btn-danger" type="reset" id="add-kid-cancel">
            <spring:message code="cancel" />
        </button>

        <footer class="bottom-msg">
            <p class="explanation"><spring:message code="requiredfield.explanation"/></p>
        </footer>
    </div>
</form:form>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script src="resources/js/validation-kids.js"></script>
<c:choose>
<c:when test="${pageContext.response.locale=='ua'}">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
    <script src="resources/js/lib/messages-ua.js"></script>
</c:when>
<c:when test="${pageContext.response.locale!= 'ua'}">
    <script src="resources/js/lib/messages.js"></script>
</c:when>
</c:choose>





