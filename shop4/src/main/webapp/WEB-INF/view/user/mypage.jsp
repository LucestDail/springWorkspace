<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
<script type = "text/javascript">
	$(document).ready(function(){
		$("#minfo").show();
		$("#oinfo").hide();
		$("#saleLine").each(function(){
			$(this).hide();
			$(this).toggle();
		})
		$("#tab1").addClass("select");
	})
	function disp_div(id,tab){
		$(".info").each(function(){
			$(this).hide();
		})
		$(".tab").each(function(){
			$(this).removeClass("select");
		})
		$("#"+id).show();
		$("#"+tab).addClass("select");
	}
	function list_disp(id){
		$("#"+id).toggle(); //보였다 안보였다
		console.log(id + "toggled");
	}
</script>
<!-- <style type = "text/css">
	.select{
		padding:3px;
		background-color:#0000FF;
	}
	.select>a{
		color:#ffffff;
		text-decoration:none;
		font-weight:bold;
	}
</style>-->
</head>
<body>
<table class = "table">
	<tr>
		<td id = "tab1" class = "tab">
			<a href = "javascript:disp_div('minfo','tab1')">회원정보보기</a>
		</td>
		<core:if test = "${param.id != 'admin' }">
			<td id = "tab2" class = "tab">
				<a href = "javascript:disp_div('oinfo','tab2')">주문정보보기</a>
			</td>
		</core:if>
	</tr>
</table>
<div id = "oinfo" class = "info" style = "display:none; width:100%;">
	<table class = "table">
		<tr>
			<th>주문번호</th>
			<th>주문일자</th>
			<th>총주문금액</th>
		</tr>
		<core:forEach items = "${salelist}" var = "sale" varStatus = "stat">
			<tr>
				<td align = "center">
					<a href = "javascript:list_disp('saleLine${stat.index}')">${sale.saleid}</a>
				</td>
				<td align = "center">
					<fmt:formatDate value = "${sale.saledate}" pattern = "yyyy-MM-dd" />
				</td>
				<td align = "right">
					<fmt:formatNumber value = "${sale.total}" pattern = "#,###" /> 원
				</td>
			</tr>
			<tr id = "saleLine${stat.index}" class = "saleLine">
				<td colspan = "3" align = "center">
					<table class = "table">
						<tr>
							<th width = "25%">상품명</th>
							<th width = "25%">상품가격</th>
							<th width = "25%">구매수량</th>
							<th width = "25%">상품총액</th>
						</tr>
						<core:forEach items="${sale.itemList}" var="saleItem">
							<tr>
								<td class = "title">
									${saleItem.item.name}
								</td>
								<td>
									<fmt:formatNumber value = "${saleItem.item.price}" pattern = "#,###" />원
								</td>
								<td>
									${saleItem.quantity} 개
								</td>
								<td>
									<fmt:formatNumber value = "${saleItem.quantity * saleItem.item.price}" pattern = "#,###" /> 원
								</td>
							</tr>
						</core:forEach>
					</table>
				</td>
			</tr>
		</core:forEach>
	</table>
</div>
<div id = "minfo" class = "info">
	<table class = "table">
		<tr>
			<th>아이디</th>
			<td>${user.userid}</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${user.username}</td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td>${user.postcode}</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>${user.phoneno}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${user.address}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${user.email}</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>
				<fmt:formatDate value = "${user.birthday}" pattern="yyyy-MM-dd" />
			</td>
		</tr>
	</table><br>
	<a href = "update.shop?id=${user.userid}" class = "btn btn-primary">정보수정</a>
	<core:if test = "${loginUser.userid != 'admin' }">
		<a href = "delete.shop?id=${user.userid}" class = "btn btn-primary">회원탈퇴</a>
	</core:if>
	<core:if test = "${loginUser.userid == 'admin' }">
		<a href = "../admin/list.shop" class = "btn btn-primary">회원목록</a>
	</core:if>
	<core:if test = "${loginUser.userid == 'admin' }">
		<a href = "../admin/endlist.shop" class = "btn btn-primary">주문목록</a>
	</core:if>
</div>
</body>
</html>