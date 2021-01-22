<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>
<form:form modelAttribute="board" action="write.shop" enctype="multipart/form-data" name="f">
	<table class="table">
		<caption>게시물 작성</caption>
		<tr>
			<th>
				글쓴이
			</th>
			<td>
				<form:input path="name" class="form-control" />
			</td>
			<td>
				<font color="red">
					<form:errors path="name" />
				</font>
			</td>
		</tr>
		<tr>
			<th>
				비밀번호
			</th>
			<td>
				<form:password path="pass" class="form-control" />
			</td>
			<td>
				<font color="red">
					<form:errors path="pass" />
				</font>
			</td>
		</tr>
		<tr>
			<th>
				제목
			</th>
			<td>
				<form:input path="subject" class="form-control" />
			</td>
			<td>
				<font color="red">
					<form:errors path="subject" />
				</font>
			</td>
		</tr>
		<tr>
			<th>
				내용
			</th>
			<td>
				<form:textarea path="content" rows = "15" class="form-control" />
			</td>
			<td>
				<font color="red">
					<form:errors path="content" />
				</font>
			</td>
		</tr>
		<script>
			CKEDITOR.replace("content")
		</script>
		<tr>
			<th>
				첨부파일
			</th>
			<td>
				<input type = "file" name = "file1" class="form-control-file">
			</td>
		</tr>
		<tr>
			<td colspan = "2" align = "center">
				<input type = "submit" value = "등록" class = "btn btn-primary">
				<a href="list.shop" class="btn btn-primary">게시글 목록</a>
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>