<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>結果表示</title>
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
 <%-- メッセージがあれば，出力する．--%>
 <c:if test="${message != null}">
  <p>
   <c:out value="${message}" />
  </p>
  <hr />
 </c:if>
 <p>
  <a href="${pageContext.request.contextPath}/Members/list">会員一覧表示に戻る</a>
 </p>
</body>
</html>