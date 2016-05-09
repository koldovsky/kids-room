<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table">
    <div class="thead row-fluid">
        <div class="th span3">Kid's Parents Name</div>
        <div class="th span3">Kid Name</div>
        <div class="th span3">Age</div>
    </div>
    <c:forEach var="kid" items="${kids}" varStatus="loop">
        <div class="tr row-fluid accordion-toggle ${(loop.index + 1) % 2 == 1 ? 'table_even_element' : 'table_odd_element'}""
            data-toggle="collapse" data-target="#kidcollapse${loop.index}">
            <div class="td span3">${kid.getParentId().getFirstName()} ${kid.getParentId().getLastName()}</div>
            <div class="td span3">${kid.getFirstName()} ${kid.getLastName()}</div>
            <div class="td span3">${kid.getDateOfBirth()}</div>
        </div>
        <div id="kidcollapse${loop.index}" class="kid-detailed-info row-fluid collapse out ${(loop.index + 1) % 2 == 1 ? 'table_even_element' : 'table_odd_element'}">
            <div class="span1"></div>
            <div class="span9">
                <div class="span4">
                    <h4>Phone Number</h4>
                    <p style="float: right">${kid.getParentId().getPhoneNumber()}</p>
                    <br>
                    <h4>Comment</h4>
                    <p style="float: right">${kid.getComment()}</p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
