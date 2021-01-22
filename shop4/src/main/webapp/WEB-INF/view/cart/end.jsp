<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 확정 상품</title>
</head>
<body>
<table class = "table">
	<caption>배송지 정보</caption>
	<tr>
		<td colspan = "2">
			${sale.user.username} 님이 주문하신 정보입니다.
		</td>
	</tr>
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
	<caption>주문 완료 상품</caption>
	<tr>
		<th>상품명</th>
		<th>가격</th>
		<th>수량</th>
		<th>합계</th>
	</tr>
	<core:forEach items = "${sale.itemList}" var = "saleitem">
		<tr>
			<td>${saleitem.item.name}</td>
			<td>${saleitem.item.price}</td>
			<td>${saleitem.quantity}</td>
			<td>${saleitem.item.price * saleitem.quantity}</td>
		</tr>
	</core:forEach>
	<tr>
		<td colspan = "4" align = "right">
			총 구매 금액 : <fmt:formatNumber value="${total}" pattern="###,###"/> 원
		</td>
	</tr>
	<tr>
		<td colspan = "4">
			<a href = "../item/list.shop" class = "btn btn-primary">상품 목록</a>&nbsp;
		</td>
	</tr>
</table>
</body>
</html>