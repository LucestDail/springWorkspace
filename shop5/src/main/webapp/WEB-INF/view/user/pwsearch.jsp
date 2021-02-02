<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pwsearch</title>
</head>
<body>
<form:form modelAttribute="user" method="post" action="pwsearch.shop">
    <spring:hasBindErrors name="user">
        <font color="red">
            <core:forEach items="${errors.globalErrors}" var="error">
                <spring:message code="${error.code}" />
            </core:forEach>
        </font>
    </spring:hasBindErrors>
    <form:hidden path="password" value = "test"/>
    <form:hidden path="username" value = "test"/>
    <form:hidden path="birthday" value = "1111-11-11"/>
    <table class = "table">
    	<caption>사용자 등록</caption>
    	<tr height="40px">
            <td>아이디</td>
            <td>
                <form:input path="userid" class="form-control"/>
                <font color="red">
                    <form:errors path="userid" />
                </font>
            </td>
        </tr>
    	 <tr height="40px">
            <td>이메일</td>
            <td>
                <form:input path="email" class="form-control"/>
                <font color="red">
                    <form:errors path="email" />
                </font>
            </td>
        </tr>
        <tr height="40px">
            <td>전화번호</td>
            <td>
                <form:input path="phoneno" class="form-control"/>
                <font color="red">
                    <form:errors path="phoneno" />
                </font>
            </td>
        </tr>
         <tr height="40px">
            <td colspan="2" align = "center">
               <input type = "submit" value = "비밀번호찾기" class = "btn btn-primary">
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>