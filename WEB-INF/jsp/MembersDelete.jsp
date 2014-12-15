<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exp2_default.css">
<title>会員の削除確認</title>
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
 <h1>削除の確認</h1>
 <%-- 削除確認データがあるかチェック --%>
 <%-- 削除対象の会員のデータは，memberToDeleteに渡されるとする --%>
 <c:if test="${memberToDelete != null}">
  <table>
   <thead>
    <tr>
     <th>id</th>
     <th>氏名</th>
     <th>ポイント数</th>
     <th>加入日</th>
     <th></th>
    </tr>
   </thead>
   <tbody>
    <tr>
     <td>${memberToDelete.id}</td>
     <%-- 特殊文字をエスケープするライブラリ関数を使用 --%>
     <td>${fn:escapeXml(memberToDelete.name)}</td>
     <td>${memberToDelete.points}</td>
     <td>${memberToDelete.joinDate}</td>
     <td>
      <form method="post"
       action="${pageContext.request.contextPath}/Members/delete/${memberToDelete.id}">
       <input type="submit" value="削除" />
      </form>
     </td>
    </tr>
   </tbody>
  </table>
 </c:if>
 <%-- 削除対象の会員のデータがない場合 --%>
 <c:if test="${memberToDelete == null}">
  <p>削除対象がありません．</p>
 </c:if>
 <p>
  <a href="${pageContext.request.contextPath}/Members/list">削除せずに会員一覧表示に戻る</a>
 </p>
</body>
</html>