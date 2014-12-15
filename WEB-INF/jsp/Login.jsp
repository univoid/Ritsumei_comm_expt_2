<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<title>Login</title>
</head>
<body>
 <h1 class="text-center">Login</h1>
 <c:if test="${errorMessage != null}">
  <div class="container col-md-offset-4 col-md-4">
  <p class="bg-danger text-danger text-center">
    <strong>login error:</strong> ${errorMessage}
  </p>
  </div>
 </c:if>
 <div class="container col-md-offset-4 col-md-4">
 <form class="form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/Login">
  <div class="form-group">
  <div class="form-group">
      <label class="control-label">User Name:</label>
      <input class="form-control" type="text" name="username" value="${username}" />
  </div>
  <div class="form-group">
      <label class="control-label">Password:</label> 
      <input class="form-control" type="password" name="password" />
  </div>
  </div>
  <div class="form-group">
      <input class="col-md-offset-5 btn btn-primary btn-lg" type="submit" value="　Login　" />
  </div>
 </form>
 </div>
 <div class="container col-md-offset-4 col-md-4">
 <div class="bg-info text-center">
    <p>admin account，Username: admin01, Password: 123123 </p>
    <p>customer account 01，Username: jack, Password: 123123 </p>
    <p>customer account 02，Username: alice, Password: 123123 </p>
 </div>
 </div>
</body>
</html>
