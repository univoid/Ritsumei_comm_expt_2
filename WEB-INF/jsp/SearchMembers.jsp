<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>検索結果(複数条件による検索)</title>
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
  <hr />
 </c:if>
 <%-- エラーメッセージの表示: ここまで --%>
 <%-- 検索結果の表示: ここから --%>
 <%-- 検索結果は，result に入っているものとする --%>
 <%-- 検索結果があるかチェック --%>
 <c:if test="${result != null}">
  <h1>検索結果</h1>
  <%-- ここから --%>
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
 <%-- 検索要求部分を表示--%>
 <h1>検索条件</h1>
 <%-- ここから --%>
 <form method="get"
  action="${pageContext.request.contextPath}/SearchMembers">
   <p>氏名に
　　　<input type="text" name="name" value="${fn:escapeXml(param.name)}">
      が含まれ、かつ</p>

   <p>ポイント数が
      <input type="number" name="minPoints" 
       value="${fn:escapeXml(param.minPoints)}">
      以上かつ
      <input type="number" name="maxPoints"
       value="${fn:escapeXml(param.maxPoints)}">
      以下、かつ</p>

   <p>加入日(YYY-MM-DD) が
      <input type="date" name="fromDate"
       value="${fn:escapeXml(param.fromDate)}">
      以降で、かつ
      <input type="date" name="toDate"
       value="${fn:escapeXml(param.toDate)}">
      以前の会員</p>

   <p> 並べ替え：
      <select name = "orderBy">
       <option value = "id"
        ${param.orderBy == 'id' ? 'selceted' : ''}>id</option>
       <option value = "ポイント数"
        ${param.orderBy == 'ポイント数' ? 'selceted' : ''}>ポイント</option>
       <option value = "加入日"
        ${param.orderBy == '加入日' ? 'selceted' : ''}>加入日</option>
      </select>
      <label><input type="radio" name="order" value="ASC"
        ${param.order == 'ASC' ? 'checked' : ''}/>ASC</label>
      <label><input type="radio" name="order" value="DESC"
        ${param.order == 'DESC' ? 'checked' : ''}/>DESC</label>
   </p>

   <p><input type="submit" value="検索"/></p>
 </form>

 <%-- ここまで --%>
</body>
</html>