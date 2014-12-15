<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>検索結果(ポイント数による検索)</title>
</head>
<body>
 <%-- エラーメッセージの表示: ここから --%>
 <%--　エラーメッセージがあれば，出力する． ここではerrorMessageにエラーメッセージがあると想定している．
また，複数のメッセージがあることを想定する --%>
 <c:if test="${errorMessages != null}">
  <h1>エラー</h1>
  <c:forEach var="msg" items="${errorMessages}">
   <p><c:out value="${msg}"/></p>
  </c:forEach>
  <hr>
 </c:if>
 <%-- エラーメッセージの表示: ここまで --%>
 <%-- 検索結果の表示: ここから --%>
 <%-- 検索結果は，result に入っているものとする --%>
 <%-- 検索結果があるかチェック --%>
 <c:if test="${result != null}">
  <h1>検索結果</h1>
  <p>検索条件: ${param.minPoints}～${param.maxPoints}</p>
  <table>
   <thead>
    <tr>
     <th>id</th>
     <th>氏名</th>
     <th>ポイント数</th>
     <th>加入日</th>
    </tr>
   </thead>
   <tbody>
    <%-- result に検索結果が格納されている --%>
    <c:forEach var="member" items="${result}">
     <tr>
      <td>${member.id}</td>
      <td>${fn:escapeXml(member.name)}</td>
      <td>${fn:escapeXml(member.points)}</td>
      <td>${fn:escapeXml(member.joinDate)}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
  <p>${fn:length(result)}件見つかりました．</p>
  <hr />

  <%-- ここまで --%>
 </c:if>
 <%-- 検索結果の表示: ここまで --%>
 <h1>ポイント数による検索</h1>
 <%-- 検索要求部分を表示：ここから--%>
 <form method="get"
  action="${pageContext.request.contextPath}/SearchMembersByPoints">
   <p>ポイント数</p>
   <input type="number" name="minPoints"
   value="${fn:escapeXml(param.minPoints)}">
   以上、かつ
   <input type="number" name="maxPoints"
   value="${fn:escapeXml(param.maxPoints)}">
   以下の会員を検索</p>
   <p><input type="submit" value="検索"/></p>
 </form>

 <%-- 検索要求部分を表示：ここまで--%>
</body>
</html>