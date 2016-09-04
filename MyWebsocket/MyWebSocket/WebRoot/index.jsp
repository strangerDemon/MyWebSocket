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
<link href="lib/faceMocion/faceMocion.css" rel="stylesheet"
	type="text/css" />
<!-- select -->
<link rel="stylesheet" type="text/css"
	href="lib/select/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="lib/select/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="lib/select/css/cs-select.css" />
<link rel="stylesheet" type="text/css"
	href="lib/select/css/cs-skin-underline.css" />
<style type="text/css">
.mt50 {
	margin-top: 50px
}

.pd30 {
	padding: 30px
}
</style>

<!-- radio -->
<link rel="stylesheet" type="text/css" href="css/radioCss/normalize.css" />
<link rel="stylesheet" type="text/css"
	href="css/radioCss/htmleaf-demo.css">
<link type="text/css" rel="stylesheet" href="css/radioCss/style.css">

<!--  -->
<script type="text/javascript" src="js/jquery-3.1.0.min.js"></script>
<script type="text/javascript" src="js/keydown.js"></script>
<!-- chat.js -->
<!-- <script type="text/javascript" src="js/jquery.js"></script> -->
<script type="text/javascript" src="js/chat.js"></script>
<!-- layer -->
<script type="text/javascript" src="lib/layer/2.1/layer.js"></script>
<!-- faceMocion -->
<script type="text/javascript" src="lib/faceMocion/faceMocion.js"></script>
<!-- radio -->
<script type="text/javascript" src="js/colorizer.js"></script>
<!-- select -->
<script src="lib/select/js/classie.js"></script>
<script src="lib/select/js/selectFx.js"></script>

</head>
<body>
	<div style="container clearfix; margin-left: 20px;">
		<h1>web chat</h1>
	</div>
	<!-- 聊天页面 -->
	<div class="container clearfix" style="position:relative;display:none"
		id="chat">
		<div class="people-list" id="people-list">
			<div class="search">
				<input type="text" placeholder="search" /> <i class="fa fa-search"></i>
			</div>
			<ul class="list" id="userList">

			</ul>
		</div>

		<div class="chat">
			<div class="chat-header clearfix">
				<img src="images/faceImage/2.jpg" alt="avatar" />

				<div class="chat-about">
					<div class="chat-with">Chat</div>
					<div class="chat-num-messages"></div>
				</div>
			</div>
			<!-- end chat-header -->

			<div class="chat-history clearfix">
				<ul id="talk">

				</ul>

			</div>
			<!-- end chat-history -->

			<div class="chat-message clearfix">
				<!-- 表情 -->
				<div>
					<input type="hidden" value="amo" class="prueba" id="facebookEmoji" />
				</div>
				<!-- 发言内容 -->
				<textarea name="message" id="message"
					placeholder="Type your message" rows="3"></textarea>
				<div>
					<input type="submit" value="发送" onclick="send()">
				</div>
			</div>
		</div>
	</div>
	<!-- 登录页面  agar上截下来的-->
	<div class="agario-panel" id="mainPanel"
		style="opacity: 1;position:absolute;top:30%;left: 40%;">
		<div role="form">
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
					<span data-itr="page_instructions_space">在线 <b>chat</b>聊天
					</span><br>
				</center>
			</span>
		</div>
	</div>
	<!-- 音乐 -->
	<div class="audio" id="radioDiv" style="position:fixed;display:none">
		<div class="echolizer"></div>
		<div class="colorizer"></div>
		<div class="disk"></div>
		<img src="images/radioImages/cover.jpg">
		<section onclick="music()" style="width:150%">
			<select class="cs-select cs-skin-underline" id="musicList">
				<option value="0" disabled selected>music</option>
				<option value="music1">music1</option>
				<option value="music2">music2</option>
				<option value="music3">music3</option>
			</select>
		</section>
	</div>
