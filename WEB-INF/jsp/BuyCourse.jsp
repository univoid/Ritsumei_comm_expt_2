<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<title>Record List</title>
</head>
<body>
 <c:if test="${user != null}">
  <p class="bg-success">User logining: ${fn:escapeXml(user.name)}
  (<a href="${pageContext.request.contextPath}/Logout">loginout</a>)
  </p>
  <hr /> 
 </c:if>
<div class="container col-md-offset-1 col-md-10">
 <c:if test="${errorMessages != null}">
  <h1 class="text-danger text-center">Error</h1>
  <c:forEach var="msg" items="${errorMessages}">
   <p class="bg-danger text-danger text-center">
    <c:out value="${msg}" />
   </p>
  </c:forEach>
 </c:if>
 <h1 class="text-center">purcharse confirmation</h1>
 <c:if test="${courseToBuy != null}">
 <table class="table table-striped table-hover col-md-6">
  <thead>
   <tr>
    <th>Course Id</th>
    <th> </th>
    <th>Plane Id</th>
    <th>Plane Company</th>
    <th>From</th>
    <th>To</th>
    <th>BoardTime</th>
    <th> </th>
    <th>Fare</th>
    <th>Remaining Ticket</th>
    <th> </th>
    <th> </th>
   </tr>
  </thead>
  <tbody>
    <tr>
     <td>${courseToBuy.courseId}</td>
     <td> </td>
     <td>${courseToBuy.planeId}</td>
     <td>${courseToBuy.planeCo}</td>
     <td>${courseToBuy.from}</td>
     <td>${courseToBuy.to}</td>
     <td>${courseToBuy.boardTime}</td>
     <td> </td>
     <td>${courseToBuy.fare}</td>
     <td>${courseToBuy.remainingTickets}</td>
     <td>
      <form method="post"
       action="${pageContext.request.contextPath}/BuyCourse/${courseToBuy.courseId}">
       <input class="btn btn-info" type="submit" value="buy" />
      </form>
     </td>
     <td><a href="${pageContext.request.contextPath}/CustomerCourse"
       class="btn btn-default" >return back</a></td>
    </tr>
  </tbody>
 </table>
 </c:if>
</div>
</body>
</html>