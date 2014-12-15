<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<title>AminMainPage</title>
</head>
<body>
 <c:if test="${user != null}">
  <p class="bg-success">User logining: ${fn:escapeXml(user.name)}
  (<a href="${pageContext.request.contextPath}/Logout">loginout</a>)
  </p>
  <hr /> 
 </c:if>
<div class="container col-md-offset-4 col-md-4"> 
 <c:if test="${errorMessages != null}">
  <h1 class="text-danger text-center">Error</h1>
  <c:forEach var="msg" items="${errorMessages}">
   <p class="bg-danger text-danger text-center">
    <c:out value="${msg}" />
   </p>
  </c:forEach>
 </c:if>
 <h1 class="text-center">Welcome here, ${user.name}!</h1>
 <hr />
 <form role="form" method="get"
       action="${pageContext.request.contextPath}/CustomerRecord">
       <div class="form-group">
        <input class="btn btn-primary btn-lg" type="submit" value="　　　　　　　　　Check your Records　　　　　　　　" />
       </div>
      </form>
 <form role="form" method="get"
       action="${pageContext.request.contextPath}/CustomerCourse">
       <div class="form-group">
        <input class="btn btn-success btn-lg" type="submit" value="　　　　　　Check and buy new course　　　　　　　" />
       </div>
      </form>
 <hr />

</div>
</body>
</html>
