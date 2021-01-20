<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세 보기</title>
</head>
<body>
	<table class = "table">
		<tr>
			<th>글쓴이</th>
			<td>${board.name}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${board.subject}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${board.content}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
				<core:if test = "${empty board.fileurl}">
					<td><a href="#">첨부파일 없음</a></td>
				</core:if>
				<core:if test = "${!empty board.fileurl}">
					<td><a href="../img/${board.fileurl}">${board.fileurl}</a></td>
				</core:if>
		</tr>
		<tr>
			<td colspan = "2">
				<a href = "reply.shop?num=${board.num}" class = "btn btn-primary">답변</a>
				<a href = "update.shop?num=${board.num}" class = "btn btn-primary">수정</a>
				<a href = "delete.shop?num=${board.num}" class = "btn btn-primary">삭제</a>
				<a href = "list.shop" class = "btn btn-primary">목록</a>
			</td>
		</tr>
	</table>
</body>
</html>