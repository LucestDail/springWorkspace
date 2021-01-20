<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 삭제</title>
</head>
<body>
<form:form modelAttribute="board" action="delete.shop" method="post">
<form:hidden path = "num" value = "${param.num}"/>
<table class = "table">
	<tr>
		<th>
			비밀번호 입력
		</th>
		<td>
			<form:password path="pass" class="form-control" />
			<font color="red">
					<form:errors path="pass" />
			</font>
		</td>
	</tr>
	<tr>
		<td colspan = "2" align = "center">
			<input type = "submit" value = "삭제" class = "btn btn-primary">
		</th>
	</tr>
</table>
</form:form>
</body>
</html>