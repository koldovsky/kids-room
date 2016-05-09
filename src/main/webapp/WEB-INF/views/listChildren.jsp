<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

 <div class="kidslist">
            <table class="listChild">
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

    <div>
        <table class="reportTime">
            <tr>
                <td colspan="2">
                    <input class="buttons2" type="submit" value="Arrival time"/>
                    <input class="buttons2" type="submit" value="Departure time"/>
                </td>
          </tr>
          <tr>
            <th class="odd">Booking time</th>
            <th class="odd">Real time</th>
          </tr>
           <tr>
                <td><input type="time" value="12:00"/></td>
                <td><input type="time" value="15:00"/></td>
          </tr>
          <tr>
            <td  colspan="2">
            <input class="buttons" type="submit" value="Apply"/>
            <input class="buttons" type="submit" value="Cancel"/>
            <input class="buttons" type="submit" value="Ok"/>
        </td>
      </tr>
    </table>
    </div>

