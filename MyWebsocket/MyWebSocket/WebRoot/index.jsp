<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link
	href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
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
								<label>发言</label><input type="text" id="text"/> <!-- <label class="checkbox"><input
									type="checkbox" /> 匿名</label> -->
								<button class="btn" type="submit">提交</button>
							</fieldset>
						</form>
						<a href="test">struts 的test</a>
					</div>
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
		//alert(event.data);
		//$("#tall").append(event.data);
		document.getElementById("tall").innerHTML+=event.data;
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
		var message = document.getElementById('text').value;
		var t="<h3>我：</h3><tr class='success' style='float:left'>"+message+"</tr><br/>";
		document.getElementById("tall").innerHTML+=t;
		//$("#tall").append(t);
		websocket.send(message);

	}
</script>

</html>