<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idform</title>
</head>
<body>
<form action="id.shop" method="post">
	<table class = "table">
		<caption>아이디 찾기</caption>
		<tr>
			<th>이메일</th>
			<td>
				<input type = "text" name = "email">
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>
				<input type = "text" name = "tel">
			</td>
		</tr>
		<tr>
			<td colspan = "2">
				<input type = "submit" value = "아이디찾기" class = "btn btn-primary">
			</td>
		</tr>
	</table>
</form>
</body>
</html>