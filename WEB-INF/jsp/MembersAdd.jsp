<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>会員の追加</title>
</head>
<body>
 <%-- ログイン機能を実装した場合はコメントを外す --%>
 <%--
 <c:if test="${user != null}">
  <p>
   ログイン中のユーザ: ${fn:escapeXml(user.name)} (<a
    href="${pageContext.request.contextPath}/Logout">ログアウト</a>)
  </p>
  <hr />
 </c:if>
 --%>
 <%-- エラーメッセージがあれば，出力する．複数のメッセージがあることを想定する --%>
 <c:if test="${errorMessages != null}">
  <h1>エラー</h1>
  <c:forEach var="msg" items="${errorMessages}">
   <p>
    <c:out value="${msg}" />
   </p>
  </c:forEach>
  <hr />
 </c:if>
 <h1>会員の追加</h1>
 <form method="post" action="${pageContext.request.contextPath}/Members/add">
  <table>
   <thead>
    <tr>
     <th>氏名</th>
     <th>ポイント数</th>
     <th>加入日</th>
     <th></th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td><input type="text" name="name" value="${nameToEdit}" /></td>
     <td><input type="number" name="points" value="${pointsToEdit}" /></td>
     <td><input type="date" name="joinDate" value="${joinDateToEdit}" /></td>
     <td><input type="submit" value="追加" /></td>
    </tr>
   </tbody>
  </table>
 </form>
 <p>
  <a href="${pageContext.request.contextPath}/Members/list">追加せずに会員一覧表示に戻る</a>
 </p>
</body>
</html>