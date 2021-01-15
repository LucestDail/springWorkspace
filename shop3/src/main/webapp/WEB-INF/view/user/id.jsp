<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>id result</title>
<script type = "text/javascript">
	function idsend(id){
		opener.document.f.userid.value = id;
		self.close();
	}
</script>
</head>
<body>
<table class = "table">
	<tr>
		<td>
			${id}
		</td>
	</tr>
	<tr>
		<td colspan = "2">
			<input type = "button" value = "아이디전송" onclick = "idsend('${id}')" class = "btn btn-primary">
		</td>
	</tr>
</table>
</html>