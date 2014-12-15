<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<title>Course List</title>
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
 <h1 class="text-center">Course List</h1>
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
   </tr>
  </thead>
  <tbody>
   <c:forEach var="course" items="${result}">
    <tr>
     <td>${course.courseId}</td>
     <td> </td>
     <td>${course.planeId}</td>
     <td>${course.planeCo}</td>
     <td>${course.from}</td>
     <td>${course.to}</td>
     <td>${course.boardTime}</td>
     <td> </td>
     <td>${course.fare}</td>
     <td>${course.remainingTickets}</td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 <hr />
 <form class="form-horizontal" role="form" method="get" action="${pageContext.request.contextPath}/AddCourse">
  <input class="col-md-offset-2 col-md-3 btn btn-primary btn-lg" type="submit" value="add new course" />
 </form>
 <a href="${pageContext.request.contextPath}/AdminMainPage"
       class="col-md-offset-1 col-md-3 btn btn-info btn-lg" >return back</a>
</div>
</body>
</html>