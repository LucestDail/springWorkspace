<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 등록</title>
</head>
<body>
<form:form modelAttribute="user" method="post" action="userEntry.shop">
    <spring:hasBindErrors name="user">
        <font color="red">
            <core:forEach items="${errors.globalErrors}" var="error">
                <spring:message code="${error.code}" />
            </core:forEach>
        </font>
    </spring:hasBindErrors>
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
            <td>비밀번호</td>
            <td>
                <form:input path="password" class="form-control"/>
                <font color="red">
                    <form:errors path="password" />
                </font>
            </td>
        </tr>
        <tr height="40px">
            <td>이름</td>
            <td>
                <form:input path="username" class="form-control"/>
                <font color="red">
                    <form:errors path="username" />
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
            <td>우편번호</td>
            <td>
                <form:input path="postcode" class="form-control"/>
                <font color="red">
                    <form:errors path="postcode" />
                </font>
            </td>
        </tr>
        <tr height="40px">
            <td>주소</td>
            <td>
                <form:input path="address" class="form-control"/>
                <font color="red">
                    <form:errors path="address" />
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
            <td>생년월일</td>
            <td>
                <form:input path="birthday" class="form-control"/>
                <font color="red">
                    <form:errors path="birthday" />
                </font>
            </td>
        </tr>
        <tr height="40px">
            <td colspan="2" align = "center">
                <input type="submit" value="등록" class = "btn btn-primary">
                <input type="reset" value="초기화" class = "btn btn-primary">
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>