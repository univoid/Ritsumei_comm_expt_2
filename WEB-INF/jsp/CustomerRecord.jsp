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
 <h1 class="text-center">Record List of ${user.name}</h1>
 <table class="table table-striped table-hover col-md-6">
  <thead>
   <tr>
    <th>Record Id</th>
    <th>BuyTime</th>
    <th> </th>
    <th>Plane Id</th>
    <th>Plane Company</th>
    <th>From</th>
    <th>To</th>
    <th>BoardTime</th>
    <th> </th>
   </tr>
  </thead>
  <tbody>
   <c:forEach var="record" items="${result}">
    <tr>
     <td>${record.recordId}</td>
     <td>${record.buyTime}</td>
     <td> </td>
     <td>${record.planeId}</td>
     <td>${record.planeCo}</td>
     <td>${record.from}</td>
     <td>${record.to}</td>
     <td>${record.boardTime}</td>
     <td>
      <form method="get"
       action="${pageContext.request.contextPath}/DeleteRecord/${record.recordId}">
       <input class="btn btn-warning" type="submit" value="delete"/>
      </form>
     </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 <a href="${pageContext.request.contextPath}/CustomerMainPage"
       class="col-md-offset-4 col-md-4 btn btn-default btn-lg">return back</a>
</div>
</body>
</html>