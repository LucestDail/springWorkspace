<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<%@ taglib prefix = "decorator" uri = "http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title /></title>
<decorator:head />
</head>
<body>
<table>
	<tr>
		<td colspan = "3" style = "text-align:right">
			<core:if test="${empty sessionScope.loginUser}">
				<a href = "${path}/user/login.shop">로그인</a>
				<a href = "${path}/user/userEntry.shop">회원가입</a>
			</core:if>
			<core:if test = "${!empty sessionScope.loginUser}">
				${sessionScope.loginUser.username} 님
				<a href = "${path}/user/logout.shop">로그아웃</a>
			</core:if>
		</td>
	</tr>
	<tr>
		<td width="15%" valign="top">
			<a href = "${path}/user/main.shop">회원관리</a><br>
			<a href = "${path}/item/list.shop">상품관리</a><br>
			<a href = "${path}/board/list.shop">게시판</a><br>
			<a href = "${path}/chat/chat.shop">채팅</a><br>
		</td>
		<td colspan = "2" style = "text-align:left; vertical-align:top">
			<decorator:body />
		</td>
	</tr>
	<tr>
		<td colspan = "3">
			구디 아카데미 Since 2016
		</td>
	</tr>
</table>
</body>
</html>