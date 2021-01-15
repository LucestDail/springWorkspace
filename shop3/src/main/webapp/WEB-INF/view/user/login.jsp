<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
<script type = "text/javascript">
function win_open(page){
	var op = "width = 500, height=350, left=50, top=150";
	open(page + ".shop","",op);
}
</script>
</head>
<body>
<form:form modelAttribute = "user" method = "post" action = "login.shop" name="f">
	<spring:hasBindErrors name="user">
		<font color = "red">
			<core:forEach items = "${errors.globalErrors}" var = "error">
				<spring:message code = "${error.code}" />
			</core:forEach>
		</font>
	</spring:hasBindErrors>
	<table class = "table">
		<caption>사용자 로그인</caption>
		<tr height = "40px">
			<td>아이디</td>
			<td>
				<form:input path = "userid" class = "form-control"/>
				<font color = "red">
					<form:errors path = "userid" />
				</font>
			</td>
		</tr>
		<tr height = "40px">
			<td>비밀번호</td>
			<td>
				<form:input path = "password" class = "form-control"/>
				<font color = "red">
					<form:errors path = "password" />
				</font>
			</td>
		</tr>
		<tr height = "40px">
			<td colspan = "2" align = "center">
				<input type = "submit" value = "로그인" class = "btn btn-primary">
				<input type = "button" value = "회원가입" onclick = "location.href = 'userEntry.shop'" class = "btn btn-primary">
				<input type = "button" value = "아이디찾기" class = "btn btn-primary" onclick = "win_open('idform')">
				<input type = "button" value = "비밀번호찾기" class = "btn btn-primary" onclick = "win_open('pwform')">
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>