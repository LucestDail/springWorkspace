<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "core" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "decorator" uri = "http://www.opensymphony.com/sitemesh/decorator" %>
<core:set var = "path" value = "${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title /></title>
<decorator:head />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">

<style>
	html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
</head>
<body class="w3-light-grey">
<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>자바/스프링 기반 빅데이터 플랫폼 개발자 양성과정 모델2 회원관리 연습</button>
  <span class="w3-bar-item w3-right">
  <core:if test = "${empty sessionScope.loginUser}"><a href = "${path}/user/login.shop"> 로그인 </a><a href = "${path}/user/userEntry.shop"> 회원가입 </a>
  </core:if>
  <core:if test = "${!empty sessionScope.loginUser}"><a href = "${path}/user/logout.shop">로그아웃</a></core:if>
  </span>
</div>
<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="picture/${sessionScope.picture}" class="w3-circle w3-margin-right" style="width:46px">
    </div>
    <div class="w3-col s8 w3-bar">
      <core:if test = "${!empty sessionScope.loginUser}">
      <span>환영합니다! <strong>${sessionScope.login}</strong>님</span><br>
      </core:if>
      <core:if test = "${empty sessionScope.loginUser}">
      <span><strong>로그인하세요</strong></span><br>
      </core:if>
    </div>
  </div>
  <hr>
  <div class="w3-container">
    <h5>Dashboard</h5>
  </div>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>Close Menu</a>
    <a href="${path}/user/main.shop" class="w3-bar-item w3-button w3-padding"><i class="fa fa-eye fa-fw"></i>회원관리</a>
    <a href="${path}/board/list.shop" class="w3-bar-item w3-button w3-padding"><i class="fa fa-users fa-fw"></i>게시판</a>
    <a href="${path}/item/list.shop" class="w3-bar-item w3-button w3-padding"><i class="fa fa-users fa-fw"></i>상품목록</a>
    <a href="${path}/chat/chat.shop" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>채팅</a>
  </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">
	<table>
		<tr>
			<td colspan = "2" style = "text-align: left; vertical-align:top;">
				<decorator:body />
			</td>
		</tr>
	</table>
  <!-- Footer -->
  <footer id="myFooter">
    <div class="w3-container w3-theme-l2 w3-padding-32">
      <h4>구디 아카데미 Since 2016</h4>
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="#" target="_blank">Dev Hyun</a></p>
    </div>
  </footer>
  <!-- End page content -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>

</body>
</html>
