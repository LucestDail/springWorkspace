<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 전 상품 목록 보기</title>
</head>
<body>
<table class = "table">
	<caption>배송지 정보</caption>
	<tr>
		<td width = "30%">주문아이디</td>
		<td width = "70%">${sessionScope.loginUser.userid}</td>
	</tr>
	<tr>
		<td width = "30%">이름</td>
		<td width = "70%">${sessionScope.loginUser.username}</td>
	</tr>
	<tr>
		<td width = "30%">우편번호</td>
		<td width = "70%">${sessionScope.loginUser.postcode}</td>
	</tr>
	<tr>
		<td width = "30%">주소</td>
		<td width = "70%">${sessionScope.loginUser.address}</td>
	</tr>
	<tr>
		<td width = "30%">전화번호</td>
		<td width = "70%">${sessionScope.loginUser.phoneno}</td>
	</tr>
</table>
<table class = "table">
	<caption>구매 상품</caption>
	<tr>
		<th>상품명</th>
		<th>가격</th>
		<th>수량</th>
		<th>합계</th>
	</tr>
	<core:forEach items = "${sessionScope.CART.itemSetList}" var = "itemSet" varStatus="stat">
		<tr>
			<td>${itemSet.item.name}</td>
			<td>${itemSet.item.price}</td>
			<td>${itemSet.quantity}</td>
			<td>${itemSet.item.price * itemSet.quantity}</td>
		</tr>
	</core:forEach>
	<tr>
		<td colspan = "4" align = "right">
			총 구매 금액 : ${sessionScope.CART.total} 원
		</td>
	</tr>
	<tr>
		<td colspan = "4">
			<a href = "end.shop" class = "btn btn-primary">주문 확정</a>&nbsp;
			<a href = "../item/list.shop" class = "btn btn-primary">상품 목록</a>&nbsp;
		</td>
	</tr>
</table>
</body>
</html>