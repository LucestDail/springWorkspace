<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pw result</title>
</head>
<body>
<table class = "table">
	<tr>
		<td>
			${pass}
		</td>
	</tr>
	<tr>
		<td colspan = "2">
			<input type = "button" value = "닫기" onclick = "self.close()" class = "btn btn-primary">
		</td>
	</tr>
</table>
</body>
</html>