<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>会員一覧</title>
</head>
<body>
 <%-- メッセージがあれば，出力する．(提出課題オプション)--%>
 <c:if test="${message != null}">
  <p>
   <c:out value="${message}" />
  </p>
  <hr />
 </c:if>
 <%-- ログイン機能を実装した場合はコメントを外す --%>

 <c:if test="${user != null}">
  <p>ログイン中のユーザ: ${fn:escapeXml(user.name)}
  (<a href="${pageContext.request.contextPath}/Logout">ログアウト</a>)
  </p>
  <hr />
 </c:if>
 
 <h1>${user.authority}</h1>
 <h1>会員一覧</h1>
 <table>
  <thead>
   <tr>
    <th>id</th>
    <th>氏名</th>
    <th>ポイント数</th>
    <th>加入日</th>
    <th></th>
    <th></th>
   </tr>
  </thead>
  <tbody>
   <%-- 一覧データは result に入っていることを前提 --%>
   <c:forEach var="member" items="${result}">
    <tr>
     <td>${member.id}</td>
     <%-- クロスサイトスクリプティング対策 --%>
     <td>${fn:escapeXml(member.name)}</td>
     <td>${member.points}</td>
     <td>${member.joinDate}</td>
     <%-- 削除ボタン: 削除確認画面を呼び出す (Get メソッドを使用) --%>
     <td>
      <form method="get"
       action="${pageContext.request.contextPath}/Members/delete/${member.id}">
       <input type="submit" value="削除"/>
      </form>
     </td>
      <%-- 編集ボタン: 編集画面を呼び出す (Get メソッドを使用) --%>
     <td>
      <form method="get"
       action="${pageContext.request.contextPath}/Members/update/${member.id}">
       <input type="submit" value="編集" />
      </form>
     </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 <hr />
 <%-- 追加ボタン: 追加情報編集画面を呼び出す --%>
 <form method="get" action="${pageContext.request.contextPath}/Members/add">
  <input type="submit" value="会員の新規追加" />
 </form>
 <hr />
 <form method="get"
  action="${pageContext.request.contextPath}/Members/list">
  <%-- 提出課題： ここから --%>
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
  <%-- 提出課題： ここまで --%>
  <p>
   <input type="submit" value="会員リストの表示" />
  </p>
 </form>
</body>
</html>