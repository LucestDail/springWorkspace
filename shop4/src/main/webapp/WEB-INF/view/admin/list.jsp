<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 회원 목록 </title>
<script type = "text/javascript">
	function allchkbox(allchk){
		$(".idchks").prop("checked",allchk.checked)
	}
</script>
</head>
<body>
<form action = "mailForm.shop" method = "post">
	<table class="table">
		<tr>
			<th colspan = "7">
				회원 목록
			</th>
		</tr>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>전화</th>
			<th>생일</th>
			<th>이메일</th>
			<th>&nbsp;</th>
			<th><input type = "checkbox" name = "allchk" onchange="allchkbox(this)"></th>
		</tr>
		<core:forEach items="${list}" var="user">
			<tr>
				<td>${user.userid}</td>
				<td>${user.username}</td>
				<td>${user.phoneno}</td>
				<td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td>
				<td>${user.email}</td>
				<td>
					<a href="../user/update.shop?id=${user.userid}" class = "btn btn-primary">수정</a>
					<a href="../user/delete.shop?id=${user.userid}" class = "btn btn-primary">강제탈퇴</a>
					<a href="../user/mypage.shop?id=${user.userid}" class = "btn btn-primary">회원정보</a>
				</td>
				<td>
					<input type = "checkbox" name="idchks" class="idchks" value="${user.userid}">
				</td>
			</tr>
		</core:forEach>
		<tr>
			<td colspan="7" align="right">
				<input type="submit" value="메일보내기" class = "btn btn-primary">
			</td>
		</tr>
	</table>
</form>
</body>
</html>