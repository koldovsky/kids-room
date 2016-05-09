<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

 <div class="kidslist">
            <table>
            <caption><h2>List of kids</h2></caption>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>
            <c:forEach var="kids" items="${AllChildren}">
                <tr>
                    <td><c:out value="${kids.getId()}" /></td>
                    <td><c:out value="${kids.getFirstName()} " /></td>
                </tr>
            </c:forEach>
        </table>
    </div>