<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<<<<<<< HEAD
<!-- <link href="bootstrap/bootstrap-combined.min.css" rel="stylesheet" media="screen"> -->
<link href="css/chat.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<script type="text/javascript" src="bootstrap/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="bootstrap/jquery-ui.js"></script>
<script type="text/javascript" src="bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="js/chat.js"></script>

=======
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link
	href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
>>>>>>> 7c66df6908cae9fff128ffeef55805c564601062
</head>
<body>
	<!-- <div class="container-fluid"> 
		<div class="row-fluid">
			<div class="span12">
				<div class="row-fluid">
					<div class="col-xs-6 span9">
					<h1 class="page-header">聊天....</h1>
					<table class="table">
						<tbody id="tall">
						</tbody>
					</table>			
					</div>
					<div class="col-xs-6 span3">
						<form action="javascript:send()">
							<fieldset>
								<legend>临时聊天室</legend>
<<<<<<< HEAD
								<label>发言</label><input type="text" id="text" />
								<button class="btn" type="submit">提交</button>
							</fieldset>
						</form>
=======
								<label>发言</label><input type="text" id="text"/> <!-- <label class="checkbox"><input
									type="checkbox" /> 匿名</label> -->
								<button class="btn" type="submit">提交</button>
							</fieldset>
						</form>
						<a href="test">struts 的test</a>
>>>>>>> 7c66df6908cae9fff128ffeef55805c564601062
					</div>
				</div>
			</div>
		</div>
	</div>-->
	<div class="container clearfix">
		<div class="people-list" id="people-list">
			<div class="search">
				<input type="text" placeholder="search" /> <i class="fa fa-search"></i>
			</div>
			<ul class="list" id="userList">
				<li class="clearfix"><img src="image/1.jpg" alt="avatar" />
					<div class="about">
						<div class="name">Vincent Porter</div>
						<div class="status">
							<i class="fa fa-circle online"></i> online
						</div>
					</div></li>

				<li class="clearfix"><img src="image/1.jpg" alt="avatar" />
					<div class="about">
						<div class="name">Aiden Chavez</div>
						<div class="status">
							<i class="fa fa-circle offline"></i> left 7 mins ago
						</div>
					</div></li>			
			</ul>
		</div>

		<div class="chat">
			<div class="chat-header clearfix">
				<img src="image/2.jpg" alt="avatar" />

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
				<textarea name="message-to-send" id="message-to-send"
					placeholder="Type your message" rows="3">
           		</textarea>
           		<div class="signin">
					<input type="submit" value="发送" onclick="send()">
				</div>
			</div>
		</div>
	</div>

</body>


<!-- websocket的页面端实现 -->
<script type="text/javascript">
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://localhost:8080/MyWebSocket/websocket");
	} else {
		alert('Not support websocket')
	}
	//连接发生错误的回调方法
	websocket.onerror = function() {
		setMessageInnerHTML("error");
	};
	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		setMessageInnerHTML("open");
	}
	//接收来自后台的websocket数据
	//添加到tall聊天记录
	websocket.onmessage = function() {
<<<<<<< HEAD
		document.getElementById("talk").innerHTML = document.getElementById("talk").innerHTML+event.data;
=======
		//alert(event.data);
		//$("#tall").append(event.data);
		document.getElementById("tall").innerHTML+=event.data;
>>>>>>> 7c66df6908cae9fff128ffeef55805c564601062
	}
	//连接关闭的回调方法
	websocket.onclose = function() {
		setMessageInnerHTML("close");
	}
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		websocket.close();

	}
	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('message').innerHTML += innerHTML + '<br/>';
	}
	//关闭连接
	function closeWebSocket() {
		websocket.close();

	}
	//发送消息
	function send() {
<<<<<<< HEAD
		var message = $("#message-to-send").val();
		var time=new Date();
		var time2=new Date(parseInt(time.getTime())).toLocaleString();
		var t = "<li><div class='message-data'><span class='message-data-name'>"+
				"<i class='fa fa-circle online'></i>我</span>"+
				" <span class='message-data-time'>"+time2+"</span></div>"+
				"<div class='message my-message'>"+message+"</div></li>";
	
		document.getElementById("talk").innerHTML = t+document.getElementById("talk").innerHTML;
		websocket.send(message);
=======
		var message = document.getElementById('text').value;
		var t="<h3>我：</h3><tr class='success' style='float:left'>"+message+"</tr><br/>";
		document.getElementById("tall").innerHTML+=t;
		//$("#tall").append(t);
		websocket.send(message);

>>>>>>> 7c66df6908cae9fff128ffeef55805c564601062
	}
</script>

</html>