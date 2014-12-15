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
 <h1 class="text-center">delete confirmation</h1>
 <c:if test="${recordToDelete != null}">
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
     <th> </th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td>${recordToDelete.recordId}</td>
     <td>${recordToDelete.buyTime}</td>
     <td> </td>
     <td>${recordToDelete.planeId}</td>
     <td>${recordToDelete.planeCo}</td>
     <td>${recordToDelete.from}</td>
     <td>${recordToDelete.to}</td>
     <td>${recordToDelete.boardTime}</td>
     <td>
      <form method="post"
       action="${pageContext.request.contextPath}/DeleteRecord/${recordToDelete.recordId}">
       <input class="btn btn-danger" type="submit" value="delete" />
      </form>
     </td>
     <td><a href="${pageContext.request.contextPath}/CustomerRecord"
       class="btn btn-default" >return back</a></td>
    </tr>
    </tr>
   </tbody>
  </table>
 </c:if>
</div>
</body>
</html>