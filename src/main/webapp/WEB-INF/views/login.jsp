<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="loginUrl" />


<div class="col-xs-6">
        <form class="form-horizontal" action="${loginUrl}" method="post">
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">email</label>
            <div class="col-sm-10">
              <input type="email" name="j_username" class="form-control" id="email" required autofocus value="manager@gmail.com">
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">password</label>
            <div class="col-sm-10">
              <input type="password" name="j_password" class="form-control" id="password" required value="manager">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <div class="checkbox">
                <label>
                  <input type="checkbox" name="remember-me"> Remember me
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">

            <div class="col-sm-offset-2 col-sm-10">
                  <c:if test="${param.error != null}">
                          <div class    ="error">
                              <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                          </div>
                  </c:if>
              <button type="submit" class="btn btn-default">Sign in</button>
            </div>
          </div>
        </form>


</div>