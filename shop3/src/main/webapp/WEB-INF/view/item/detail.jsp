<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 보기</title>
</head>
<body>
<table class = "table">
	<caption>상품 상세 보기</caption>
	<tr>
		<td>
			<img src = "../img/${item.pictureUrl}">
		</td>
		<td>
			<table>
				<tr>
					<td>상품명</td>
					<td>${item.name}</td>
				</tr>
				<tr>
					<td>가격</td>
					<td>${item.price} 원</td>
				</tr>
				<tr>
					<td>상품설명</td>
					<td>${item.description}</td>
				</tr>
				<tr>
					<td colspan = "2">
						<form action="../cart/cartAdd.shop">
						<input type = "hidden" name = "id" value="${item.id}">
							<table>
								<tr>
									<td>
										<select name="quantity" class="form-control">
											<core:forEach begin="1" end="10" var="i">
												<option>
													${i}
												</option>
											</core:forEach>
										</select>
									</td>
									<td>
										<input type="submit" value="장바구니" class = "btn btn-primary">
										<input type="button" value="상품목록" onclick="location.href='list.shop'" class = "btn btn-primary">
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>