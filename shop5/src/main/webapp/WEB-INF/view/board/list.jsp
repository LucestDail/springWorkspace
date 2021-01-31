<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<script type="text/javascript">
function listpage(page){
	document.searchform.pageNum.value=page;
	document.searchform.submit();
}
</script>
</head>
<body>
	<table class = "table">
		<core:if test="${listcount > 0}">
			<tr>
				<td colspan = "4">Spring 게시판</td>
				<td>글개수 : ${listcount}</td>
			</tr>
			<th>번호</th>
			<th>제목</th>
			<th>글쓴이</th>
			<th>날짜</th>
			<th>조회수</th>
			<core:forEach var="board" items="${boardlist}">
				<tr>
					<td>${boardno}</td>
					<core:set var = "boardno" value="${boardno-1}"/>
					<td style="text-align:left;">
						<core:if test="${!empty board.fileurl}">
							<a href="../img/${board.fileurl}">@</a>
						</core:if>
						<core:if test="${empty board.fileurl}">
							&nbsp;&nbsp;&nbsp;
						</core:if>
						
						<core:forEach begin="1" end="${board.grplevel}">
							&nbsp;&nbsp;
						</core:forEach>
						<core:if test="${board.grplevel > 0}">
							└
						</core:if>
						<a href="detail.shop?num=${board.num}">${board.subject}</a>
					</td>
					<td>
						${board.name}
					</td>
					<td>
						<fmt:formatDate value="${board.regdate}" pattern="yyyyMMdd" var="rdate"/>
						<core:if test="${today == rdate}">
							<fmt:formatDate value="${board.regdate}" pattern="HH:mm:ss"/>
						</core:if>
						<core:if test="${today != rdate}">
							<fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</core:if>
					</td>
					<td>
						${board.readcnt}
					</td>
				</tr>
			</core:forEach>
			<tr>
				<td colspan="5" align="center">
					<core:if test="${pageNum > 1}">
						<a href="javascript:listpage('${pageNum - 1}')">[이전]</a>
					</core:if>
					<core:if test="${pageNum <= 1}">
						[이전]
					</core:if>
					<core:forEach var="a" begin="${startpage}" end="${endpage}">
						<core:if test="${a == pageNum}">
							[${a}]
						</core:if>
						<core:if test="${a != pageNum}">
							<a href="javascript:listpage('${a}')">
								[${a}]
							</a>
						</core:if>
					</core:forEach>
					<core:if test="${pageNum < maxpage}">
						<a href="javascript:listpage('${pageNum + 1}')">[다음]</a>
					</core:if>
					<core:if test="${pageNum >= maxpage}">
						[다음]
					</core:if>
				</td>
			</tr>
		</core:if>
		<core:if test="${listcount == 0}">
			<tr>
				<td colspan="5">
					등록된 게시물이 없습니다.
				</td>
			</tr>
		</core:if>
		<tr>
			<td colspan = "5" align="right">
				<a href="write.shop" class = "btn btn-primary">글쓰기</a>
			</td>
		</tr>
				<tr>
			<td colspan="5">
				<div style="display: inline;">
					<form action="list.shop" method="post" name="searchform">
						<input type="hidden" name="pageNum" value="1">
						<select name="searchtype" class="form-control">
							<option value="">선택하세요</option>
							<option value="subject">제목</option>
							<option value="name">작성자</option>
							<option value="content">내용</option>
						</select>
						<script type = "text/javascript">
							searchform.searchtype.value="${param.searchtype}";
						</script>
						<input type="text" name="searchcontent" value="${param.searchcontent}" class = "form-control">
						<input type="submit" value="검색" class="btn btn-primary">
						<input type="button" value="전체게시물보기" onclick="location.href='list.shop'" class="btn btn-primary">
					</form>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>