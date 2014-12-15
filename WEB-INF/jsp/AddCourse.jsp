<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<title>Add New Course</title>
</head>
<body>
 <c:if test="${user != null}">
  <p class="bg-success">User logining: ${fn:escapeXml(user.name)}
  (<a href="${pageContext.request.contextPath}/Logout">loginout</a>)
  </p>
  <hr /> 
 </c:if>
<div class="container col-md-offset-2 col-md-8">
 <c:if test="${errorMessages != null}">
  <h1 class="text-danger text-center">Error</h1>
  <c:forEach var="msg" items="${errorMessages}">
   <p class="bg-danger text-danger text-center">
    <c:out value="${msg}" />
   </p>
  </c:forEach>
 </c:if>
 <h1 class="text-center">Add New Course</h1>
 <form class="form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/AddCourse">
  <table class="table table-hover col-md-4">
   <thead>
    <tr>
     <th>Plane Id</th>
     <th>Board Time</th>
     <th>Sum of Tickets</th>
     <th> </th>
     <th> </th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td><input type="number" name="planeId"/></td>
     <td><input type="time" name="boardTime"/></td>
     <td><input type="number" name="sumOfTickets"/></td>
     <td><input class="btn btn-info" type="submit" value="　SUBMIT　" /></td>
     <td><a href="${pageContext.request.contextPath}/AdminCourse"
       class="btn btn-default" >return back</a></td>
    </tr>
   </tbody>
  </table>
  <p class="text-center text-info">hint: Standard form of boardTime: YYYY-MM-DD HH:MM:SS</p>
 </form>
</body>
</html>