<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>検索結果</title>
</head>
<body>
 <%-- 検索結果は，result に入っているものとする --%>
 <%-- 検索結果があるかチェック --%>
 <c:if test="${result != null}">
  <h1>検索結果</h1>
  <%-- 特殊文字をエスケープするライブラリ関数を使用 --%>
  <%-- バージョン2では，特殊文字をエスケープするライブラリ関数 fn:escapeXml を使用する  --%>
  <%-- <p>検索条件: ${fn:escapeXml(param.name)}</p> --%>
  <p>検索条件: ${param.name}</p>
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
      <%-- 特殊文字をエスケープするライブラリ関数を使用 --%>
      <%-- バージョン2では，特殊文字をエスケープするライブラリ関数 fn:escapeXml を使用する  --%>
      <%-- <td>${fn:escapeXml(member.name)}</td> --%>
      <td>${fn:escapeXml(member.name)}</td>
      <td>${fn:escapeXml(member.points)}</td>
      <td>${fn:escapeXml(member.joinDate)}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
  <p>${fn:length(result)}件見つかりました．</p>
  <hr />
 </c:if>
 <%-- 検索要求部分の表示: ここから --%>
 <h1>名前による検索</h1>
 <form method="get"
  action="${pageContext.request.contextPath}/SearchMembersByName">
  <%-- バージョン2では，特殊文字をエスケープするライブラリ関数 fn:escapeXml を使用する  --%>
  <%-- <input type="text" name="name" value="${fn:escapeXml(param.name)}"> --%>
  <input type="text" name="name" value="${fn:escapeXml(param.name)}">
  <input type="submit" value="検索">
 </form>
 <%-- 検索要求部分の表示: ここまで --%>
</body>
</html>