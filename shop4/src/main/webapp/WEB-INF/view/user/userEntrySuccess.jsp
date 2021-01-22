<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 등록 결과 화면</title>
</head>
<body>
<h2>사용자 등록 결과 화면</h2>
<table class = "table">
	<tr>
		<td>아이디</td>
		<td>
			${user.userid}
		</td>
	</tr>
	<tr>
		<td>이름</td>
		<td>
			${user.username}
		</td>
	</tr>
	<tr>
		<td>우편번호</td>
		<td>
			${user.postcode}
		</td>
	</tr>
	<tr>
		<td>주소</td>
		<td>
			${user.address}
		</td>
	</tr>
	<tr>
		<td>전화번호</td>
		<td>
			${user.phoneno}
		</td>
	</tr>
	<tr>
		<td>이메일</td>
		<td>
			${user.email}
		</td>
	</tr>
	<tr>
		<td>생년월일</td>
		<td>
			<fmt:formatDate value = "${user.birthday}" pattern = "yyy년 MM월 dd일" />
		</td>
	</tr>
	<tr>
		<td colspan = "2" align = "center">
			<input type="button" value="로그인화면" onclick="location.href='login.shop'" class = "btn btn-primary">
       		<input type="button" value="가입화면" onclick="location.href='userEntry.shop'" class = "btn btn-primary">
		</td>
	</tr>
</table>
</body>
</html>