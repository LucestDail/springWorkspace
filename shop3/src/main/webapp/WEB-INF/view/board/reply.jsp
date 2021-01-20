<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 답글 쓰기</title>
</head>
<body>
<form:form modelAttribute="board" action="reply.shop" method="post" name="f">
	<form:hidden path="num"/>
	<form:hidden path="grp"/>
	<form:hidden path="grplevel"/>
	<form:hidden path="grplevel"/>
	<table>
		<caption>게시판 답글 등록</caption>
		<tr>
			<th>
				글쓴이
			</th>
			<td>
				<input type = "text" name = "name">
				<font color = "red">
					<form:errors path = "name"/>
				</font>
			</td>
		</tr>
		<tr>
			<th>
				비밀번호
			</th>
			<td>
				<form:password path = "pass"/>
				<font color = "red">
					<form:errors path = "pass"/>
				</font>
			</td>
		</tr>
		<tr>
			<th>
				제목
			</th>
			<td>
				<form:input path="subject" value="RE:${board.subject}"/>
				<font color = "red">
					<form:errors path = "subject"/>
				</font>
			</td>
		</tr>
		<tr>
			<th>
				내용
			</th>
			<td>
				<textarea rows="15" cols="80" name = "content"></textarea>
				<font color = "red">
					<form:errors path = "name"/>
				</font>
			</td>
		</tr>
		<script>
			CKEDITOR.replace("content",{filebrowserImageUploadUrl : "imgupload.shop"});
		</script>
		<tr>
			<td colspan = "2">
				<a href="javascript:document.f.submit()">답변글등록</a>
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>