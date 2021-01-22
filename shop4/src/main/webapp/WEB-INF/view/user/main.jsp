<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<body>
<table class = "table">
	<caption>환영합니다 ${sessionScope.loginUser.username} 님</caption>
	<tr>
		<td>
			<a href="mypage.shop?id=${sessionScope.loginUser.userid}" class = "btn btn-primary">MyPage</a>
			<a href="logout.shop" class = "btn btn-primary">로그아웃</a>
		</td>
	</tr>
</table>
</body>
</html>