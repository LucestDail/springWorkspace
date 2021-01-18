<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 보내기</title>
<script type="text/javascript">
	function idinputchk(f){
		if(f.naverid.value == ""){
			alert("네이버 아이디를 입력하세요");
			f.naverid.focus();
			return false;
		}
		if(f.naverpw.value == ""){
			alert("네이버 비밀번호를 입력하세요");
			f.naverpw.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<form name = "mailform" method="post" action="mail.shop" enctype="multipart/form-data" onsubmit="return idinputchk(this)">
<table class = "table">
	<caption>메일 보내기</caption>
	<tr>
		<th>
			네이버 ID
		</th>
		<td>
			<input type = "text" name = "naverid" class = "form-control">
		</td>
	</tr>
	<tr>
		<th>
			네이버 PW
		</th>
		<td>
			<input type = "password" name = "naverpw" class = "form-control">
		</td>
	</tr>
	<tr>
		<th>
			보내는 사람
		</th>
		<td>
			${loginUser.email}
		</td>
	</tr>
	<tr>
		<th>
			받는 사람
		</th>
		<td>
			<input type = "text" class = "form-control" name = "recipient" size="100" value='<core:forEach items="${list}" var="user">${user.username}&lt;${user.email}&gt;,</core:forEach>'>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>
			<input type="text" name="title" size="100" class = "form-control">
		</td>
	</tr>
	<tr>
		<th>메시지 형식</th>
		<td>
			<select name="mtype" class="form-control">
				<option value="text/html; charset=utf-8">HTML</option>
				<option value="text/plain; charset=utf-8">TEXT</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>첨부파일1</th>
		<td>
			<input type="file" name="file1" class="form-control">
		</td>
	</tr>
	<tr>
		<th>첨부파일2</th>
		<td>
			<input type="file" name="file1" class="form-control">
		</td>
	</tr>
	<tr>
		<td colspan = "2">
			<textarea name="contents" rows="10" cols="120">
			</textarea>
			<script>CKEDITOR.replace("contents")</script>
		</td>
	</tr>
	<tr>
		<td colspan = "2">
			<input type = "submit" value = "메일보내기" class = "btn btn-primary">
		</td>
	</tr>
</table>
</form>
</body>
</html>