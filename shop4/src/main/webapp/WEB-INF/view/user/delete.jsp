<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>
<table class = "table">
	<tr>
		<th>아이디</th>
		<td>${user.userid}</td>
	</tr>
	<tr>
		<th>이름</th>
		<td>${user.username}</td>
	</tr>
	<tr>
		<th>생년월일</th>
		<td><fmt:formatDate value ="${user.birthday}" pattern="yyyy-MM-dd" /></td>
	</tr>
</table>
<form  method="post" action="delete.shop" name="deletefrom">
	<input type = "hidden" name = "userid" value="${param.id}">
	비밀번호<input type = "password" name = "password" class = "form-control">
	<a href="javascript:deletefrom.submit()" class = "btn btn-primary">회원탈퇴</a>
</form>
</body>
</html>