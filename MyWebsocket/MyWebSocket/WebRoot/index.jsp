<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<script type="text/javascript" src="bootstrap/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="bootstrap/jquery-ui.js"></script>
<link href="bootstrap/bootstrap-combined.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript" src="bootstrap/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="row-fluid">
					<div class="col-xs-6 span9">
						<h1 class="page-header">聊天....</h1>
						<table class="table" style="table-layout:fixed">
							<tbody id="talk">
							</tbody>
						</table>
					</div>
					<div class="col-xs-6 span3">
						<form action="javascript:send()">
							<fieldset>
								<legend>临时聊天室</legend>
								<label>发言</label><input type="text" id="text" />
								<!-- <label class="checkbox"><input
									type="checkbox" /> 匿名</label> -->
								<button class="btn" type="submit">提交</button>
							</fieldset>
						</form>
						<a href="test">验证是加了struts的</a>
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
		document.getElementById("talk").innerHTML = event.data+document.getElementById("talk").innerHTML;
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
		var time=new Date();
		var time2=new Date(parseInt(time.getTime())).toLocaleString();
		var t = "<tr class='success' style='float:center'><td style='font-size: 20px'> 我：  </td></tr>"+
			"<tr class='success' style='float:center'><td style='font-size: 20px;overflow:hidden;text-overflow:ellipsis;word-break:keep-all;white-space:nowrap;'>&nbsp; &nbsp;&nbsp;&nbsp; "+ message+ "</td></tr>"+
			"<tr class='success' style='float:center'><td style='text-align: right'>"+time2+"</td></tr>";
	
		document.getElementById("talk").innerHTML = t+document.getElementById("talk").innerHTML;
		websocket.send(message);


	}
</script>

</html>