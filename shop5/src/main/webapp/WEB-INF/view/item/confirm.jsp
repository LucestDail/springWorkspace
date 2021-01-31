<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 삭제 전 확인</title>
</head>
<body>
<table class = "table">
	<caption>상품 삭제 전 확인</caption>
	<tr>
		<td>
			<img src = "../img/${item.pictureUrl}">
		</td>
		<td>
			<table>
				<tr>
					<th>상품명</th>
					<td>${item.name}</td>
				</tr>
				<tr>
					<th>가격</th>
					<td>${item.price}</td>
				</tr>
				<tr>
					<th>상품설명</th>
					<td>${item.description}</td>
				</tr>
				<tr>
					<td colspan = "2">
						<input type = "button" value = "상품삭제" onclick="location.href='delete.shop?id=${item.id}'" class="btn btn-danger">
						<input type = "button" value = "상품목록" onclick="location.href='list.shop'" class = "btn btn-primary">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>