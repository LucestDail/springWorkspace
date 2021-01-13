<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록</title>
</head>
<body>
<a href = "create.shop" class = "btn btn-primary">상품 등록</a>
<a href = "../cart/cartView.shop" style = "float:right;" class = "btn btn-primary"> 장바구니</a>
<table class = "table table-hover">
	<caption>${sessionScope.loginUser.username} 님의 상품목록</caption>
	<tr>
		<th width = "80">상품 ID</th>
		<th width = "320">상품명</th>
		<th width = "100">가격</th>
		<th width = "80">수정</th>
		<th width = "80">삭제</th>
	</tr>
	<core:forEach items = "${itemList}" var = "item">
	<tr>
		<td align = "center">${item.id}</td>
		<td align = "left">
			<a href = "detail.shop?id=${item.id}">${item.name}</a>
		</td>
		<td align = "right">
			<fmt:formatNumber value = "${item.price}" type = "CURRENCY" currencySymbol = ""/>원
		</td>
		<td align = "center">
			<a href = "edit.shop?id=${item.id}">수정</a>
		</td>
		<td align = "center">
			<a href = "confirm.shop?id=${item.id}">삭제</a>
		</td>
	</tr>
	</core:forEach>
	<tr>
		<td colspan = "5" align = "center">
			<input type="button" value="로그인화면" onclick="location.href='../user/login.shop'" class = "btn btn-primary">
       		<input type="button" value="가입화면" onclick="location.href='../user/userEntry.shop'" class = "btn btn-primary">
		</td>
	</tr>
</table>
</body>
</html>