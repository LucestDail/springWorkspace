<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %><%-- include 지시어 : 지정 파일을 페이지에 포함, 변수명 공유 --%>
<%@ taglib prefix = "decorator" uri = "http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title /></title>
<script type = "text/javascript">
$(function(){
//	piegraph();
//	bargraph();
//	exchangeRate();
//	exchangeRate2();
})

</script>
<decorator:head />
</head>
<body>
<table>
	<tr>
		<td colspan = "3" style = "text-align:right">
			<core:if test="${empty sessionScope.loginUser}">
				<a href = "${path}/user/login.shop">로그인</a>
				<a href = "${path}/user/userEntry.shop">회원가입</a>
			</core:if>
			<core:if test = "${!empty sessionScope.loginUser}">
				${sessionScope.loginUser.username} 님
				<a href = "${path}/user/logout.shop">로그아웃</a>
			</core:if>
		</td>
	</tr>
	<tr>
		<td width="40%" valign="top">
			<a href = "${path}/user/main.shop">회원관리</a><br>
			<a href = "${path}/item/list.shop">상품관리</a><br>
			<a href = "${path}/board/list.shop">게시판</a><br>
			<a href = "${path}/chat/chat.shop">채팅</a><br>
			<br>  
			<%-- ajax을 통해 얻은 환율 정보 내용 출력 --%>
   			<div id="exchange"></div>
   			<div id="exchange2"></div>
   			<div id="canvas-holder" style="width:50%; height:300px;">
    			 <canvas id="canvas1" width="100%" height="100%"></canvas>
    			 <canvas id="canvas2" width="100%" height="100%"></canvas>
  			 </div>
		</td>
		<td colspan = "2" style = "text-align:left; vertical-align:top">
			<decorator:body />
		</td>
	</tr>
	<tr>
		<td colspan = "3">
			구디 아카데미 Since 2016
		</td>
	</tr>
</table>
<script type="text/javascript" 
  src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script>
var randomColorFactor = function(){
	  return Math.round(Math.random() * 255);//0~255 임의의값
  }
  var randomColor = function(opa) { //임의의 색상 리턴.
	  return "rgba("+ randomColorFactor() + ","
			  + randomColorFactor() + ","
			  + randomColorFactor() + ","
			  + (opa || '.3') + ")";
  }
  $(function(){
//	  piegraph();
//	  bargraph();
//	  exchangeRate();
//	  exchangeRate2(); 
  })
  function exchangeRate() {
	  $.ajax("${path}/ajax/exchange.shop",{
		  success : function(data) {
			  console.log("success exchange");
			  $("#exchange").html(data);
		  },
		  error : function(e){
			  alert("환율 조회시 서버 오류:"+e.status);
		  }
	  })
  }
   function exchangeRate2() {
	  $.ajax("${path}/ajax/exchange2.shop",{
		  success : function(data) {
			  console.log("success exchange2");
			  $("#exchange2").html(data);
		  },
		  error : function(e){
			  alert("환율 조회시 서버 오류:"+e.status);
		  }
	  })
  }
  function piegraph() {
	  $.ajax("${path}/ajax/graph.shop",{
		  success : function(data) { //글쓴이별 게시글 등록 건수
// data : [{"name":"홍길동","cnt":5},{"name":"테스트","cnt":4}]
			  pieGraphPrint(data);
		  },
		  error : function(e) {
			  alert("서버 오류:" + e.status);
		  }
	  })
  }
  function bargraph() {
	  $.ajax("${path}/ajax/graph2.shop",{
		  success : function(data) {
			  barGraphPrint(data);
		  },
		  error : function(e) {
			  alert("서버 오류:" + e.status);
		  }
	  })
  } 
  function pieGraphPrint(data) {
	var rows = JSON.parse(data);
	var names = []
	var datas = []
	var colors = []
	$.each(rows,function(index,item) {
		names[index] = item.name;
		datas[index] = item.cnt;
		colors[index] = randomColor(0.7);
	})
	var config = {
		  type : 'pie',
		  data : {
			  datasets : [{
				  data : datas,
				  backgroundColor:colors
			  }],
			  labels : names
		  },
	      options : {
	    	responsive : true,
	    	legend : {position : 'top'},
	    	title : {
	    		display : true,
	    		text : '글쓴이 별 게시판 등록 건수',
	    		position : 'bottom'
	    	}
	      }
	}
	var ctx = document.getElementById("canvas1").getContext("2d");
	new Chart(ctx,config);
  }
  //[ { "regdate":"2020-12-24" , "cnt":"2" } , { "regdate":"2020-12-23" , "cnt":"7" } ]
  function barGraphPrint(data) {
      var rows = JSON.parse(data);
      var regdates = [];//[2020-12-24,2020-12-23]
      var datas = []; //[2,7]
      var colors = [];
	  $.each(rows,function(index,item) {
		regdates[index] = item.regdate;
		datas[index] = item.cnt;
		colors[index] = randomColor(0.7);
	  })
	  var chartData = {
			labels: regdates,
			datasets: [{
				type: 'line',
				borderWidth: 2,
				borderColor:colors,
				label: '건수',
				fill: false,
				data: datas,
			}, {
				type: 'bar',
				label: '건수',
				backgroundColor: colors,
				data: datas
			}]
	      }
	    var config = {
				type: 'bar',
				data: chartData,
				options: {			
					responsive: true,
					title: {display: true,
						    text: '최근 7일 게시판 등록 건수',
						    position : 'bottom'
					},
					legend : {display : false },
					scales: {
						xAxes: [{ display : true,   stacked : true }],			
						yAxes: [{ display : true,   stacked : true }]			
				    }
				}
	    }
		var ctx = document.getElementById('canvas2').getContext('2d');
		new Chart(ctx,config);
  }
</script>
</body>
</html>