<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<button id="enable" class="btn btn-default" style="float: right"><spring:message code="button.edit" /></button>
<div class="table" id="allkidstable">
    <div class="thead row-fluid">
        <div class="th span3"><spring:message code="kids.parentsName" /></div>
        <div class="th span3"><spring:message code="kids.name" /></div>
        <div class="th span3"><spring:message code="kids.age" /></div>
    </div>
    <c:forEach var="kid" items="${kids}" varStatus="loop">
        <div class="tr row-fluid accordion-toggle ${(loop.index + 1) % 2 == 1 ?
            'table_even_element' : 'table_odd_element'}""
            data-toggle="collapse" data-target="#kidcollapse${loop.index}">

            <div class="td span3">
                <a href="#" id="parentname${loop.index}">${kid.getParentId().getFullName()}</a>
            </div>
            <div class="td span3">
                <a href="#" id="kidname${loop.index}">${kid.getFullName()}</a>
            </div>
            <div class="td span3">
                ${kid.getAge()}
            </div>

        </div>
        <div id="kidcollapse${loop.index}" class="kid-detailed-info row-fluid collapse out ${(loop.index + 1) % 2 == 1 ? 'table_even_element' : 'table_odd_element'}">
            <div class="span1"></div>
            <div class="span9">
                <div class="span4">
                    <h4><spring:message code="kids.phone" /></h4>
                    <p style="float: right">
                        <a href="#" id="kidphone${loop.index}">${kid.getParentId().getPhoneNumber()}</a>
                    </p>
                    <br>
                    <h4><spring:message code="kids.comment" /></h4>
                    <p style="float: right">
                        <a href="#" id="kidcomment${loop.index}">${kid.getComment()}</a>
                    </p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script src="resources/js/allkidslist.js"></script>
