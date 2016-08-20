<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 基本样式 -->
<link href="css/chat.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<!-- login -->
<link href="lib/login/master.css" rel="stylesheet">
<!--faceMocion -->
<link rel="stylesheet" type="text/css" href="lib/faceMocion/demo.css">
<link href="lib/faceMocion/faceMocion.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.mt50{margin-top: 50px}
	.pd30{padding: 30px}
</style>
<!-- chat.js -->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/chat.js"></script>
<!-- layer -->
<script type="text/javascript" src="lib/layer/2.1/layer.js"></script>
<!-- faceMocion -->
<script type="text/javascript" src="lib/faceMocion/faceMocion.js"></script> 

</head>
<body>
	<div style="container clearfix; margin-left: 20px;"><h1>web chat</h1></div>
	<!-- 聊天页面 -->
	<div class="container clearfix" style="position:relative;display:none" id="chat">
		<div class="people-list" id="people-list">
			<div class="search">
				<input type="text" placeholder="search" /> <i class="fa fa-search"></i>
			</div>
			<ul class="list" id="userList">
						
			</ul>
		</div>

		<div class="chat">
			<div class="chat-header clearfix">
				<img src="faceImage/2.jpg" alt="avatar" />

				<div class="chat-about">
					<div class="chat-with">Chat</div>
					<div class="chat-num-messages"></div>
				</div>
				<i class="fa fa-star"></i>
			</div>
			<!-- end chat-header -->

			<div class="chat-history">
				<ul id="talk">
					
				</ul>

			</div>
			<!-- end chat-history -->
			
			<div class="chat-message clearfix">
				<!-- 表情 -->
				<div class="col-md-8 col-md-offset-4 col-xs-12 mt50 pd30">
					<input type="hidden" value="amo" class="prueba" id="facebookEmoji"/>
				</div>
				<!-- 发言内容 -->
				<textarea name="message" id="message" placeholder="Type your message" rows="3"></textarea>
           		<div>
					<input type="submit" value="发送" onclick="send()">
				</div>
			</div>
		</div>
	</div>
	<!-- 登录页面  agar上截下来的-->
	<div class="agario-panel" id="mainPanel" style="opacity: 1;position:absolute;top:30%;left: 40%;">
		<div role="form" >
			<div class="form-group clearfix">
				<div style="float: left; margin-left: 20px;">
					<h2>昵称</h2>
				</div>
			</div>
			<div class="form-group clearfix">
				<input class="form-control" id="name" autofocus="" maxlength="15"
					placeholder="Nick">
			</div>
			<div class="form-group" id="agario-main-buttons">
				<div class="row">
					<button class="btn btn-play-guest btn-success btn-needs-server"
						onclick="chat(document.getElementById('name').value); return false;"
						type="submit" data-itr="page_play_as_guest">chat now</button>
				</div>

			</div>
		</div>
		<div id="instructions">
			<hr style="margin-top:10px;margin-bottom:10px;">
			<span class="text-muted"> 
				<center>
					<span data-itr="page_instructions_space">在线 <b>chat</b>聊天</span><br> 
				</center>
			</span>
		</div>
	</div>
</body>


<!-- websocket的页面端实现 -->
<script type="text/javascript">
	var websocket = null;
	function chat(name){
		document.getElementById("chat").style.display="block";
		document.getElementById("mainPanel").style.display="none";
		var user="<li class='clearfix'><img src='faceImage/1.jpg' alt='avatar' />"+
					"<div class='about'>"+
						"<div class='name'>"+name+"</div>"+
						"<div class='status'>"+
							"<i class='fa fa-circle online'></i>"+
						"</div>"+
					"</div></li>";
			$("#userList").append(user);
	}
	
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://localhost:8080/MyWebSocket/websocket");
		//websocket = new WebSocket("ws://121.42.178.69:8080/MyWebSocket/websocket");
	} else {
		layer.msg("Not support websocket!", { icon: 5, time: 1000 });
	}
	
	//连接发生错误的回调方法
	websocket.onerror = function() {
		layer.msg("error!", { icon: 5, time: 1000 });
	};
	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		layer.msg("!", { icon: 6, time: 1000 });
	}
	//接收来自后台的websocket数据
	//添加到talk聊天记录
	websocket.onmessage = function() {
		//把特殊字符转成图片 molesto 
		var imageStr=/amo|molesto|asusta|divierte|gusta|triste|asombro|alegre/;
		var emoji=event.data.match(imageStr);
		var show= event.data;
		if(emoji!=null||emoji!=""){
			var img="<div class='Selector selectorFace  "+emoji+
            		"' dato-descripcion='"+emoji+"'></div>";           		
              show=show.replace(emoji,img);
		}
		$("#talk").append(show);
		//刷新用户列表
		
	}
	//连接关闭的回调方法
	websocket.onclose = function() {
		layer.msg("close!", { icon: 2, time: 1000 });
	}
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		websocket.close();

	}
	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}
	//发送消息
	function send() { 
		var message = $("#message").val();
		var time=new Date();
		var time2=new Date(parseInt(time.getTime())).toLocaleString();
		var t = "<li><div class='message-data'><span class='message-data-name' style='font-size:20px'>"+
				"<i class='fa fa-circle online'></i>我</span>"+
				" <span class='message-data-time'>"+time2+"</span></div>"+
				"<div class='message my-message'>"+message+"</div></li>";
		
		document.getElementById("talk").innerHTML = document.getElementById("talk").innerHTML+t;
		//$("#talk").append(t);
		websocket.send(message);
		$("#message").val("");
	}
</script>

<!-- load facebook emoji -->
<script type="text/javascript">
	$(function(){
		$("#facebookEmoji").faceMocion();
	})
</script>
<!-- 控制事件 -->
<script type="text/javascript">
	document.ready(){
		alert(123);
	
	}


</script>
</html>