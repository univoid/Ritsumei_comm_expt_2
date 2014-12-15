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
 <h1 class="text-center">Record List</h1>
 <table class="table table-striped table-hover col-md-6">
  <thead>
   <tr>
    <th>Record Id</th>
    <th>User Name</th>
    <th>BuyTime</th>
    <th> </th>
    <th>Plane Id</th>
    <th>Plane Company</th>
    <th>From</th>
    <th>To</th>
    <th>BoardTime</th>
   </tr>
  </thead>
  <tbody>
   <c:forEach var="record" items="${result}">
    <tr>
     <td>${record.recordId}</td>
     <td>${record.userName}</td>
     <td>${record.buyTime}</td>
     <td> </td>
     <td>${record.planeId}</td>
     <td>${record.planeCo}</td>
     <td>${record.from}</td>
     <td>${record.to}</td>
     <td>${record.boardTime}</td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 <p class="text-center text-info">${fn:length(result)} Records found．</p>
 <hr />
 <form class="form-horizontal" role="form" method="get"
  action="${pageContext.request.contextPath}/AdminRecord">
   <div class="form-group">
      <label class="col-md-3 control-label">User Name:</label>
      <div class="col-md-6"> 
      <input class="form-control" type="text" name="name">
      </div>
   </div>
   <div class="form-group">
      <label class="col-md-3 control-label">Plane Id:</label> 
      <div class="col-md-6">
      <input class="form-control" type="text" name="id" >
      </div>
   </div>
   <div class="form-group">
      <label class="col-md-3 control-label">Company:</label>
      <div class="col-md-6">
      <select class="form-control" name="company">
        <option> </option>
        <option value="MU">MU</option>
        <option value="JAL">JAL</option>
        <option value="ANA">ANA</option>
      </select>
      </div>
   </div>
   <div class="form-group">
      <label class="col-md-3 control-label">From & To</label> 
      <div class="col-md-3">
      <select class="form-control" name="from">
        <option> </option>
        <option value="osaka">osaka</option>
        <option value="tokyo">tokyo</option> 
        <option value="nagoya">nagoya</option>
        <option value="shanghai">shanghai</option>
      </select>　
      </div>
      <div class="col-md-3">
      <select class="form-control" name="to">
        <option value = ""> </option>
        <option value="osaka">osaka</option>
        <option value="tokyo">tokyo</option>
        <option value="nagoya">nagoya</option>
        <option value="shanghai">shanghai</option>
      </select>
      </div>
   </div>

   <div class="form-group">
      <label class="col-md-3 control-label">Sort by:</label>
      <div class="col-md-3">
      <select class="form-control" name = "orderBy">
        <option value = ""> </option>
        <option name = "BuyTime" value = "records.time">Buy Time</option>
        <option name = "BoardTime" value = "courses.time">Board Time</option>
      </select>
      </div>
      <label class="col-md-1 radio-inline control-label">
        <input type="radio" name="order" value="ASC"
        ${param.order == 'ASC' ? 'checked' : ''}>ASC
      </label>
      <label class="col-md-1 radio-inline control-label">
        <input type="radio" name="order" value="DESC"
        ${param.order == 'DESC' ? 'checked' : ''}>DSA
      </label>
   </div>
   <div class="form-group">
      <input class="col-md-offset-4 btn btn-primary btn-lg" type="submit" value="submit check" />
      <a href="${pageContext.request.contextPath}/AdminMainPage"
       class="col-md-offset-1 btn btn-info btn-lg" >　return back　</a>
   </div>
 </form>
</div>
</body>
</html>