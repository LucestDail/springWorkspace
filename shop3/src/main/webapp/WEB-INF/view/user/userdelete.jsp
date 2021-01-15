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
<form:form modelAttribute="user" method="post" action="userdelete.shop" name="deletefrom">
<spring:hasBindErrors name="user">
        <font color="red">
            <core:forEach items="${errors.globalErrors}" var="error">
                <spring:message code="${error.code}" />
            </core:forEach>
        </font>
    </spring:hasBindErrors>
    <table class = "table">
    <tr>
    
    </tr>
	<input type = "hidden" name = "userid" value="${param.id}">
	비밀번호<input type = "password" name = "password">
	<a href="javascript:deletefrom.submit()">회원탈퇴</a>
	</table>
</form:form>
</body>
</html>