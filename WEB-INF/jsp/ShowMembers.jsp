<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>会員の一覧の表示(並べ替え)</title>
</head>
<body>
 <h1>会員の一覧の表示(並べ替え)</h1>
 <%-- 条件文の記述例 --%>
 <c:if test="${result == null}">
  <p>resultが見つかりません．</p>
 </c:if>
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
   <%-- resultの個々の結果を変数memberに入れる --%>
   <c:forEach var="member" items="${result}">
    <tr>
     <%-- memberのidを取得 --%>
     <td>${member.id}</td>
     <%-- memberの氏名を取得 --%>
     <td>${member.name}</td>
     <%-- memberのポイント数を取得 --%>
     <td ${(member.points >= pointsThreshold) ?
	'style="background-color: #ffccff"': ''}>${member.points}</td>
     <%-- memberの加入日を取得 --%>
     <td>${member.joinDate}</td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 <%-- リストの長さを求める関数(fn:length)を使用する --%>
 <p>${fn:length(result)}件見つかりました．</p>
 
 <form method="get"
  action="${pageContext.request.contextPath}/ShowMembers">
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

  <p>
   <label> ポイント数閾値：
    <input type = "number" name = "pointsThreshold" value = "pointsThreshold"/></label>
  </p>
  <input type="submit" value="再検索"/>
 </form>


 <%-- ここまで --%>
</body>
</html>