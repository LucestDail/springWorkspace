<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 결과 화면</title>
</head>
<body>
<table class = "table">
	<caption>로그인 성공 : ${sessionScope.loginUser.username}</caption>
	<tr>
		<td>아이디</td>
		<td>
			${sessionScope.loginUser.userid}
		</td>
	</tr>
	<tr>
		<td>이름</td>
		<td>
			${sessionScope.loginUser.username}
		</td>
	</tr>
	<tr>
		<td>우편번호</td>
		<td>
			${sessionScope.loginUser.postcode}
		</td>
	</tr>
	<tr>
		<td>주소</td>
		<td>
			${loginUser.address}
		</td>
	</tr>
	<tr>
		<td>전화번호</td>
		<td>
			${loginUser.phoneno}
		</td>
	</tr>
	<tr>
		<td>이메일</td>
		<td>
			${loginUser.email}
		</td>
	</tr>
	<tr>
		<td>생년월일</td>
		<td>
			<fmt:formatDate value = "${loginUser.birthday}" pattern = "yyy년 MM월 dd일" />
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="상품목록" onclick="location.href='../item/list.shop'" class = "btn btn-primary">
		</td>
	</tr>
</table>
</body>
</html>