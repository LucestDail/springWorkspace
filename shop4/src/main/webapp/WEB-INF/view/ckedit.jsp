<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<script>
	window.parent.CKEDITOR.tools.callFunction(${CKEditorFuncNum},'${fileName}','이미지 업로드 완료');
</script>