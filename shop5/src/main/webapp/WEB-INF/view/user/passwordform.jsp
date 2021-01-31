<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>passwordform</title>
</head>
<body>
<form action = "passwordform.shop" method = "post">
	<table class = "table">
		<caption>비밀번호 변경</caption>
		<tr>
			<th> 현재 비밀번호</th>
			<td>
				<input type = "password" name = "pass" class = "form-control">
			</td>
		</tr>
		<tr>
			<th> 변경 비밀번호</th>
			<td>
				<input type = "password" name = "chgpass" class = "form-control">
			</td>
		</tr>
		<tr>
			<th> 변경 비밀번호 재입력</th>
			<td>
				<input type = "password" name = "chgpass2" class = "form-control">
			</td>
		</tr>
		<tr>
			<td colspan = "2" align="center">
				<input type = "submit" value = "비밀번호 변경" class = "btn btn-primary">
			</td>
		</tr>
	</table>
</form>
</body>
</html>