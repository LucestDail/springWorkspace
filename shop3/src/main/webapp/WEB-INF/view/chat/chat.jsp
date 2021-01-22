<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jspHeader.jsp"%><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Client</title>
<core:set var = "port" value = "${pageContext.request.localPort}"/>
<core:set var = "server" value = "${pageContext.request.serverName}"/>
<core:set var = "path" value = "${pageContext.request.contextPath}"/>
<script type = "text/javascript">
	$(function() {
		var ws = new WebSocket("ws://${server}:${port}${path}/chatting.shop");
		ws.onopen = function(){
			$("#chatStatus").text("info:connection opened")
			$("input[name=chatInput]").on("keydown",function(evt){
				if(evt.keyCode == 13){
					var msg = $("input[name=chatInput]").val();
					ws.send(msg);
					$("input[name=chatInput]").val("");
				}
			})
		}
		ws.onmessage = function(event){
			$("textarea").eq(0).prepend(event.data+"\n");
		}
		ws.onclose = function(event){
			$("$chatStatus").text("info:connection close");
		}
	})
</script>
</head>
<body>
<p><div id="chatStatus"></div>
<textarea name="chatMsg" rows="15" cols="40"></textarea><br>
메시지 입력 : <input type="text" name="chatInput">
</body>
</html>