</body>
<!-- websocket的页面端实现 -->
<script type="text/javascript">
	var websocket = null;
	function chat(name) {
		document.getElementById("chat").style.display = "block";
		document.getElementById("mainPanel").style.display = "none";
		document.getElementById("radioDiv").style.display = "block";
		var name = $("#name").val() == "" ? "null" : $("#name").val();
		websocket.send("#name" + name);
	}

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://localhost:8080/MyWebSocket/websocket");
		//websocket = new WebSocket("ws://121.42.178.69:8080/MyWebSocket/websocket");
	} else {
		layer.msg("Not support websocket!", {
			icon : 5,
			time : 1000
		});
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		layer.msg("error!", {
			icon : 5,
			time : 1000
		});
	};
	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		layer.msg("open!", {
			icon : 6,
			time : 1000
		});
	}

	//接收来自后台的websocket数据
	websocket.onmessage = function() {
		//刷新用户列表	
		var message = event.data;
		var logMessage = message.substring(0, 5);
		if (logMessage == "login") {
			var name = message.substring(message.indexOf("户") + 1, message
					.indexOf("加"));
			//var id=message.substring(message.indexOd("key'"),message.indexOd("keyEnd"));alert(id);
			var user = "<li class='clearfix' id='"+name+"'><img src='images/faceImage/3.jpg' alt='avatar' />"
					+ "<div class='about'>"
					+ "<div class='name'>"
					+ name
					+ "</div>" + "</div></li>";
			$("#userList").append(user);
			message = event.data.substring(5);
		} else if (logMessage == "lgout") {
			message = event.data.substring(5);
			var name = message.substring(message.indexOf("户") + 1, message
					.indexOf("退"));
			$("#" + name).remove();
		} else if (logMessage == "allUs") {
			message = event.data.substring(5);
			$("#userList").append(message);
			return;
		}
		//把特殊字符转成图片
		var imageStr = /amo|molesto|asusta|divierte|gusta|triste|asombro|alegre/;
		var emoji = message.match(imageStr);
		var show = event.data;
		if (emoji != null || emoji != "") {
			var img = "<div class='Selector selectorFace  "+emoji+
            		"' dato-descripcion='"+emoji+"'></div>";
			message = message.replace(emoji, img);
		}
		$("#talk").append(message);
	}
	//连接关闭的回调方法
	websocket.onclose = function() {
		layer.msg("close!", {
			icon : 2,
			time : 1000
		});
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
		var time = new Date();
		var time2 = new Date(parseInt(time.getTime())).toLocaleString();
		var t = "<li><div class='message-data'><span class='message-data-name' style='font-size:20px'>"
				+ "&nbsp;我</span>"
				+ " <span class='message-data-time'>"
				+ time2
				+ "</span></div>"
				+ "<div class='message my-message'>"
				+ message + "</div></li>";

		document.getElementById("talk").innerHTML = document
				.getElementById("talk").innerHTML
				+ t;
		//$("#talk").append(t);
		websocket.send(message);
		$("#message").val("");
	}
</script>

<!-- load facebook emoji -->
<script type="text/javascript">
	$(function() {
		$("#facebookEmoji").faceMocion();	
	})
</script>
<!-- 音乐 -->
<script type="text/javascript">

	function music(){
		var name=$("#musicList").val();
		if(name!=null){
			$.colorizer("div.colorizer", {
				
				file : "radio/"+name+".mp3",
				shadow : ".colorizer",
				echolizer : ".echolizer"
			}); 
		}		
	}
	//写成自己下拉框控制点击事件
	/* $.colorizer("div.colorizer", {
		file : "radio/music1.mp3",
		shadow : ".colorizer",
		echolizer : ".echolizer"
	}); */
</script>
<!-- select 触发事件 -->
<script>
	(function() {
		[].slice.call(document.querySelectorAll('select.cs-select')).forEach(function(el) {
							new SelectFx(el);
				});
	})();
</script>
</html>