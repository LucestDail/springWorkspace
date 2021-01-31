<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
</head>
<body>
<table class = "table">
	<caption>장바구니</caption>
	<tr>
		<td colspan = "4"> 장바구니 상품 목록</td>
	</tr>
	<tr>
		<th>상품명</th>
		<th>가격</th>
		<th>수량</th>
		<th>합계</th>
	</tr>
	<core:set var = "tot" value = "${0}"/>
	<core:forEach items = "${cart.itemSetList}" var = "itemSet" varStatus = "stat">
		<tr>
			<td>${itemSet.item.name}</td>
			<td>${itemSet.item.price}</td>
			<td>${itemSet.quantity}</td>
			<td>
				${itemSet.quantity * itemSet.item.price}
				<core:set var = "tot" value = "${tot + (itemSet.quantity * itemSet.item.price)}"/>
				<a href = "cartDelete.shop?index=${stat.index}">X</a>
			</td>
		</tr>
	</core:forEach>
	<tr>
		<td colspan = "4" align = "right">
			총 구매 금액 : ${tot} 원
		</td>
	</tr>
</table>
<br>
${message}
<br>
<a href = "../item/list.shop"  class = "btn btn-primary"> 상품목록 </a>
<a href = "checkout.shop" class = "btn btn-primary"> 주문하기</a>
</body>
</html>