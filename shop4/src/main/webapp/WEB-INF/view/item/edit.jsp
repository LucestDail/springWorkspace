<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>
</head>
<body>
<form:form modelAttribute="item" action="update.shop" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<form:hidden path="pictureUrl"/>
	<table class = "table">
		<caption>상품 정보 수정</caption>
		<tr>
			<th>상품명</th>
			<td>
				<form:input path="name" maxlength="20" class="form-control"/>
			</td>
			<td>
				<font color="red">
					<form:errors path="name"/>
				</font>
			</td>
		</tr>
		<tr>
			<th>상품가격</th>
			<td>
				<form:input path="price" maxlength="20" class="form-control"/>
			</td>
			<td>
				<font color="red">
					<form:errors path="price"/>
				</font>
			</td>
		</tr>
		<tr>
			<th>상품이미지</th>
			<td>
				<input type="file" name="picture"/>
			</td>
			<td>
				${item.pictureUrl}
			</td>
		</tr>
		<tr>
			<th>상품설명</th>
			<td>
				<form:textarea path="description" cols="20" rows="5" class="form-control"/>
			</td>
			<td>
				<font color="red">
					<form:errors path="description"/>
				</font>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<input type="submit" value="수정등록" class = "btn btn-primary">&nbsp;
				<input type="button" value="상품목록" onclick="location.href='list.shop'" class = "btn btn-primary">
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>