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
	<caption>${user.username} 님의 개인정보</caption>
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
		<td>
			<input type="button" value="상품목록" onclick="location.href='../item/list.shop'" class = "btn btn-primary">
			<input type="button" value="정보수정" onclick="location.href='userUpdate.shop?id=${user.userid}'" class = "btn btn-primary">
		</td>
	</tr>
</table>
</body>
</html>